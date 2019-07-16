package material;

import light.Light;
import raytracer.Ray;
import scene.Scene;
import scene.SceneObject;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Phong extends Material {

    private final float k_a;
    private final float k_d;
    private final float k_s;
    private final float reflectivity;
    private final float refractivity;
    private final RgbColor matColor;

    public Phong(RgbColor matColor, float reflectivity, float refractivity, float k_a, float k_d, float k_s) {
        this.reflectivity = reflectivity;
        this.refractivity = refractivity;
        this.k_a = k_a;
        this.k_d = k_d;
        this.k_s = k_s;
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

        RgbColor phongColor = new RgbColor(0,0,0);
        Vec3 campos = scene.perspCamera.getPos();

        Vec3 intersection = lightRay.getOrigin();
        Vec3 N = object.getNormal(intersection);
        Vec3 L = lightRay.getDirection().normalize();

        // AMBIENT
        // I_a * k_a

        RgbColor I_in = light.getColor();

        RgbColor lightAmbient = matColor.multScalar(k_a);

        // DIFFUSE
        // L_d = k_d * I_in( N*L)

        float L_N;
        // intensity of reflected light is proportional to cosine of angle between surface and incoming light direction
        // heißt: I_l ist proportional zu cos(theta) (=L*N)

        float cosTheta = L.scalar(N);
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

        RgbColor lightDiffuse = I_in.multScalar(L_N * k_d);


        // SPECULAR
        // I_in * k_s * ( ( n + 2 ) / (2 * PI ) ) * ( R * V )^n

        RgbColor lightSpecular = new RgbColor(0,0,0);

        float n = 30;

        Vec3 V = ((campos).sub(lightRay.getOrigin())).normalize();

        Vec3 R = ((N.multScalar(N.scalar(L))).multScalar(2)).sub(L);


        float R_V = R.scalar(V);

        if (R_V < 1.0 && R_V > 0.0){
            lightSpecular = I_in.multScalar( (k_s * ((n+2)/(2* (float) Math.PI)) * (float) Math.pow(R_V,n)) );
        }



        //Log.print("lambertColor: " + lambertColor);
        //Log.print("A: " + lightAmbient.toString() + " D: " + lightDiffuse.toString() + " S: " + lightSpecular.toString());
        phongColor = (((phongColor.add(lightAmbient)).add(lightDiffuse)).add(lightSpecular));

        return phongColor.multScalar(light.getIntensity());
    }

    public RgbColor getColor() {
        return matColor;
    }
}
