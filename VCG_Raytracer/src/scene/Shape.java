package scene;

import utils.algebra.Vec3;

public class Shape extends SceneObject {
    public Shape(Vec3 pos) {
        super(pos);
    }

    public boolean intersect(){
        return true;
    }
}
