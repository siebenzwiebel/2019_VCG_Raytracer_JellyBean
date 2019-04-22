package camera;

import utils.algebra.Vec2;
import utils.algebra.Vec3;

public class PerspCam extends Camera {


    private double fov;
    private double focalLength;
    private int width;
    private int height;

    private float aspectRatio;
    private float pX;
    private float pY;

    private Vec3 view;
    private Vec3 up;
    private Vec3 side;
    private Vec3 camUp;

    public PerspCam(Vec3 mPosition, Vec3 lookAt, Vec3 up, double fov, double focalLength, int width, int height) {
        super(mPosition, lookAt);
        this.up = up;
        this.fov = fov;
        this.focalLength = focalLength;
        this.width = width;
        this.height = height;
        this.aspectRatio = width/height;

    }

    public Vec3 calcCamUp(){
        up = up.normalize();
        view = (lookAt.sub(mPosition)).normalize();
        side = (up.cross(view)).normalize();
        up = view.cross(up);
        return up;
    }

    public Vec3 getUp() {
        return up;
    }

    public Vec3 getLookAt() {
        return lookAt;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public Vec3 getSide() {
        return side;
    }

    public double getFov() {
        return fov;
    }

    public Vec3 getPos(){
        return mPosition;
    }

    public Vec3 calculate2DRayDirection(Vec2 pixelPoint){
        double x = (2f*(pixelPoint.x+0.5)/width)-1;
        double y = (2f*(pixelPoint.x+0.5)/width)-1;
        return new Vec3((float)x, (float)y, 0);
    }

}
