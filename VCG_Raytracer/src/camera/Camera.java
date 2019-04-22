package camera;

import scene.SceneObject;
import utils.algebra.Vec3;

public class Camera extends SceneObject {

    protected Vec3 lookAt;

    public Camera(Vec3 mPosition, Vec3 lookAt) {
        super(mPosition);
        this.lookAt = lookAt;
    }

}