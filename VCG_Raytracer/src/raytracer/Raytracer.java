/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1. Send primary ray
    2. intersection test with all shapes
    3. if hit:
    3a: send secondary ray to the light source
    3b: 2
        3b.i: if hit:
            - Shape is in the shade
            - Pixel color = ambient value
        3b.ii: in NO hit:
            - calculate local illumination
    4. if NO hit:
        - set background color

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package raytracer;

import camera.PerspectiveCamera;
import scene.Scene;
import scene.SceneObject;
import ui.Window;
import utils.*;
import utils.algebra.Matrix4x4;
import utils.algebra.Vec2;
import utils.algebra.Vec3;
import utils.algebra.Vec4;
import utils.io.Log;

import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;
    private Scene mScene;
    private long mtStart;

    public Raytracer(Scene scene, Window renderWindow){
        Log.print(this, "Init");
        mRenderWindow = renderWindow;
        mScene = scene;
        mtStart = System.currentTimeMillis();
        mBufferedImage = renderWindow.getBufferedImage();
    }

    public void exportRendering(BufferedImage renderImage){
        mRenderWindow.exportRenderingToFile(renderImage, String.valueOf(stopTime(mtStart)), 1);
    }

    private static double stopTime(long tStart){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        return tDelta / 1000.0;
    }

    public void renderScene(){
        Log.print(this, "Prepare rendering at " + String.valueOf(stopTime(mtStart)));

        for(float y = 0f; y < mBufferedImage.getHeight(); y++){
            for(float x = 0; x < mBufferedImage.getWidth(); x++){

                    Vec2 screenPosition = new Vec2(x, y);
                    RgbColor unusedColor = this.sendPrimaryRay(screenPosition);
                    mRenderWindow.setPixel(mBufferedImage, unusedColor, new Vec2(x, y));
            }
        }
        exportRendering(mBufferedImage);
    }

    private Ray createPrimaryRay(Vec3 position, Vec3 direction){

        return new Ray(position, direction);

    }

    private RgbColor sendPrimaryRay(Vec2 pixelPoint){

        int x = (int) pixelPoint.x;
        int y = (int) pixelPoint.y;

        Ray ray = mScene.perspCamera.rayFor(x, y);
        //Log.print(ray.toString());




    /*
        Vec3 defaultPixel = (mScene.perspCamera.pixelToNDC(pixelPoint)).normalize();
        //Log.print("defaultpixel: " + defaultPixel.toString());

        Vec3 worldOrigin = mScene.perspCamera.getPos();

        Vec3 camLookAt = mScene.perspCamera.getLookAt();

        Vec3 camWorldUp = mScene.perspCamera.getWorldUp();
        Vec3 camUp = mScene.perspCamera.getUp();
        //Log.print(camUp.toString() + camWorldUp.toString());

        //Log.print("camLookAt: " + camLookAt.toString());
        //Log.print("worldLookAt: " + worldLookAt.toString());
        //Log.print("worldLookAt: " + worldLookAt.toString());

        // die mATRIXHIER STIRBT BEI 0 0 0 DAS IST ECHT DOOF

        // WAS WIRD HIER ÜBERHAUPT ROTIERT? WiRD DER bUMs NEU AUSGErIChTET?#
                                // IST DER WORLD UP RICHTIG??????

        Matrix4x4 cameraToWorld = Matrix4x4.directionalRotationMatrix(camUp, camWorldUp);

        Vec4 direction4D = cameraToWorld.multVec3(new Vec4(defaultPixel.x, defaultPixel.y, defaultPixel.z, 0));
        Vec3 worldDirection = new Vec3(direction4D.x, direction4D.y, direction4D.z);
        ;
        //worldDirection = worldDirection.sub(worldOrigin);
        // worldDirection = worldDirection.normalize(); // WHY SHIT SO SMALL????
        //Log.print(worldDirection.toString());
        worldDirection = worldDirection.add(mScene.perspCamera.getLookAt()).normalize();
        //Log.print("worldDirection: " + worldDirection.toString());


        Ray primaryRay = createPrimaryRay(worldOrigin, worldDirection);



        /*
        Matrix4x4 rotationMatrix = Matrix4x4.directionalRotationMatrix(defaultOrigin, worldOrigin);


        //Rotating the pixel to match the rotation of the camera(must be done as a 4DVector)
        Vec4 direction4D = rotationMatrix.multVec3(new Vec4(direction.x, direction.y, direction.z, 1));
        //Back to 3DVector
        direction = new Vec3(direction4D.x, direction4D.y, direction4D.z);
        //Putting the rotated pixel in front of the camera
        //direction = direction.add(mScene.perspCamera.getLookAt());
        //Log.print("direction: " + direction.toString());
        */

        Ray primaryRay = createPrimaryRay(ray.getOrigin(), ray.getDirection());

        return traceRay(primaryRay);
    }

    private RgbColor traceRay(Ray inRay){

        // INTERSECTION

        RgbColor hitColor = new RgbColor(1,1,1);

        for (SceneObject object:mScene.getShapeList()){
            if(object.isHitByRay(inRay)) {
                return hitColor;
            }
        }
        return new RgbColor(0,0,0);



        /*

        // here comes all the magic
        //
        //
        //
        // ...
        // this could be your favourite color!
        //Log.print("X: " + direction.x + " Y: " + direction.y + " Z: " + direction.z);
        //Log.print("x: " + (direction.x+1)/2 + " y: " + (direction.y+1)/2 + " z" + (direction.z+1)/2);
        float color = direction.x + direction.y + direction.z;
         */


        //Vec3 direction = inRay.getDirection();
        //Log.print("color: " + direction.toString());
        //return new RgbColor((direction.x+1)/2,(direction.y+1)/2,(direction.z+1)/2);

    }

}
