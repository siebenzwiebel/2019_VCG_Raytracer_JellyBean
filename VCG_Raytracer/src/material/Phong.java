package material;

import camera.Camera;
import camera.PerspectiveCamera;
import light.Light;
import raytracer.Ray;
import scene.Scene;
import scene.SceneObject;
import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec3;
import utils.io.Log;

public class Phong extends Material {

    protected float k_a;
    protected float k_d;
    protected float k_s;

    public Phong(float k_a, float k_d, float k_s) {
        this.k_a = k_a;
        this.k_d = k_d;
        this.k_s = k_s;
    }

    public RgbColor calculateColor(Ray lightRay, Light light, SceneObject object, Scene scene){

        RgbColor phongColor = new RgbColor(0,0,0);
        Vec3 campos = scene.perspCamera.getPos();
        //implement getnormal function for sphere
        Vec3 intersection = lightRay.getOrigin();
        Vec3 normalVector = object.getNormal(intersection);
        Vec3 lightVector = lightRay.getDirection().normalize();


        // calculate dis shit
        // Id = Il*rd
        // AMBIENT - SHOULD BE OBJECT COLOR?
        //

        // AMBIENT
        // I_a * k_a

        k_a *= Globals.ambient;
        RgbColor I_a = new RgbColor(.1f, .1f, .1f);
        RgbColor I_in = light.getColor();

        RgbColor lightAmbient = I_a.multScalar(k_a);

        // DIFFUSE
        // L_d = k_d * I_in( N*L)

        float L_N;
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



        // SPECULAR
        // I_in * k_s * ( ( n + 2 ) / (2 * PI ) ) * ( R * V )^n

        RgbColor lightSpecular = new RgbColor(0,0,0);

        float n = 12;
        Vec3 N = normalVector;
        Vec3 L = lightVector;
        Vec3 V = ((campos).sub(lightRay.getOrigin())).normalize();

        Vec3 R = ((N.multScalar(N.scalar(L))).multScalar(2)).sub(L);


        float R_V = R.scalar(V);

        if (R_V < 1.0 && R_V > 0.0){
            lightSpecular = I_in.multScalar((k_s * ((n+2)/(2* (float) Math.PI)) * (float) Math.pow(R_V,n)));
        }



        //Log.print("lambertColor: " + lambertColor);
        //Log.print("A: " + lightAmbient.toString() + " D: " + lightDiffuse.toString() + " S: " + lightSpecular.toString());
        phongColor = (((phongColor.add(lightAmbient)).add(lightDiffuse)).add(lightSpecular)).multScalar(light.getIntensity());

        return phongColor;
    }

}
