package shape;

import material.Material;
import raytracer.Ray;
import utils.RgbColor;
import utils.algebra.Matrix4x4;
import utils.algebra.Vec3;
import utils.algebra.Vec4;
import utils.io.Log;

public class Sphere extends Shape {
    private float radius;
    private Matrix4x4 transformationMatrix;
    private Matrix4x4 inverseTransformationMatrix;

    public Sphere(Vec3 pos, float radius, RgbColor color, Material material) {
        super(pos, color, material);
        this.radius = radius;
        this.transformationMatrix = calculateTransformationMatrix(pos, radius);
        this.inverseTransformationMatrix = transformationMatrix.invert();

    }

    public float isHitByRay(Ray ray){

        Vec4 ray4DDirection = new Vec4(ray.getDirection().x, ray.getDirection().y, ray.getDirection().z, 0);
        Vec4 ray4DOrigin = new Vec4(ray.getOrigin().x, ray.getOrigin().y, ray.getOrigin().z, 1);
        Vec3 oc = (ray.getOrigin()).sub(mPosition);

        Vec4 newRay4DDirection = inverseTransformationMatrix.multVec3(ray4DDirection).normalize();
        Vec4 newRay4DOrigin = inverseTransformationMatrix.multVec3(ray4DOrigin);

        // to 3d
        Vec3 direction = new Vec3(newRay4DDirection.x, newRay4DDirection.y, newRay4DDirection.z);
        Vec3 position = new Vec3(newRay4DOrigin.x, newRay4DOrigin.y, newRay4DOrigin.z);

        float a = direction.scalar(direction);
        float b = 2*oc.scalar(direction);
        float c = oc.scalar(oc) - radius*radius;

        float discriminant = b*b - 4*a*c;

        if(discriminant<0){
            return -1f;
        }else {
            return (-b - (float)Math.sqrt(discriminant)) / (2 * a);
        }
    }

    public Vec3 getPos(){
        return this.mPosition;
    }

    public Matrix4x4 calculateTransformationMatrix(Vec3 pos, float scale){
        Matrix4x4 transMat = new Matrix4x4();
        Vec4 pos4D = new Vec4(pos.x, pos.y, pos.z, 1);
        transMat = transMat.translateXYZW(pos4D).scale(scale);
        return transMat;
    }

    public Vec3 matTransform(Vec3 ray){
        // apply transformationMatrix to ray
        Vec4 ray4D = new Vec4(ray.x, ray.y, ray.z, 1);
        ray4D = transformationMatrix.multVec3(ray4D);
        return new Vec3(ray4D.x, ray4D.y, ray4D.z);
    }



}
