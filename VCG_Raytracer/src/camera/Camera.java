package camera;

import raytracer.Ray;
import utils.algebra.Vec3;

public abstract class Camera {



    final Vec3 e;
    Vec3 g;
    private final Vec3 t;
    final Vec3 u;
    final Vec3 v;
    final Vec3 w;
    final int width;
    final int height;


    Camera(Vec3 e, Vec3 g, Vec3 t, int width, int height) {
        this.e = e;
        this.g = g;
        this.t = t;
        this.width = width;
        this.height = height;
        Vec3 gCalc = calcg(e, g);

        //          g
        //  w = - -----
        //         |g|

        this.w = gCalc.multScalar(1.0f / gCalc.length()).multScalar(-1.0f);

        // Now calculationg vector u
        // The formuar for this vector looks like this
        //      t x w
        // u = ---------
        //      |t x w|

        this.u = (t.cross(w)).multScalar(1.0f / t.cross(w).length());

        // Now calculating vector v
        // The formular for this vector looks like this
        //
        // v = w x u
        //
        this.v = u.cross(w);
    }

    // --Commented out by Inspection (2019-06-27 11:55):public abstract Ray rayFor(int x, int y);

    @Override
    public String toString() {
        return "Camera{" +
                "e=" + e +
                ", g=" + g +
                ", t=" + t +
                ", u=" + u +
                ", v=" + v +
                ", w=" + w +
                '}';
    }

    Vec3 calcg(Vec3 e, Vec3 g){
        //Log.print("g: " + g.toString());
        //Log.print("e: " + e.toString());
        //Log.print("g-e: " + (g.sub(e)).toString());
        return g.sub(e);
    }

    public Vec3 getPos() {
        return e;
    }
}