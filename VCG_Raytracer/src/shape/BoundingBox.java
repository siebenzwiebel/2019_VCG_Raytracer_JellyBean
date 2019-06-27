package shape;

import material.Material;
import raytracer.Ray;
import utils.algebra.Vec3;

public class BoundingBox extends Shape{
    private final Vec3 p0;
    private final Vec3 p1;

    public BoundingBox(Vec3 pos, Material material, Vec3 p0, Vec3 p1) {
        super(pos, material);
        this.p0 = p0;
        this.p1 = p1;
    }

    public float isHitByRay(Ray ray){
        float t = 0;
        return t;
    }

}
