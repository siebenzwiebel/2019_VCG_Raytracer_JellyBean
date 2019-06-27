package scene;

import material.Material;
import raytracer.Ray;
import utils.algebra.Vec3;

public class SceneObject {

    public Vec3 mPosition;

    protected final Material material;
    private Vec3 normal;

    protected SceneObject(Vec3 pos, Material material){

        this.mPosition = pos;
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

    public Material getMaterial() {
        return material;
    }

    public Vec3 getNormal(Vec3 intersection){return normal;}
}
