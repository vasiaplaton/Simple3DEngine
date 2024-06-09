package Graphics2d;

/**
 * The {@code Vec2} class represents a 2D vector with components of a numerical type.
 * This class is immutable, meaning that once an instance is created, its state cannot be changed.
 *
 * @param <T> the type of the numerical components of the vector, which extends {@link Number}
 */
public class Vec2<T extends Number> {

    /**
     * The x component of the vector.
     */
    public final T x;

    /**
     * The y component of the vector.
     */
    public final T y;

    /**
     * Constructs a {@code Vec2} instance with the specified x and y components.
     *
     * @param x the x component of the vector
     * @param y the y component of the vector
     */
    public Vec2(T x, T y) {
        this.x = x;
        this.y = y;
    }
}