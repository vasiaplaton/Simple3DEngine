package Graphics3d;
import Math.*;

/**
 * The {@code Object} class represents a 3D object in a scene with position, orientation, and transformation capabilities.
 * This class provides methods to manipulate the object's transformation matrix, including translation, scaling, and rotation.
 */
public class Object {

    /**
     * The angle of rotation around the x, y, and z axes.
     */
    protected Vec3D angle = new Vec3D(0.0);

    /**
     * The angle of rotation around the left, up, and look-at vectors.
     */
    protected Vec3D angleLeftUpLookAt = new Vec3D(0.0);

    /**
     * The position of the object in 3D space.
     */
    protected Vec3D position = new Vec3D(0.0);

    /**
     * The transformation matrix of the object.
     */
    protected Matrix4 transformMatrix = Matrix4.identity();

    /**
     * Returns the model matrix of the object.
     * The model matrix represents the transformation of the object in world coordinates.
     *
     * @return the model matrix
     */
    public Matrix4 model() {
        return Matrix4.translation(position).multiple(transformMatrix);
    }

    /**
     * Returns the inverse of the model matrix of the object.
     *
     * @return the inverse model matrix
     */
    public Matrix4 invModel() {
        return Matrix4.view(model());
    }

    /**
     * Returns the left vector of the object.
     *
     * @return the left vector
     */
    public Vec3D left() {
        return transformMatrix.x().normalized();
    }

    /**
     * Returns the up vector of the object.
     *
     * @return the up vector
     */
    public Vec3D up() {
        return transformMatrix.y().normalized();
    }

    /**
     * Returns the look-at vector of the object.
     *
     * @return the look-at vector
     */
    public Vec3D lookAt() {
        return transformMatrix.z().normalized();
    }

    /**
     * Returns the position of the object.
     *
     * @return the position vector
     */
    public Vec3D position() {
        return position;
    }

    /**
     * Returns the angle of the object.
     *
     * @return the angle vector
     */
    public Vec3D angle() {
        return angle;
    }

    /**
     * Returns the angle of rotation around the left, up, and look-at vectors.
     *
     * @return the angle vector for left, up, and look-at rotation
     */
    public Vec3D angleLeftUpLookAt() {
        return angleLeftUpLookAt;
    }

    /**
     * Transforms the object by applying the specified transformation matrix.
     *
     * @param t the transformation matrix to apply
     */
    public void transform(Matrix4 t){
        transformMatrix = t.multiple(transformMatrix);
    }

    /**
     * Transforms the object relative to a specified point by applying the specified transformation matrix.
     *
     * @param point the reference point for the transformation
     * @param transform the transformation matrix to apply
     */
    public void transformRelativePoint(Vec3D point, Matrix4 transform) {

        // translate object in new coordinate system (connected with point)
        transformMatrix = Matrix4.translation(position().minus(point)).multiple(transformMatrix);
        // transform object in the new coordinate system
        transformMatrix = transform.multiple(transformMatrix);
        // translate object back in self connected coordinate system
        position = transformMatrix.w().plus(point);
        transformMatrix = Matrix4.translation(transformMatrix.w().neg()).multiple(transformMatrix);

    }

    /**
     * Translates the object by the specified displacement vector.
     *
     * @param dv the displacement vector
     */
    public void translate(Vec3D dv) {
        position = position.plus(dv);
    }

    /**
     * Scales the object by the specified scale vector.
     *
     * @param s the scale vector
     */
    public void scale(Vec3D s) {
        transform(Matrix4.scale(s));
    }

    /**
     * Rotates the object by the specified rotation vector.
     *
     * @param r the rotation vector
     */
    public void rotate(Vec3D r) {
        angle = angle.plus(r);

        Matrix4 rotationMatrix = Matrix4.rotationX(r.x).multiple(Matrix4.rotationY(r.y)).multiple(Matrix4.rotationZ(r.z));
        transform(rotationMatrix);
    }

    /**
     * Rotates the object around the specified axis vector by the specified rotation value.
     *
     * @param v the axis vector
     * @param rv the rotation value
     */
    public void rotate(Vec3D v, double rv) {
        transform(Matrix4.rotation(v, rv));
    }

    /**
     * Rotates the object relative to a specified point by the specified rotation vector.
     *
     * @param s the reference point for the rotation
     * @param r the rotation vector
     */
    public void rotateRelativePoint(Vec3D s, Vec3D r) {
        angle = angle.plus(r);

        transformRelativePoint(s, Matrix4.rotation(r));
    }

    /**
     * Rotates the object relative to a specified point around the specified axis vector by the specified rotation value.
     *
     * @param s the reference point for the rotation
     * @param v the axis vector
     * @param r the rotation value
     */
    public void rotateRelativePoint(Vec3D s, Vec3D v, double r) {
        transformRelativePoint(s, Matrix4.rotation(v, r));
    }

    /**
     * Rotates the object around the left vector by the specified rotation value.
     *
     * @param rl the rotation value
     */
    public void rotateLeft(double rl) {
        angleLeftUpLookAt = new Vec3D(angleLeftUpLookAt.x + rl,
                angleLeftUpLookAt.y,
                angleLeftUpLookAt.z);

        rotate(left(), rl);
    }

    /**
     * Rotates the object around the up vector by the specified rotation value.
     *
     * @param ru the rotation value
     */
    public void rotateUp(double ru) {
        angleLeftUpLookAt = new Vec3D(angleLeftUpLookAt.x,
                angleLeftUpLookAt.y + ru,
                angleLeftUpLookAt.z);

        rotate(up(), ru);
    }

    /**
     * Rotates the object around the look-at vector by the specified rotation value.
     *
     * @param rlAt the rotation value
     */
    public void rotateLookAt(double rlAt) {
        angleLeftUpLookAt = new Vec3D(angleLeftUpLookAt.x,
                angleLeftUpLookAt.y,
                angleLeftUpLookAt.z + rlAt);
        rotate(lookAt(), rlAt);
    }

    /**
     * Translates the object to the specified point.
     *
     * @param point the point to translate the object to
     */
    public void translateToPoint(Vec3D point) {
        translate(point.minus(position()));
    }


}
