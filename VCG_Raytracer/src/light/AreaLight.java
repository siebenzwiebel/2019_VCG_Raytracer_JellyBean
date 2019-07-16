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

        // create a lot of point lights with fraction of intensity. easy.
        float half = Globals.lightSamples;
        float step = length/half;
        float stepHalf = step*.5f;
        float lengthHalf = length * .5f;
        float xBoundary = (position.x + lengthHalf - stepHalf);
        float zBoundary = (position.z + lengthHalf - stepHalf);
        Log.print("step: " + step);
        for (float x =  position.x - lengthHalf + stepHalf; x <= xBoundary; x += step){
            for (float z = position.z - lengthHalf + stepHalf; z <= zBoundary; z += step){
                float randx = Globals.rand(-1,1)*stepHalf;
                float randcosx = (float)Math.cos(Globals.rand(-1,1))*stepHalf;

                float randz = Globals.rand(-1,1)*.5f*stepHalf;
                float randcosz = (float)Math.cos(Globals.rand(-1,1))*stepHalf;

                PointLight sampleLight = new PointLight(new Vec3(x+randcosx, position.y, z+randcosz), color, intensity/density);
                //Log.print("x: " + x + " y: " + position.y + "z: " + z);
                renderScene.addLight(sampleLight);
            }
        }


    }

    public int getDensity() {
        return density;
    }
    public float getLength(){ return length; }
}
