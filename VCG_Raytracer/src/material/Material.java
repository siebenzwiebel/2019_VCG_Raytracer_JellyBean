package material;

import light.Light;
import raytracer.Ray;
import scene.SceneObject;
import utils.RgbColor;

public abstract class Material {

    public abstract RgbColor calculateColor(Ray lightRay, Light light, SceneObject object);

}
