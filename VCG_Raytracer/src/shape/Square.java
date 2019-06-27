package shape;

import material.Material;
import raytracer.Ray;
import utils.Globals;
import utils.algebra.Vec3;

public class Square extends Plane{

    private final Vec3 p1;
    private final Vec3 p2;
    private final Vec3 p3;
    private final Vec3 e1;
    private final Vec3 e2;


    public Square(Vec3 pos, Vec3 p2, Vec3 p3, Material material, Vec3 direction) {
        super(pos, material, direction);
        this.p1 = pos;
        this.p2 = p2;
        this.p3 = p3;
        this.e1 = p2.sub(p1);
        this.e2 = p3.sub(p1);

    }

    private Vec3 getNormal(){
        return (e2.cross(e1)).normalize();
    }

    @Override
    public float isHitByRay(Ray ray){

        // FIRST CHECK FOR PLANE INTERSECTION
        Vec3 normal = this.getNormal();
        float denom = normal.scalar(ray.getDirection());
        if (Math.abs(denom) > Globals.epsilon){
            //Log.print("inside loop1");
            Vec3 difference = p1.sub(ray.getOrigin());
            float t = difference.scalar(normal) / denom;
            if (t > Globals.epsilon){
                //Log.print("inside loop2");
                // CHECK IF INTERSECTION IS IN BOUNDARIES

                Vec3 intersectionPoint = (ray.getOrigin()).add(ray.getDirection().multScalar(t));
                Vec3 v = intersectionPoint.sub(p1);

                float width = e1.length();
                float height = e2.length();
                float proj1 = (v.scalar(e1))/width;
                float proj2 = (v.scalar(e2))/height;

                if ((proj1 < width && proj1 > 0) && (proj2 < height && proj2 > 0)) {
                    return t;
                }
                else {
                    return -1;
                }

            }
            else {
                return -1;
            }
        }
        return -1;
    }




}
