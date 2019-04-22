package raytracer;

import utils.algebra.Vec3;

public class Ray {

    private Vec3 startPoint, direction;

    public Ray(Vec3 startPoint, Vec3 direction, float distance) {
        this.startPoint = startPoint;
        this.direction = direction;
    }

    public Vec3 getStartPoint() {
        return startPoint;
    }

    public Vec3 getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "startPoint=" + startPoint +
                ", direction=" + direction +
                '}';
    }
}
