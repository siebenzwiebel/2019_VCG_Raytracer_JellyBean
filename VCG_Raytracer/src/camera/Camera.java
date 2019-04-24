package camera;

import raytracer.Ray;
import scene.SceneObject;
import utils.algebra.Vec2;
import utils.algebra.Vec3;
import utils.io.Log;

public abstract class Camera {



    protected final Vec3 e;
    protected Vec3 g;
    protected Vec3 gCalc;
    protected final Vec3 t;
    protected final Vec3 u;
    protected final Vec3 v;
    protected final Vec3 w;
    protected final int width;
    protected final int height;


    public Camera(Vec3 e, Vec3 g, Vec3 t, int width, int height) {
        this.e = e;
        this.g = g;
        this.gCalc = calcg(e, g);
        this.t = t;
        this.width = width;
        this.height = height;


        //          g
        //  w = - -----
        //         |g|
        // TODO FIX FOR g = 0/0/0

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

    public abstract Ray rayFor(int x, int y);

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

    public Vec3 calcg(Vec3 e, Vec3 g){
        Log.print("g: " + g.toString());
        Log.print("e: " + e.toString());
        Log.print("g-e: " + (g.sub(e)).toString());
        return g.sub(e);
    }
}