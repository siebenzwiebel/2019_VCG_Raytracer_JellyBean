package shape;

import material.Material;
import raytracer.Ray;
import utils.algebra.Matrix4x4;
import utils.algebra.Vec3;
import utils.algebra.Vec4;

public class Sphere extends Shape {
    private final float radius;
    private final Matrix4x4 transformationMatrix;
    private final Matrix4x4 inverseTransformationMatrix;

    public Sphere(Vec3 pos, float radius, Material material) {
        super(pos, material);
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

        float a = direction.scalar(direction);
        float b = 2 * oc.scalar(direction);
        float c = oc.scalar(oc) - radius*radius;

        float discriminant = b*b - 4*a*c;

        if(discriminant<0){
            return -1f;
        }else {
            float root1 = (float) ((-b - Math.sqrt(discriminant)) * 0.5f);
            float root2 = (float) ((-b + Math.sqrt(discriminant)) * 0.5f);

            if (root1 < 0 && root2 < 0)return -1f;
            if (root1 < 0) return  root2;
            else if (root2 < 0) return  root1;
            else if (root2 == root1) return root1;

            else {
                if (root1 < root2) return  root1;
                else return  root2;
            }
        }

    }

    public Vec3 getPos(){
        return this.mPosition;
    }

    private Matrix4x4 calculateTransformationMatrix(Vec3 pos, float scale){
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

    public Vec3 getNormal(Vec3 intersection){
        return (intersection.sub(this.mPosition)).normalize();

    }

    @Override
    public String toString() {
        return "Sphere{" +
                "mPosition=" + mPosition +
                ", reflectivity=" + material.getReflectivity() +
                '}';
    }
}
