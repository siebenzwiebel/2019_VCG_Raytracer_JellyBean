package shape;

import scene.SceneObject;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Shape extends SceneObject {
    public Shape(Vec3 pos, RgbColor color) {
        super(pos, color);
    }

    public boolean intersect(){
        return true;
    }
}
