package material;

import light.Light;
import raytracer.Ray;
import scene.SceneObject;
import utils.RgbColor;
import utils.algebra.Vec3;
import utils.io.Log;

public class LambertMaterial extends Material {


    public RgbColor calculateColor(Ray lightRay, Light light, SceneObject object){

        RgbColor lambertColor;

        Vec3 normalVector = (lightRay.getOrigin().sub(object.getPosition())).normalize();
        Vec3 lightVector = (lightRay.getDirection().sub(lightRay.getOrigin())).normalize();

        // calculate dis shit
        // Id = Il*rd
        // AMBIENT - SHOULD BE OBJECT COLOR?
        float lightAmbient = 0;
        //
        // DIFFUSE
        float lightDiffuse;
        // intensity of reflected light is proportional to cosine of angle between surface and incoming light direction
        // heißt: I_l ist proportional zu cos(theta) (=L*N)

        float cosTheta = (float) lightVector.scalar(normalVector);
        //Log.print("cosTheta: " + cosTheta);
        if (cosTheta == 0){
            lightDiffuse = 1;
        }
        else if (cosTheta == 1){
            lightDiffuse = 0;
        }
        else if (cosTheta < 0){
            lightDiffuse = 0;
        }
        else {
            lightDiffuse = cosTheta;
        }
        //Log.print("test: " + lightDiffuse);

        // L_d = k_d * Imax(0, N*L)
        lambertColor = new RgbColor(
                lightDiffuse * light.getColor().red() * light.getIntensity(),
                lightDiffuse * light.getColor().green() * light.getIntensity(),
                lightDiffuse * light.getColor().blue() * light.getIntensity()
        );
        //Log.print("lambertColor: " + lambertColor);


        return lambertColor;
    }

}
