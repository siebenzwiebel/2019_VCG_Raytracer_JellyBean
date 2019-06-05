package shape;

import material.Material;
import raytracer.Ray;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Square extends Shape{

private Vec3 p0;
private Vec3 p1;
private Vec3 p2;
private Vec3 p3;
private Vec3 a;
private Vec3 b;

    public Square(Vec3 pos, RgbColor color, Material material, Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3) {
        super(pos, color, material);
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public float isHitByRay(Ray ray){
        float t = 0;
        return t;
    }


}
