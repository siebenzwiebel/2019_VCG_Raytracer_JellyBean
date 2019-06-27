package light;

import utils.RgbColor;
import utils.algebra.Vec3;

public abstract class Light {

    private final Vec3 position;
    private final RgbColor color;
    private final float intensity; // 0-1

    Light(Vec3 position, RgbColor color, float intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }

    public Vec3 getPosition() {
        return position;
    }

    public RgbColor getColor() {
        return color;
    }

    public float getIntensity() {
        return intensity;
    }

    @Override
    public String toString() {
        return "Light{" +
                "position=" + position +
                ", color=" + color +
                ", intensity=" + intensity +
                '}';
    }
}
