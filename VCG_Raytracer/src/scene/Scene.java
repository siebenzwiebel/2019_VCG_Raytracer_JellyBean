package scene;

import camera.PerspectiveCamera;
import utils.algebra.Vec3;
import utils.io.Log;

import java.util.*;

public class Scene {

    private ArrayList<SceneObject> mShapeList; // TODO: change SceneObject for class Shape
    private ArrayList<SceneObject> mLightList; // TODO: change SceneObject for class Light

    private SceneObject mSceneCamera;

    public PerspectiveCamera perspCamera;

    public ArrayList<SceneObject> getShapeList() {
        return mShapeList;
    }
    public ArrayList<SceneObject> getLightList() {
        return mLightList;
    }

    public Scene(){
        Log.print(this, "Init");
        mShapeList = new ArrayList<>();
        mLightList = new ArrayList<>();
    }

    public void createPerspCamera(Vec3 camPos, Vec3 viewPoint, Vec3 upVec, double viewAngle, int screenWidth, int screenHeight){
        Log.error(this, "Cannot set camera!");
        perspCamera = new PerspectiveCamera(camPos, viewPoint, upVec, viewAngle, screenWidth, screenHeight);
    }

    public void addObject(SceneObject pObject){
        mShapeList.add(pObject);
    }

}
