package shape;

import material.Material;
import scene.SceneObject;
import utils.RgbColor;
import utils.algebra.Vec3;

public class Shape extends SceneObject {
    public Shape(Vec3 pos, Material material) {
        super(pos, material);
    }

    public boolean intersect(){
        return true;
    }

    public Material getMaterial(){return material;}


}
