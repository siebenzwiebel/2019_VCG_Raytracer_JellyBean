package material;

import light.Light;
import raytracer.Ray;
import scene.Scene;
import scene.SceneObject;
import utils.RgbColor;

public abstract class Material {

    public abstract float getReflectivity();
    public abstract float getRefractivity();
    public abstract float getAlbedo();
    public abstract float getMatConst();

    public abstract RgbColor calculateAmbientColor(Ray lightRay, Light light, SceneObject object, Scene scenes);
    public abstract RgbColor calculateColor(Ray lightRay, Light light, SceneObject object, Scene scenes);

}
