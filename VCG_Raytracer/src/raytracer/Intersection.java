package raytracer;

import scene.SceneObject;
import shape.Shape;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Intersection {

    private Shape object;
    private Ray inRay;
    private boolean hit;
    private Vec3 hitPoint;
    private Vec3 outRay;
    private Vec3 distance;
    private Vec3 normal;




    public RgbColor isHit(SceneObject object, Ray ray){
        RgbColor color = new RgbColor(1,1,1);
        // what kind of object?
        // get by subclass wäre nice, dann entsprechende methode, aber wäre case oder if abfrage, was wohl nicht so geil ist. how to do this nicely?
        // return object hit point
        return color;
    }

    public Vec3 getHitPoint(){
        return hitPoint;
    }




}
