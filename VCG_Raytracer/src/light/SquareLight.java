package light;

import utils.RgbColor;
import utils.algebra.Vec3;

public class SquareLight extends Light {

    public SquareLight(Vec3 position, RgbColor color, float intensity) {
        super(position, color, intensity);
    }

    public RgbColor isHitByRay(){
        RgbColor color = new RgbColor(1,1,1);
        return color;
    }

}
