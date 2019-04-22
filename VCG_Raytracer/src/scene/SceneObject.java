package scene;

import utils.algebra.Vec3;

public class SceneObject {

    protected Vec3 mPosition = new Vec3();


    public SceneObject(Vec3 pos){

        this.mPosition = pos;
    }

    public Vec3 getPosition(){
        return mPosition;
    }

    public void setPosition(Vec3 position){
        mPosition = position;
    }

    public void moveTo(Vec3 transition){
        mPosition = mPosition.add( transition );
    }
}
