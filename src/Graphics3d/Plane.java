package Graphics3d;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Plane} class represents a plane in 3D space defined by a normal vector and a point.
 * This class provides methods to calculate the distance from a point to the plane,
 * find the intersection of a line with the plane, and clip a triangle against the plane.
 */
public class Plane {

    /**
     * The normal vector of the plane.
     */
    public final Vec3D normal;

    /**
     * A point on the plane.
     */
    public final Vec3D point;


    /**
     * A point of intersection used for initialization purposes.
     */
    public final Vec3D point1;

    /**
     * Constructs a {@code Plane} instance with the specified normal vector and point.
     * The normal vector is normalized.
     *
     * @param normal the normal vector of the plane
     * @param point the point on the plane
     */
    public Plane(Vec3D normal, Vec3D point) {
        this.normal = normal.normalized();
        this.point = point;
        this.point1 = intersection(new Vec3D(0.0, 0.0, 0.0), new Vec3D(100.0, 100.0, 100.0)).pos;
    }

    /**
     * Calculates the distance from the specified point to the plane.
     *
     * @param point_in the point to calculate the distance from
     * @return the distance from the point to the plane
     */
    double distance(Vec3D point_in) {
        return point_in.dot(normal) - point.dot(normal);
    }

    /**
     * A record representing the intersection of a line with the plane.
     */
    record Intersect(Vec3D pos, double dist) {}

    /**
     * Finds the intersection of the line segment between the start and end points with the plane.
     *
     * @param start the start point of the line segment
     * @param end the end point of the line segment
     * @return the intersection point and the interpolation factor
     */
    Intersect intersection(Vec3D start, Vec3D end) {
        double s_dot_n = start.dot(normal);
        double k = (s_dot_n - point.dot(normal)) / (s_dot_n - end.dot(normal));
        Vec3D res = start.plus((end.minus(start)).multiple(k));
        return new Intersect(res, k);
    }

    /**
     * Clips the specified triangle against the plane and returns the resulting triangles.
     * The clipping operation can result in 0, 1, or 2 triangles.
     *
     * @param tri the triangle to clip
     * @return a list of resulting triangles after clipping
     */
    public List<Triangle> clip(Triangle tri) {
        List<Triangle> result = new ArrayList<>();
        List<Vec3D> insidePoints = new ArrayList<>();
        List<Vec3D> outsidePoints = new ArrayList<>();

        double[] distances = new double[]{distance(new Vec3D(tri.getPoint(0))),
                distance(new Vec3D(tri.getPoint(1))),
                distance(new Vec3D(tri.getPoint(2)))};

        for (int i = 0; i < 3; i++) {
            if (distances[i] >= 0) {
                insidePoints.add(new Vec3D(tri.getPoint(i)));
            } else {
                outsidePoints.add(new Vec3D(tri.getPoint(i)));
            }
        }


        if (insidePoints.size() == 1) {
            Intersect intersect1 = intersection(insidePoints.get(0), outsidePoints.get(0));
            Intersect intersect2 = intersection(insidePoints.get(0), outsidePoints.get(1));

            result.add(new Triangle(insidePoints.get(0).makePoint4D(),
                    intersect1.pos.makePoint4D(),
                    intersect2.pos.makePoint4D(),
                    tri.color));
        }

        if (insidePoints.size() == 2) {
            Intersect intersect1 = intersection(insidePoints.get(0), outsidePoints.get(0));
            Intersect intersect2 = intersection(insidePoints.get(1), outsidePoints.get(0));

            result.add(new Triangle(insidePoints.get(0).makePoint4D(),
                    intersect1.pos.makePoint4D(),
                    insidePoints.get(1).makePoint4D(),
                    tri.color));
            result.add(new Triangle(intersect1.pos.makePoint4D(),
                    intersect2.pos.makePoint4D(),
                    insidePoints.get(1).makePoint4D(),
                    tri.color));
        }

        if (insidePoints.size() == 3) {
            result.add(tri);
        }
        return result;

    }

}
