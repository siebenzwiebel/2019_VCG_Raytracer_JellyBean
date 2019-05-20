package scene;

import camera.PerspectiveCamera;
import light.Light;
import utils.algebra.Vec3;
import utils.io.Log;

import java.util.*;

public class Scene {

    private ArrayList<SceneObject> mShapeList; // TODO: change SceneObject for class Shape
    private ArrayList<Light> mLightList; // TODO: change SceneObject for class Light

    private SceneObject mSceneCamera;

    public PerspectiveCamera perspCamera;

    public ArrayList<SceneObject> getShapeList() {
        return mShapeList;
    }
    public ArrayList<Light> getLightList() {
        return mLightList;
    }

    public Scene(){
        //Log.print(this, "Init");
        mShapeList = new ArrayList<>();
        mLightList = new ArrayList<>();
    }

    public void createPerspCamera(Vec3 camPos, Vec3 viewPoint, Vec3 upVec, double viewAngle, int screenWidth, int screenHeight){
        perspCamera = new PerspectiveCamera(camPos, viewPoint, upVec, viewAngle, screenWidth, screenHeight);
    }

    public void addObject(SceneObject pObject){
        mShapeList.add(pObject);
    }

    public void addLight(Light pObject){
        mLightList.add(pObject);
    }

}
