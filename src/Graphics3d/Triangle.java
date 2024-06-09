package Graphics3d;

import java.awt.*;
import Math.*;

/**
 * The {@code Triangle} class represents a triangle in 3D space, defined by three 4D points.
 * This class provides methods for transforming the triangle with a matrix and calculating its normal vector.
 */
public class Triangle {

    /**
     * An array holding the three points of the triangle.
     */
    private final Vec4D[] points = new Vec4D[3];

    /**
     * The normal vector of the triangle.
     */
    public final Vec3D normal;

    /**
     * The color of the triangle.
     */
    public final Color color;

    /**
     * Constructs a {@code Triangle} instance with the specified points and color.
     *
     * @param p1 the first point of the triangle
     * @param p2 the second point of the triangle
     * @param p3 the third point of the triangle
     * @param color the color of the triangle
     */
    public Triangle(Vec4D p1, Vec4D p2, Vec4D p3, Color color) {
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        normal = calcNormal();
        this.color = color;
    }

    /**
     * Transforms the triangle by applying the specified matrix to its points.
     *
     * @param matrix4 the matrix to apply
     * @return a new transformed {@code Triangle} instance
     */
    public Triangle multiple(Matrix4 matrix4) {
        return new Triangle(matrix4.multiple(points[0]), matrix4.multiple(points[1]), matrix4.multiple(points[2]), color);
    }

    /**
     * Returns the point of the triangle at the specified index.
     *
     * @param pointNum the index of the point (0, 1, or 2)
     * @return the point at the specified index
     */
    public Vec4D getPoint(int pointNum){
        return points[pointNum];
    }

    /**
     * Calculates the normal vector of the triangle.
     * The normal vector is computed using the cross product of two edges of the triangle.
     *
     * @return the normal vector of the triangle
     */
    private Vec3D calcNormal(){
        Vec3D normal;
        Vec3D v1 = new Vec3D(points[1].minus(points[0]));
        Vec3D v2 = new Vec3D(points[2].minus(points[0]));
        Vec3D crossProduct = v1.cross(v2);
        if (crossProduct.sqrAbs() > Const.EPS) {
            normal = crossProduct.normalized();
        } else {
            normal = new Vec3D(0.0);
        }
        return normal;
    }
}
