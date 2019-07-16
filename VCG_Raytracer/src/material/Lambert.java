package material;

import light.Light;
import raytracer.Ray;
import scene.Scene;
import scene.SceneObject;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Lambert extends Material {

    private final float reflectivity;
    private final float refractivity;
    private final float k_a;
    private final float k_d;
    private final RgbColor matColor;



    public Lambert(RgbColor matColor, float reflectivity, float refractivity, float k_a, float k_d) {
        this.k_a = k_a;
        this.k_d = k_d;
        this.reflectivity = reflectivity;
        this.refractivity = refractivity;
        this.matColor = matColor;
    }



    public float getReflectivity() {
        return reflectivity;
    }
    public float getRefractivity() {
        return refractivity;
    }
    public float getAlbedo(){return k_a;}

    public RgbColor calculateAmbientColor(Ray lightRay, Light light, SceneObject object, Scene scene){

        // AMBIENT
        // I_a * k_a
        return (matColor.multScalar(k_a)).multScalar(light.getIntensity());

    }

    public RgbColor calculateColor(Ray lightRay, Light light, SceneObject object, Scene scene){

        RgbColor lambertColor = new RgbColor(0,0,0);

        //implement getnormal function for sphere
        Vec3 intersection = lightRay.getOrigin();
        Vec3 normalVector = object.getNormal(intersection);
        Vec3 lightVector = lightRay.getDirection().normalize();

        // AMBIENT
        // I_a * k_a

        RgbColor I_in = light.getColor();

        RgbColor lightAmbient = matColor.multScalar(k_a);
        RgbColor lightDiffuse;


            // DIFFUSE
            // L_d = k_d * I_in( N*L)

            float L_N;

            // intensity of reflected light is proportional to cosine of angle between surface and incoming light direction
            // heißt: I_l ist proportional zu cos(theta) (=L*N)

            float cosTheta = lightVector.scalar(normalVector);

            if (cosTheta == 0) {
                L_N = 1;
            } else if (cosTheta == 1) {
                L_N = 0;
            } else if (cosTheta < 0) {
                L_N = 0;
            } else {
                L_N = cosTheta;
            }

            lightDiffuse = I_in.multScalar(L_N * k_d);



        //Log.print("lambertColor: " + lambertColor);
        //Log.print("A: " + lightAmbient.toString() + " D: " + lightDiffuse.toString() + " S: " + lightSpecular.toString());
        lambertColor = ((lambertColor.add(lightAmbient)).add(lightDiffuse));
        return lambertColor.multScalar(light.getIntensity());
    }

}
