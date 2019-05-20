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

import camera.PerspectiveCamera;
import light.Light;
import material.Material;
import scene.Scene;
import scene.SceneObject;
import ui.Window;
import utils.*;
import utils.algebra.Matrix4x4;
import utils.algebra.Vec2;
import utils.algebra.Vec3;
import utils.algebra.Vec4;
import utils.io.Log;

import java.awt.image.BufferedImage;
import java.util.*;

import static java.lang.Float.POSITIVE_INFINITY;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toList;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private Scene mScene;
    private long mtStart;
    private int currentRecursion = 0;

    public Raytracer(Scene scene, Window renderWindow){
        //Log.print(this, "Init");
        mRenderWindow = renderWindow;
        mScene = scene;
        mtStart = System.currentTimeMillis();
        mBufferedImage = renderWindow.getBufferedImage();

    }

    public void exportRendering(BufferedImage renderImage, int recursion, int frame){
        mRenderWindow.exportRenderingToFile(renderImage, String.valueOf(stopTime(mtStart)), recursion-1, frame);
    }

    private static double stopTime(long tStart){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    public void renderScene(int frame){
        //Log.print(this, "Prepare rendering at " + String.valueOf(stopTime(mtStart)));

        for(float y = 0f; y < mBufferedImage.getHeight(); y++){
            for(float x = 0; x < mBufferedImage.getWidth(); x++){
                    RgbColor color = traceRay(createPrimaryRay(new Vec2(x,y)));

                    mRenderWindow.setPixel(mBufferedImage, color, new Vec2(x, y));

            }
        }
        exportRendering(mBufferedImage, Globals.recursionDepth, frame);
    }

    private Ray createPrimaryRay(Vec2 pixelPoint){

        int x = (int) pixelPoint.x;
        int y = (int) pixelPoint.y;

        // get correct ray from camera
        Ray primaryRay = mScene.perspCamera.rayFor(x, y);
        //Log.print(primaryRay.toString());


        return primaryRay;

    }

    private Ray createSecondaryRay(Vec3 position, Vec3 direction){

        direction = (direction.sub(position)).normalize();
        position = (position.add(direction.multScalar(Globals.epsilon)));
        Ray secondaryRay = new Ray(position, direction);
        //Log.print(secondaryRay.toString());
        return secondaryRay;


        // EVTL MUSS HIER NOCH WAS PASSIEREN. KEINE AHNUNG.
        // ERSTMAL MACHTDAS DING NUR NEN RAY AUS ORIGIN UND DIRECTION

    }

    private RgbColor traceRay(Ray ray) {
        currentRecursion++;
        // CHECK FOR INTERSECTION
        float t = POSITIVE_INFINITY; // distance
        SceneObject hitObject = null;
        SceneObject shadowObject = null;
        boolean hit = false;
        RgbColor calcColor = new RgbColor(0, 0, 0);
        RgbColor bgColor = new RgbColor(0,0,0);


            for (SceneObject object : mScene.getShapeList()) {

                float tmin = object.isHitByRay(ray);
                if (tmin != -1 && tmin < t) {
                    t = tmin;
                    //Log.print(" " + t);
                    hitObject = object;
                    hit = true;
                }

            }

            if (hit) {

                for (Light light : mScene.getLightList()) {
                    Ray secondaryRay = createSecondaryRay(ray.at(t), light.getPosition());
                    Material mat = hitObject.getMaterial();
                    boolean inShadow = false;

                    for (SceneObject shadowingObject : mScene.getShapeList()) {
                        if (shadowingObject != hitObject) {
                            float tmin2 = shadowingObject.isHitByRay(secondaryRay);
                            float tLight = (light.getPosition().sub(secondaryRay.getOrigin())).length();
                            //Log.print("tmin2: " + tmin2 + " tLight: " + tLight);



                            if (tmin2 > 0 && tmin2 < tLight) {
                                //Log.print("tmin2: " + tmin2 + " tLight: " + tLight);
                                //t = tmin2;
                                //Log.print("" + hitObjectColor);
                                //hitObject = object;
                                shadowObject = shadowingObject;
                                inShadow = true;
                                break;
                            }
                        }

                    }

                    if (!inShadow){
                        calcColor = calcColor.add(mat.calculateColor(secondaryRay, light, hitObject, mScene) );
                    }
                    else {
                    }


                }
                calcColor = calcColor.add( hitObject.getColor().multScalar(Globals.ambient) );

            }



        return calcColor;

    }

}
