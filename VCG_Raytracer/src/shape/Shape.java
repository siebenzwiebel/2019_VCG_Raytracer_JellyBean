package shape;

import material.Material;
import scene.SceneObject;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Shape extends SceneObject {
    public Shape(Vec3 pos, RgbColor color, Material material) {
        super(pos, color, material);
    }

    public boolean intersect(){
        return true;
    }


}
