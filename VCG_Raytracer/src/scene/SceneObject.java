package scene;

import raytracer.Ray;
import utils.RgbColor;
import utils.algebra.Vec3;

public class SceneObject {

    protected Vec3 mPosition;
    protected RgbColor color;

    public SceneObject(Vec3 pos, RgbColor color){

        this.mPosition = pos;
        this.color = color;
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
}
