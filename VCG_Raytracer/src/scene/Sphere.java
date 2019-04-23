package scene;

import raytracer.Ray;
import utils.algebra.Vec3;

public class Sphere extends SceneObject {
    private float radius;

    public Sphere(Vec3 pos, float radius) {
        super(pos);
        this.radius = radius;
    }

    public boolean isHitByRay(Ray ray){

        Vec3 position = ray.getOrigin();
        Vec3 direction = ray.getDirection();

        Float b = 2*(position.x*direction.x + position.y*direction.y +position.z*direction.z );
        Float c = position.x*position.x + position.y*position.y + position.z*position.z - radius*radius;

        float discriminant = b*b - 4*c;

        if(discriminant<0){
            return false;
        }else if(discriminant==0){
            return true;
        } else { //discriminant >0
            return true;
        }
    }

}
