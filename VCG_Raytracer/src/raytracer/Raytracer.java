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

import scene.Scene;
import ui.Window;
import utils.*;
import utils.algebra.Vec2;
import utils.algebra.Vec3;
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

    private Ray createPrimaryRay(float x, float y){
        return new Ray();
    }

    private RgbColor sendPrimaryRay(Vec2 pixelPoint){
        Ray primaryRay = createPrimaryRay(pixelPoint.x, pixelPoint.y);
        return traceRay(primaryRay);
    }

    private RgbColor traceRay(Ray inRay){
        // here comes all the magic
        //
        //
        //
        // ...
        // this could be your favourite color!
        return new RgbColor(0,0.521f,0.743f);
    }

}
