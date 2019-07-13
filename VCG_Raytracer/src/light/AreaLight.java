package light;

import material.Lambert;
import shape.Square;
import utils.RgbColor;
import utils.algebra.Vec3;
import scene.Scene;


public class AreaLight{

    public static float length;
    public static Vec3 positionAreaLight;
    public static int density;
    public static float intensityAreaLigth;
    public static Vec3 colorAreaLigth;

    public AreaLight(float length, Vec3 positionAreaLight,int density,float intensityAreaLigth,Vec3 colorAreaLigth)
    {
        this.length=length;
        this.positionAreaLight=positionAreaLight;
        this.density=density;
        this.intensityAreaLigth=intensityAreaLigth;
        this.colorAreaLigth=colorAreaLigth;
    }


    public static void addAreaLigth(Scene renderScene,AreaLight areaLight){
        renderScene.addObject(new Square(new Vec3(-length/2+positionAreaLight.x, positionAreaLight.y, -length/2+positionAreaLight.z),
                                         new Vec3(-length/2+positionAreaLight.x, positionAreaLight.y,length/2+positionAreaLight.z),
                                         new Vec3(length/2+positionAreaLight.x,positionAreaLight.y,-length/2+positionAreaLight.z), new Lambert(new RgbColor(1,1,1),
                                        0, 0, 1, 0), new Vec3(-1, -1, -16)));

        PointLight white = new PointLight(new Vec3(positionAreaLight.x, positionAreaLight.y, positionAreaLight.z), new RgbColor(colorAreaLigth.x, colorAreaLigth.y, colorAreaLigth.z), intensityAreaLigth);
        renderScene.addLight(white);
    }



    public static int getDensity() {
        return density;
    }
}
