package Engine;
import Graphics3d.*;
import Graphics3d.Object;
import Math.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

/**
 * The Camera class represents a virtual camera used for rendering 3D scenes.
 * It handles projection and clipping of 3D models onto a 2D viewport.
 */
public class Camera extends Object {
    final double aspect;
    private final Matrix4 SP;

    private final List<Plane> clipPlanes = new ArrayList<>();


    /**
     * Constructs a Camera with the specified parameters.
     *
     * @param width  the width of the viewport
     * @param height the height of the viewport
     * @param fov    the field of view angle in degrees
     * @param ZNear  the distance to the near clipping plane
     * @param ZFar   the distance to the far clipping plane
     */
    public Camera(int width, int height, double fov, double ZNear, double ZFar) {
        aspect = (double) width / (double) height;
        Matrix4 P = Matrix4.projection(fov, aspect, ZNear, ZFar);
        Matrix4 S = Matrix4.screenSpace(width, height);
        SP = S.multiple(P);

        clipPlanes.add(new Plane(new Vec3D(0.0, 0.0, 1.0), new Vec3D(0.0, 0.0, ZNear))); // near plane
        clipPlanes.add(new Plane(new Vec3D(0.0, 0.0, -1.0), new Vec3D(0.0, 0.0, ZFar))); // far plane

        double thetta1 = Const.PI * fov * 0.5 / 180.0;
        double thetta2 = Math.atan(aspect * Math.tan(thetta1));
        clipPlanes.add(new Plane(new Vec3D(-Math.cos(thetta2), 0.0, Math.sin(thetta2)), new Vec3D(0.0))); // left plane
        clipPlanes.add(new Plane(new Vec3D(Math.cos(thetta2), 0.0, Math.sin(thetta2)), new Vec3D(0.0))); // right plane
        clipPlanes.add(new Plane(new Vec3D(0.0, Math.cos(thetta1), Math.sin(thetta1)), new Vec3D(0.0))); // down plane
        clipPlanes.add(new Plane(new Vec3D(0.0, -Math.cos(thetta1), Math.sin(thetta1)), new Vec3D(0.0))); // up plane

    }

    /**
     * Projects the specified 3D model onto the camera viewport.
     *
     * @param mesh the 3D model to project
     * @return a list of triangles representing the projected model
     */
    public List<Triangle> project(IModel mesh) {
        List<Triangle> triangles = new ArrayList<>();

        Matrix4 V = invModel();
        Matrix4 M = mesh.model();


        for (int i = 0; i < mesh.list.size(); i++) {
            Triangle t = mesh.list.get(i);

            Triangle MTriangle = t.multiple(M);


            double dot = MTriangle.normal.dot(new Vec3D(MTriangle.getPoint(0)).minus(position).normalized());

            if (dot > 0) {
           //   continue;
            }
            List<Triangle> clippedTriangles = new ArrayList<>();
            List<Triangle> tempBuffer = new ArrayList<>();

            Triangle VMTriangle = MTriangle.multiple(V);

            clippedTriangles.add(VMTriangle);

            for (Plane plane : clipPlanes) {
                while (!clippedTriangles.isEmpty()) {
                    List<Triangle> clipResult = plane.clip(clippedTriangles.get(clippedTriangles.size()-1));
                    clippedTriangles.remove(clippedTriangles.size()-1);
                    tempBuffer.addAll(clipResult);
                }
                List<Triangle> other = new ArrayList<>(tempBuffer);
                tempBuffer = new ArrayList<>(clippedTriangles);
                clippedTriangles = new ArrayList<>(other);
            }

            for (Triangle clipped : clippedTriangles) {
                double dot1 = clipped.normal.dot(new Vec3D(clipped.getPoint(0)).minus(position).normalized());
                Color color = clipped.color;
                Color ambientColor = new Color(
                        (int) (color.getRed() * ( 0.5 * Math.abs(dot1) + 0.5)),
                        (int) (color.getGreen() * (0.5 * Math.abs(-dot1) + 0.5 )),
                        (int)  (color.getBlue() * (0.5 * Math.abs(-dot1 ) + 0.5)),
                        color.getAlpha());

                Triangle clippedProjected = clipped.multiple(SP);

                Triangle clippedProjectedNormalized = new Triangle(
                        clippedProjected.getPoint(0).divide(clippedProjected.getPoint(0).w),
                        clippedProjected.getPoint(1).divide(clippedProjected.getPoint(1).w),
                        clippedProjected.getPoint(2).divide(clippedProjected.getPoint(2).w),
                        ambientColor);

                triangles.add(clippedProjectedNormalized);
            }
        }
        return triangles;
    }

    /**
     * Sorts the specified list of triangles based on their average depth.
     *
     * @param triangles the list of triangles to sort
     */
    public static void sorted(List<Triangle> triangles){
        triangles.sort((t1, t2) -> {
            double[] v_z1 = new double[]{t1.getPoint(0).z, t1.getPoint(1).z, t1.getPoint(2).z};
            double[] v_z2 = new double[]{t2.getPoint(0).z, t2.getPoint(1).z, t2.getPoint(2).z};
            Arrays.sort(v_z1);
            Arrays.sort(v_z2);

            double z1 = v_z1[0] + v_z1[1] + v_z1[2];
            double z2 = v_z2[0] + v_z2[1] + v_z2[2];

            return Double.compare(z2, z1);
        });

    }

}
