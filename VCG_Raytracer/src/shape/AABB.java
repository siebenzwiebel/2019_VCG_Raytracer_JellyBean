package shape;

import material.Material;
import raytracer.Ray;
import utils.algebra.Vec3;

public class AABB extends Shape{
    private final Vec3 minP;
    private final Vec3 maxP;
    private final Vec3[] normals = new Vec3[6];
    private final float[] D = new float[6];

    public AABB(Vec3 pos, Material material, Vec3 minP, Vec3 maxP) {
        super(pos, material); // We do actually not use this. Thanks Shape class...
        this.minP = minP;
        this.maxP = maxP;

        normals[0] = new Vec3(0.0f, 1.0f, 0.0f);
        normals[1] = new Vec3(1.0f, 0.0f, 0.0f);
        normals[2] = new Vec3(0.0f, 0.0f, 1.0f);
        normals[3] = new Vec3(0.0f, -1.0f, 0.0f);
        normals[4] = new Vec3(-1.0f, 0.0f, 0.0f);
        normals[5] = new Vec3(0.0f, 0.0f, -1.0f);

        Vec3 u = maxP;
        Vec3 v = minP;

        for ( int i = 0; i < 3; i++ ) {
            D[i] = normals[i].scalar(u);
            D[i+3] = normals[i+3].scalar(v);
        }

    }

    public Vec3 getMinP() {
        return minP;
    }

    public Vec3 getMaxP() {
        return maxP;
    }

    public Vec3 getNormal(){
        return normals[6];
    }

    public float isHitByRay(Ray ray){
        float tNear = Float.NEGATIVE_INFINITY;
        float tFar = 9999;//Float.POSITIVE_INFINITY;
        Vec3 n;

        float vd, vn, t;

        // Check every plane defining the convex body
        for ( int i = 0 ; i < 6; i++ ) {
            n = normals[i];
            vd = n.x * ray.getDirection().x + n.y * ray.getDirection().y + n.z * ray.getDirection().z;
            vn = D[i] - (n.x * ray.getOrigin().x + n.y * ray.getOrigin().y + n.z * ray.getOrigin().z);

            if ( vd == 0.0f ) {
                if ( vn < 0.0f ) {
                    return -1;	// ray is parallel to the plane and was casted from outside
                }
            } else {
                t = vn / vd;

                if ( vd > 0.0f ) {
                    // it's a back-face
                    if ( t < 0.0f ) {
                        return -1;	// Polyhedron is behind the ray
                    }

                    if ( t < tFar ) {
                        tFar = t;
                    }
                } else {
                    // it's a front-face
                    if ( t > tNear ) {
                        tNear = t;
                    }
                }

                if ( tNear > tFar ) {
                    return -1;
                }
            }
        }

        return tNear;
    }





}
