package shape;

import material.Material;
import raytracer.Ray;
import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Plane extends Shape {

    public Vec3 direction;

    public Plane(Vec3 pos, Material material, Vec3 direction) {
        super(pos, material);
        this.direction = direction;
    }

    public Vec3 getNormal(Vec3 intersection){
        Vec3 normal = (direction.sub(mPosition)).normalize();
        return normal;
    }

    public float isHitByRay(Ray ray){

        Vec3 normal = this.getNormal(ray.getOrigin());
        float denom = normal.scalar(ray.getDirection());
        if (Math.abs(denom) > Globals.epsilon){
            Vec3 difference = mPosition.sub(ray.getOrigin());
            float t = difference.scalar(normal) / denom;
            if (t > Globals.epsilon){
                return t;
            }
            else {
                return -1;
            }
        }
        return -1;
    }

    public Vec3 getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "direction=" + direction +
                ", mPosition=" + mPosition +
                ", material=" + material +
                '}';
    }
}
