package camera;


import raytracer.Ray;
import utils.algebra.Vec3;


public class PerspectiveCamera extends Camera{

    public final double angle;

    public PerspectiveCamera(final Vec3 e, final Vec3 g, final Vec3 t, double angle, int width, int height) {
        super(e, g, t, width, height);
        this.angle = angle;
        this.g = calcg(e, g);
    }

    public Ray rayFor(int x, int y) {
        // same origin, therefore o = e;
        final Vec3 o = this.e;
        float w = width;
        float h = height;


        //               h
        //              ---
        //               2             w-1                w-1
        // r = -w * ( ------ ) + (x - ----- ) * u + (y - ----- ) * v
        //            tan a             2                  2


        final double firstBracket = (double) h/2.0/Math.tan(this.angle/2.0);
        final double xBracket = (double)x-(((double)w-1.0)/2.0);
        final double yBracket = (double)y-(((double)h)-1.0)/2.0;


        final Vec3 r = this.w.multScalar(-1.0f).multScalar((float)firstBracket).add(this.u.multScalar((float)xBracket).add(v.multScalar((float)yBracket)));

        final Vec3 d = r.normalize();


        return new Ray(o, d);
    }

    @Override
    public String toString() {
        return "PerspectiveCamera{" +
                "angle=" + angle +
                '}';
    }

}