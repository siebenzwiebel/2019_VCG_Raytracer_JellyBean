package shape;

import material.Material;
import raytracer.Ray;
import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Plane extends Shape {

    public Vec3 direction;

    public Plane(Vec3 pos, RgbColor color, Material material, Vec3 direction) {
        super(pos, color, material);
        this.direction = direction;
    }

    public Vec3 getNormal(Vec3 pos, Vec3 dir){
        Vec3 normal = dir.sub(pos).normalize();
        return normal;
    }

    public float isHitByRay(Ray ray){

        Vec3 normal = this.getNormal(this.mPosition, this.direction);
        float denom = this.mPosition.scalar(normal.multScalar(-1));
        if (Math.abs(denom) > Globals.epsilon){
            float t = -1 * (denom + ray.getOrigin().scalar(normal)) / ray.getDirection().scalar(normal);
            if (t >= 0){
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
                ", color=" + color +
                ", material=" + material +
                '}';
    }
}
