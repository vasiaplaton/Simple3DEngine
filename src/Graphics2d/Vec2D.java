package Graphics2d;

/**
 * The {@code Vec2D} class represents a 2D vector with double precision components.
 * This class provides methods for common vector operations such as normalization,
 * negation, scaling, addition, and rotation.
 */
public class Vec2D {
    /**
     * The x component of the vector.
     */
    public final double x;

    /**
     * The y component of the vector.
     */
    public final double y;

    /**
     * Constructs a {@code Vec2D} instance with the specified x and y components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     */
    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;

    }

    /**
     * Returns the magnitude (length) of the vector.
     *
     * @return the magnitude of the vector
     */
    public double abs(){
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns a normalized version of the vector.
     * The normalized vector has the same direction but a magnitude of 1.
     *
     * @return a normalized vector
     */
    public Vec2D normalized(){
        return new Vec2D(x / abs(), y / abs());
    }


    /**
     * Returns the negation of the vector.
     * The negated vector has the same magnitude but points in the opposite direction.
     *
     * @return the negated vector
     */
    public Vec2D neg(){
        return new Vec2D(-x, -y);
    }


    /**
     * Returns a new vector that is the result of scaling this vector by a scalar value.
     *
     * @param v the scalar value to scale the vector by
     * @return the scaled vector
     */
    public Vec2D multiple(double v){
        return new Vec2D(x * v, y * v);
    }

    /**
     * Returns a new vector that is the result of adding the specified vector to this vector.
     *
     * @param a the vector to add
     * @return the resultant vector of the addition
     */
    public Vec2D add(Vec2D a){
        return new Vec2D(x + a.x, y + a.y);
    }

    /**
     * Returns a string representation of the vector.
     * The string representation consists of the x and y components enclosed in parentheses.
     *
     * @return a string representation of the vector
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Returns a new vector that is the result of rotating this vector by the specified angle.
     * The angle is specified in radians.
     *
     * @param a the angle in radians to rotate the vector
     * @return the rotated vector
     */
    public Vec2D rotate(double a){
        return new Vec2D(x * Math.cos(a) - y * Math.sin(a), x * Math.sin(a) + y * Math.cos(a));
    }


}