package shape;

import material.Material;
import raytracer.Ray;
import utils.algebra.Vec3;

public class Triangle extends Shape{

    private final Vec3 e0;
    private final Vec3 e1;
    private final Vec3 e2;
    private final Vec3 ab;
    private final Vec3 ac;
    private final Vec3 normal;
    private float t;

    public Triangle(Vec3 pos, Material material, Vec3 e0, Vec3 e1, Vec3 e2) {
        super(pos, material);
        this.e0 = e0;
        this.e1 = e1;
        this.e2 = e2;
        this.ab = e1.sub(e0);
        this.ac = e2.sub(e0);
        this.normal = ab.cross(ac);

    }

    public float isHitByRay(Ray ray){

        Vec3 pvec = ray.getDirection().cross(ac);
        float det = ab.scalar((pvec));


        if(det < 0.000001f)return -1;
        if(Math.abs(det) < 0.000001f)return -1;

        float invDet = 1/det;

        Vec3 tvec = ray.getOrigin().sub(e0);
        float u = tvec.scalar(pvec) * invDet;

        if(u < 0 || u > 1)return -1;

        Vec3 qvec = tvec.cross(ab);
        float v = ray.getDirection().scalar(qvec) * invDet;
        if(v<0 || u+v >1)return -1;

        t = ac.scalar(qvec) * invDet;

        return t;
    }

    public Vec3 getAb() {
        return ab;
    }

    public Vec3 getAc() {
        return ac;
    }

    public Vec3 getNormal(Vec3 intersection) {
        return normal;
    }
}
