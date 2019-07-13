/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1. Send primary ray
    2. intersection test with all shapes
    3. if hit:
    3a: send secondary ray to the light source
    3b: 2
        3b.i: if hit:
            - Shape is in the shade
            - Pixel color = ambient value
        3b.ii: in NO hit:
            - calculate local illumination
    4. if NO hit:
        - set background color

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package raytracer;

import light.AreaLight;
import light.Light;
import material.Material;
import org.w3c.dom.css.RGBColor;
import scene.Scene;
import scene.SceneObject;
import shape.Square;
import ui.Window;
import utils.*;
import utils.algebra.Vec2;
import utils.algebra.Vec3;
import utils.io.Log;

import java.awt.image.BufferedImage;

import static java.lang.Float.POSITIVE_INFINITY;

public class Raytracer {

    private final BufferedImage mBufferedImage;
    private final Window mRenderWindow;
    private final Scene mScene;
    private final long mtStart;
    private final int recursions = 0;
    private int indirectLightRecursions = 0;



    RgbColor refcolor = new RgbColor(0,0,0);

    public Raytracer(Scene scene, Window renderWindow){
        //Log.print(this, "Init");
        mRenderWindow = renderWindow;
        mScene = scene;
        mtStart = System.currentTimeMillis();
        mBufferedImage = renderWindow.getBufferedImage();

    }

    private void exportRendering(BufferedImage renderImage, int recursion, int frame){
        Log.print(String.valueOf(stopTime(mtStart)));
        mRenderWindow.exportRenderingToFile(renderImage, String.valueOf(stopTime(mtStart)), recursion-1, frame);
        //Log.print("elapsed time: " + String.valueOf(stopTime(mtStart)));
    }

