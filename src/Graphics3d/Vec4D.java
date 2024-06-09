package Graphics3d;

import Math.*;

/**
 * The {@code Vec4D} class represents a 4D vector with double precision components.
 * This class provides methods for common vector operations such as addition, negation,
 * subtraction, scaling, and division.
 */
public class Vec4D {

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
     * The w component of the vector.
     */
    public final Double w;

    /**
     * Constructs a {@code Vec4D} instance with the specified x, y, z, and w components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     * @param w the w component of the vector
     */
    public Vec4D(Double x, Double y, Double z, Double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Returns a new vector that is the result of adding the specified vector to this vector.
     *
     * @param vec the vector to add
     * @return the resultant vector of the addition
     */
    public Vec4D plus(Vec4D vec){
        return new Vec4D(x + vec.x, y + vec.y, z + vec.z, w + vec.w);
    }

    /**
     * Returns the negation of this vector.
     *
     * @return the negated vector
     */
    public Vec4D neg(){
        return new Vec4D(-x, -y, -z, -w);
    }

    /**
     * Returns a new vector that is the result of subtracting the specified vector from this vector.
     *
     * @param vec the vector to subtract
     * @return the resultant vector of the subtraction
     */
    public Vec4D minus(Vec4D vec){
        return plus(vec.neg());
    }

    /**
     * Returns a new vector that is the result of scaling this vector by a scalar value.
     *
     * @param number the scalar value to scale the vector by
     * @return the scaled vector
     */
    public Vec4D multiple(double number) {
        return new Vec4D(x * number, y * number, z * number, w * number);
    }

    /**
     * Returns a new vector that is the result of dividing this vector by a scalar value.
     *
     * @param number the scalar value to divide the vector by
     * @return the scaled vector
     * @throws IllegalArgumentException if the scalar value is zero
     */
    public Vec4D divide(double number) {
        if (Math.abs(number) > Const.EPS) {
            return multiple(1.0 / number);
        } else {
            throw new IllegalArgumentException("Vec4D::operator/(double number): division by zero");
        }
    }


}
