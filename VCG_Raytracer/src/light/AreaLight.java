package light;

import material.Lambert;
import shape.Square;
import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec3;
import scene.Scene;
import utils.io.Log;


public class AreaLight extends Light{

    private float length;
    private int density;


    public AreaLight(Vec3 position, RgbColor color, float intensity, float length, int density, Scene renderScene)
    {
        super(position, color, intensity);
        this.length=length;
        this.density=density;
        if (Globals.lightSamples > 1) {
            // create a lot of point lights with fraction of intensity. easy.
            float step = length / Globals.lightSamples;
            float stepHalf = step * .5f;
            float lengthHalf = length * .5f;

            float xBoundary = (position.x + lengthHalf - stepHalf);
            float zBoundary = (position.z + lengthHalf - stepHalf);

            float xStart = (position.x - lengthHalf + stepHalf);
            float zStart = (position.z - lengthHalf + stepHalf);

            for (float x = xStart; x <= xBoundary; x += step) {
                for (float z = zStart; z <= zBoundary; z += step) {

                    float randx = Globals.rand(-1, 1) * stepHalf;
                    float randcosx = (float) Math.cos(Globals.rand(-1, 1)) * stepHalf;

                    float randz = Globals.rand(-1, 1) * .5f * stepHalf;
                    float randcosz = (float) Math.cos(Globals.rand(-1, 1)) * stepHalf;

                    PointLight sampleLight = new PointLight(new Vec3(x + randx, position.y, z + randz), color, intensity / density);
                    //Log.print("x: " + x + " y: " + position.y + "z: " + z);
                    renderScene.addLight(sampleLight);
                }
            }
        }
        else {
            PointLight sampleLight = new PointLight(position, color, intensity);
            renderScene.addLight(sampleLight);
        }


    }

    public int getDensity() {
        return density;
    }
    public float getLength(){ return length; }
}