    private static double stopTime(long tStart){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    public void renderScene(int frame, int yStart, int yEnd){
        //Log.print(this, "Prepare rendering at " + String.valueOf(stopTime(mtStart)));



        for(float y = yStart; y < yEnd; y++) {
            for (float x = 0f; x < mBufferedImage.getWidth(); x++) {
                //RgbColor color = new RgbColor(0,0,0);

                //RgbColor stl = traceRay(createPrimaryRay(new Vec2(x - .25f, y + .25f)), recursions).multScalar(.25f);
                //RgbColor str = traceRay(createPrimaryRay(new Vec2(x + .25f, y + .25f)), recursions).multScalar(.25f);
                //RgbColor sbl = traceRay(createPrimaryRay(new Vec2(x - .25f, y - .25f)), recursions).multScalar(.25f);
                //RgbColor sbr = traceRay(createPrimaryRay(new Vec2(x + .25f, y - .25f)), recursions).multScalar(.25f);



                // EASY GRID SAMPLING -- just set Globals.sampling to anythong greater than 1
                RgbColor sampledColor = new RgbColor(0,0,0);

                // TODO: SAMPLE NOT EVERY 1/x OF THE PIXEL, INSTEAD DO IT ADAPTIVE: SPLIT, IF COLORS DIFFER TOO MUCH, THEN GO DEEPER :)
                float incr = (float) 1 / Globals.sampling;
                for(int ySub = 0; ySub < Globals.sampling; ySub++) {
                    for (float xSub = 0; xSub < Globals.sampling; xSub++) {
                        // TODO: REPLACE RANDOMNESS WITH BLUE NOISE
                        float xRand = Globals.rand(-1,1) * .01f * Globals.sampleFraction;
                        float yRand = Globals.rand(-1,1) * .01f * Globals.sampleFraction;
                        sampledColor = sampledColor.add(traceRay(createPrimaryRay(new Vec2((x - .5f + (xSub * incr)) + xRand, y - .5f + (ySub * incr) + yRand)), recursions).multScalar(Globals.sampleFraction));
                    }
                }

                //RgbColor sm = traceRay(createPrimaryRay(new Vec2(x, y)), recursions).multScalar(.5f);
                //color = (color.add(stl).add(str).add(sbl).add(sbr).add(sm));

                //color = traceRay(createPrimaryRay(new Vec2(x, y)), recursions);

                mRenderWindow.setPixel(mBufferedImage, sampledColor, new Vec2(x, y));
            }

        }
        exportRendering(mBufferedImage, Globals.recursionDepth, frame);
    }

    private Ray createPrimaryRay(Vec2 pixelPoint){

        int x = (int) pixelPoint.x;
        int y = (int) pixelPoint.y;

        // get correct ray from camera
        return mScene.perspCamera.rayFor(x, y);

    }

    private Ray createSecondaryRay(Vec3 position, Vec3 direction){

        direction = (direction.sub(position)).normalize();
        position = (position.add(direction.multScalar(Globals.epsilon)));
        return new Ray(position, direction);

    }

    Vec3 createCoordinateSystemNT(Vec3 N, Vec3 Nt, Vec3 Nb)
    {
        double ntValue = 0;
        if (Math.abs(N.x) > Math.abs(N.y)) {
            ntValue = 1 / Math.sqrt(N.x * N.x + N.z * N.z);
            Nt = new Vec3(N.z, 0, -N.x).multScalar((float) ntValue);
        }
        else {
            ntValue = 1 / Math.sqrt(N.y * N.y + N.z * N.z);
            Nt = new Vec3(0, -N.z, N.y).multScalar((float) ntValue);
           // Nb = N.cross(Nt);
        }
        return Nt;
    }

    Vec3 createCoordinateSystemNB(Vec3 N, Vec3 Nt, Vec3 Nb)
    {
        double ntValue = 0;
        if (Math.abs(N.x) > Math.abs(N.y)) {
            ntValue = 1 / Math.sqrt(N.x * N.x + N.z * N.z);
            Nt = new Vec3(N.z, 0, -N.x).multScalar((float) ntValue);
        }
        else {
            ntValue = 1 / Math.sqrt(N.y * N.y + N.z * N.z);
            Nt = new Vec3(0, -N.z, N.y).multScalar((float) ntValue);
        }
        Nb = N.cross(Nt);
        return Nb;
    }

    Vec3 uniformSampleHemisphere(float r1,float r2)
    {
        // cos(theta) = r1 = y
        // cos^2(theta) + sin^2(theta) = 1 -> sin(theta) = srtf(1 - cos^2(theta))
        float sinTheta =(float) Math.sqrt(1 - (r1 * r1));
        float phi = (float) (2 * Math.PI * r2);
        float x = (float) (sinTheta * Math.cos(phi));
        float z = (float) (sinTheta * Math.sin(phi));
        return new Vec3(x, r1, z);
    }

    private RgbColor traceRay(Ray ray, int recursions) {
        // CHECK FOR INTERSECTION
        float t = POSITIVE_INFINITY; // distance
        SceneObject hitObject = null;
        SceneObject shadowObject = null;
        boolean hit = false;
        RgbColor calcColor = new RgbColor(0, 0, 0);
        RgbColor indirectLightContrib = new RgbColor(0, 0, 0);
        float redValue=0;
        float greenValue=0;
        float blueValue=0;




            for (SceneObject object : mScene.getShapeList()) {


                float tmin = object.isHitByRay(ray);
                if (tmin > Globals.epsilon && tmin < t) {
                    t = tmin;
                    //Log.print(" " + t);
                    hitObject = object;
                    hit = true;
                }
            }

            if (hit) {
                // TODO: ADD LIGHT SAMPLING
                for (Light light : mScene.getLightList()) {
                    //Ray secondaryRay = createSecondaryRay(ray.at(t), light.getPosition());
                    Ray secondaryRay = createSecondaryRay(ray.at(t).add(new Vec3(1,1,1).multScalar(Globals.epsilon)), light.getPosition());
                    Material mat = hitObject.getMaterial();

                    boolean inShadow = false;
                        for (SceneObject shadowingObject : mScene.getShapeList()) {
                            if (shadowingObject != hitObject) {
                                float tmin2 = shadowingObject.isHitByRay(secondaryRay);
                                float tLight = (light.getPosition().sub(secondaryRay.getOrigin())).length();

                                if (tmin2 > 0 && tmin2 < tLight) {
                                    shadowObject=shadowingObject;
                                    inShadow = true;
                                }
                            }

                        }
                    if (!inShadow){
                        calcColor = calcColor.add(mat.calculateColor(secondaryRay, light, hitObject, mScene) );
                    }

                    else {  //               && ligth=Arealight
                        float k=0;
                        float shadowValue = 0.925f; // eigentlich 1 aber dann sieht man Schatten kaum
                        SceneObject hittObject = null;

                        for(int i = 0; i<Globals.shadowRays ; i++){        // for schleife für density
                            float xValue = (float)(Math.random()-0.5f)*2;
                            float zValue = (float)(Math.random()-0.5f)*2;
                            if(i<=Globals.shadowRays/4){
                                xValue=(float)Math.random();
                                zValue=(float)Math.random();
                            }
                            if(i>Globals.shadowRays/4 && i <= 2*Globals.shadowRays/4){
                                xValue=(float)-Math.random();
                                zValue=(float)-Math.random();
                            }
                            if(i>2*Globals.shadowRays/4 && i <= 3*Globals.shadowRays/4){
                                xValue=(float)-Math.random();
                                zValue=(float)Math.random();
                            }
                            if(i>3*Globals.shadowRays/4 && i <= 4*Globals.shadowRays/4){
                                xValue=(float)Math.random();
                                zValue=(float)-Math.random();
                            }

                            Ray shadowRay = createSecondaryRay(ray.at(t).add(light.getPosition().add(new Vec3(xValue,0,zValue).multScalar(Globals.epsilon))), light.getPosition().add(new Vec3(xValue,0,zValue)));
                            for (SceneObject object : mScene.getShapeList()) {
                                float tmin = object.isHitByRay(shadowRay);
                                float tt=POSITIVE_INFINITY;
                                if (tmin > Globals.epsilon && tmin < tt) {
                                    tt = tmin;
                                    hittObject = object;
                                }
                            }
                            if(shadowObject==hittObject){
                                k=k+1;
                            }
                        }
                        RgbColor calcTempColor = new RgbColor(0,0,0).add(mat.calculateColor(ray, light, hitObject, mScene)).multScalar(shadowValue-k/Globals.shadowRays*shadowValue);
                        calcColor=calcTempColor;
                    }
                }


                }

                if (recursions < Globals.recursionDepth && hitObject != null) {

                    recursions++;
                    if (hitObject.getMaterial().getReflectivity() != 0) {

                        Vec3 N = hitObject.getNormal(ray.at(t));
                        Vec3 I = ray.getDirection();


                        Vec3 refVec = I.sub(N.multScalar(I.scalar(N)*2));
                        Ray refRay = new Ray(ray.at(t),refVec.normalize());

                        refVec = I.sub(N.multScalar(2f * (I.scalar(N))));
                        refRay = new Ray(ray.at(t).add(hitObject.getNormal(ray.at(t)).multScalar(Globals.epsilon)), refVec);

                        calcColor = calcColor.add(traceRay(refRay, recursions).multScalar(hitObject.getMaterial().getReflectivity()));
                        //return calcColor.add( hitObject.getColor().multScalar(Globals.ambient));
                   }
                    //recursiveDepth =0;
                    //calcColor = calcColor.add( hitObject.getColor().multScalar(Globals.ambient) );
                    // TODO: ADD FRESNEL, CURRENTLY k_r AND k_t ARE DEFINED WHEN CREATING MATERIALS (SHOULD BE k_r * refl_color + (1-k_r) * refr_color*/

                    if (hitObject.getMaterial().getRefractivity() != 0) {

                        Vec3 normal = hitObject.getNormal(ray.at(t)).normalize();
                        Vec3 I = ray.getDirection().normalize();
                        double NdotI = normal.scalar(I);
                        double etai = 1;
                        double etat = Globals.nGlass;

                        if (NdotI < 0) {
                            NdotI = -1 * NdotI;
                        } else {
                            normal = normal.multScalar(-1);
                            double temp = etai;
                            etai = etat;
                            etat = temp;
                        }

                        double eta = etai / etat;
                        double k = 1 - (eta * eta) * (1 - NdotI * NdotI);
                        if (k >= 0f) {
                            //return calcColor = calcColor.add( hitObject.getColor().multScalar(Globals.ambient) );

                            Vec3 refrDirection = I.add(normal.multScalar((float) NdotI)).multScalar((float) eta).sub(normal.multScalar((float) Math.sqrt(k)));

                            Ray refrRay = new Ray(ray.at(t).add(refrDirection.normalize().multScalar(Globals.epsilon)), refrDirection.normalize());
                            calcColor = calcColor.add(traceRay(refrRay, recursions).multScalar(hitObject.getMaterial().getRefractivity()));
                        }
                        //recursiveDepth =0;
                        //return calcColor.multScalar(hitObject.getMaterial().getRefractivity());

                    }
                }

        if(indirectLightRecursions<Globals.lightRecursionDepth && ((hitObject.getMaterial().getReflectivity()+hitObject.getMaterial().getRefractivity())==0)){
            Vec3 Nt = new Vec3(0,0,0);
            Vec3 Nb = new Vec3(0,0,0);
            Nt=createCoordinateSystemNT(hitObject.getNormal(ray.at(t)),Nt,Nb);
            Nb=createCoordinateSystemNB(hitObject.getNormal(ray.at(t)),Nt,Nb);
            indirectLightRecursions++;
            for(int n =0;n<Globals.lightRecursionRays;++n){
                float theta = (float) (Math.random() * Math.PI);
                float cosTheta = (float) Math.cos(theta);
                float sinTheta = (float) Math.sin(theta);

                Vec3 sample = uniformSampleHemisphere(cosTheta,sinTheta);
                Vec3 sampleWorld = new Vec3(
                        sample.x * Nb.x + sample.y * hitObject.getNormal(ray.at(t)).x + sample.z * Nt.x,
                        sample.x * Nb.y + sample.y * hitObject.getNormal(ray.at(t)).y + sample.z * Nt.y,
                        sample.x * Nb.z + sample.y * hitObject.getNormal(ray.at(t)).z + sample.z * Nt.z);

                Ray indirectLightRay = new Ray(ray.at(t),sampleWorld.normalize());
                RgbColor sampleColor = traceRay(indirectLightRay,recursions);
                redValue=(redValue+((sampleColor.getRedValue()*0.25f)));            // wie stark soll R/G/B gewichtet werden -> eigentlich cosTheta anstatt der Wert aber dann sieht es falsch aus
                greenValue=(greenValue+((sampleColor.getGreenValue()*0.25f)));
                blueValue=(blueValue+((sampleColor.getBlueValue()*0.25f)));
            }
            indirectLightContrib = new RgbColor(redValue/Globals.lightRecursionRays,greenValue/Globals.lightRecursionRays,blueValue/Globals.lightRecursionRays);
            indirectLightRecursions=0;
        }

        float multiplyValue = (float)(1/Math.PI); // aus der Formel für Monte Carlo Raytracing
        float multiplyValue2 = 2; // Bild heller/dunkler machen
        float indirectLightValue = 1.5f; // Wie "stark" soll das indirekt Licht sein
        if(hitObject != null &&(hitObject.getMaterial().getReflectivity() !=0  || hitObject.getMaterial().getRefractivity() !=0)) {  // notwendig -> sonst flecken auf den kugeln bei refl/refr
            multiplyValue = 1;
            multiplyValue2 = 1;
        }

        return calcColor.multScalar(multiplyValue).add(indirectLightContrib.multScalar(indirectLightValue)).multScalar(multiplyValue2);

    }

}
