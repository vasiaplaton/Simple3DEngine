package Graphics3d;

import Math.*;

/**
 * The {@code Vec3D} class represents a 3D vector with double precision components.
 * This class provides methods for common vector operations such as normalization,
 * negation, scaling, addition, subtraction, cross product, dot product, and rotation.
 */
public class Vec3D{
    /**
     * The x component of the vector.
     */
    public final Double x;

    /**
     * The y component of the vector.
     */
    public final Double y;

    /**
     * The z component of the vector.
     */
    public final Double z;

    /**
     * Constructs a {@code Vec3D} instance with the specified x, y, and z components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     */
    public Vec3D(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a {@code Vec3D} instance with the same value for x, y, and z components.
     *
     * @param a the value for all components of the vector
     */
    public Vec3D(Double a) {
        this(a, a, a);
    }

    /**
     * Constructs a {@code Vec3D} instance from a {@code Vec4D} instance,
     * discarding the w component.
     *
     * @param vec4D the {@code Vec4D} instance to convert
     */
    public Vec3D(Vec4D vec4D) {
        this(vec4D.x, vec4D.y, vec4D.z);
    }

    /**
     * Creates a {@code Vec4D} instance representing this vector as a point.
     * The w component of the resulting {@code Vec4D} is set to 1.0.
     *
     * @return a {@code Vec4D} instance with w component set to 1.0
     */
    public Vec4D makePoint4D() {
        return new Vec4D(x, y, z, 1.0);
    }

    /**
     * Computes the cross product of this vector and the specified vector.
     *
     * @param vec the vector to compute the cross product with
     * @return the cross product vector
     */
    public Vec3D cross(Vec3D vec){
        return new Vec3D(y * vec.z - vec.y * z,
                z * vec.x - vec.z * x,
                x * vec.y - vec.x * y);
    }

    /**
     * Returns the negation of this vector.
     *
     * @return the negated vector
     */
    public Vec3D neg(){
        return new Vec3D(-x, -y, -z);
    }

    /**
     * Returns a new vector that is the result of adding the specified vector to this vector.
     *
     * @param vec the vector to add
     * @return the resultant vector of the addition
     */
    public Vec3D plus(Vec3D vec){
        return new Vec3D(x + vec.x, y + vec.y, z + vec.z);
    }

    /**
     * Returns the squared magnitude of the vector.
     *
     * @return the squared magnitude of the vector
     */
    public double sqrAbs(){
        return x * x + y * y + z * z;
    }

    /**
     * Returns the magnitude (length) of the vector.
     *
     * @return the magnitude of the vector
     */
    public double abs(){
        return Math.abs(sqrAbs());
    }

    /**
     * Returns a new vector that is the result of scaling this vector by a scalar value.
     *
     * @param val the scalar value to scale the vector by
     * @return the scaled vector
     */
    public Vec3D multiple(double val) {
        return new Vec3D(x * val, y * val, z * val);
    }

    /**
     * Returns a normalized version of the vector.
     * The normalized vector has the same direction but a magnitude of 1.
     *
     * @return a normalized vector
     */
    public Vec3D normalized() {
        double vecAbs = sqrAbs();
        if (vecAbs > Const.EPS) {
            return multiple(1 / Math.sqrt(vecAbs));
        } else {
            return new Vec3D(1.0);
        }
    }

    /**
     * Returns a new vector that is the result of subtracting the specified vector from this vector.
     *
     * @param vec the vector to subtract
     * @return the resultant vector of the subtraction
     */
    public Vec3D minus(Vec3D vec){
        return plus(vec.neg());
    }

    /**
     * Computes the dot product of this vector and the specified vector.
     *
     * @param vec the vector to compute the dot product with
     * @return the dot product
     */
    public double dot(Vec3D vec)  {
        return vec.x * x + vec.y * y + vec.z * z;
    }

    /**
     * Returns a string representation of the vector.
     * The string representation consists of the x, y, and z components enclosed in parentheses.
     *
     * @return a string representation of the vector
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    /**
     * Returns a new vector that is the result of rotating this vector by the specified rotation vector.
     * The rotation is performed using a rotation matrix.
     *
     * @param rotation the rotation vector
     * @return the rotated vector
     */
    public Vec3D rotate(Vec3D rotation){
        return Matrix4.rotation(rotation).multiple(this);
    }
}
