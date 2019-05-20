package material;

import light.Light;
import raytracer.Ray;
import scene.Scene;
import scene.SceneObject;
import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec3;
import utils.io.Log;

public class Lambert
        extends Material {


    public RgbColor calculateColor(Ray lightRay, Light light, SceneObject object, Scene scene){

        RgbColor lambertColor = new RgbColor(0,0,0);
        Vec3 campos = scene.perspCamera.getPos();
        //implement getnormal function for sphere
        Vec3 intersection = lightRay.getOrigin();
        Vec3 normalVector = object.getNormal(intersection);
        Vec3 lightVector = lightRay.getDirection().normalize();

        // AMBIENT
        // I_a * k_a

        float k_a = Globals.ambient;
        RgbColor I_a = new RgbColor(.1f, .1f, .1f);
        RgbColor I_in = light.getColor();

        RgbColor lightAmbient = I_a.multScalar(k_a);

        // DIFFUSE
        // L_d = k_d * I_in( N*L)

        float L_N;
        float k_d = 1 - Globals.ambient;
        // intensity of reflected light is proportional to cosine of angle between surface and incoming light direction
        // heißt: I_l ist proportional zu cos(theta) (=L*N)

        float cosTheta = lightVector.scalar(normalVector);
        //Log.print("cosTheta: " + cosTheta);
        if (cosTheta == 0){
            L_N = 1;
        }
        else if (cosTheta == 1){
            L_N = 0;
        }
        else if (cosTheta < 0){
            L_N = 0;
        }
        else {
            L_N = cosTheta;
        }
        //Log.print("test: " + lightDiffuse);

        RgbColor lightDiffuse = I_in.multScalar(L_N * k_d);


        /*
        // SPECULAR
        // I_in * k_s * ( ( n + 2 ) / (2 * PI ) ) * ( R * V )^n

        RgbColor lightSpecular = new RgbColor(0,0,0);

        float n = 32;
        float k_s = 1 - k_d;
        Vec3 N = normalVector;
        Vec3 L = lightVector;
        Vec3 V = ((campos).sub(lightRay.getOrigin())).normalize();

        Vec3 R = (N.multScalar(N.scalar(L)).sub(L)).multScalar(2);


        float R_V = R.scalar(V);


        for (int i=1; i<n; i++){
            R_V *= R_V;
        }
        if (R_V < 1.0 && R_V > 0.0){
            lightSpecular = I_in.multScalar((k_s * ( ( n + 2 ) / (float)( 2 * Math.PI ) ) * R_V ));
        }
        */


        //Log.print("lambertColor: " + lambertColor);
        //Log.print("A: " + lightAmbient.toString() + " D: " + lightDiffuse.toString() + " S: " + lightSpecular.toString());
        lambertColor = ((lambertColor.add(lightAmbient)).add(lightDiffuse)).multScalar(light.getIntensity());

        return lambertColor;
    }

}
