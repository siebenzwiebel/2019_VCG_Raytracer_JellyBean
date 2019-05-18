package scene;

import material.Material;
import raytracer.Ray;
import utils.RgbColor;
import utils.algebra.Vec3;

public class SceneObject {

    public Vec3 mPosition;
    protected RgbColor color;
    protected Material material;

    public SceneObject(Vec3 pos, RgbColor color, Material material){

        this.mPosition = pos;
        this.color = color;
        this.material = material;
    }

    public float isHitByRay(Ray ray){
        return -1;
    }

    public Ray invMatTransform(Ray ray){
        return ray;
    }

    public Vec3 getPosition(){
        return mPosition;
    }

    public void setPosition(Vec3 position){
        mPosition = position;
    }

    public void moveTo(Vec3 transition){
        mPosition = mPosition.add( transition );
    }

    public RgbColor getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }
}
