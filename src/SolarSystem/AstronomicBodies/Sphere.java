package SolarSystem.AstronomicBodies;

import Graphics3d.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sphere extends IModel {
    // число точек для разбиения окружности(больше - качественнее, но медленнее отрисовка)
    public static final int latDivides = 20;

    Color spColor = null;

    public Sphere(){

    }

    public Sphere(double r, Color color){
        this.spColor = color;
        rebuildSphere(r);
    }


    public void rebuildSphere(double r) {
        list.clear();
        // slice sphere
        for (int hStep = 0; hStep < latDivides; hStep++) {
            // main slice
            double a1 = hStep * Math.PI / latDivides; // [-PI; PI)
            double h1 = r * Math.cos(a1);
            double r1 = r * Math.sin(a1);
            // one slice upper
            double a2 = (hStep + 1) * Math.PI / latDivides; // (-PI; PI]
            double h2 = r * Math.cos(a2);
            double r2 = r * Math.sin(a2);

            // num of points on slice
            int numOfPointsForFloor = (int) ((Math.max(r1, r2) / r) * latDivides * 2);
            // all points for main slice
            java.util.List<Vec3D> from = getCirclePoints(numOfPointsForFloor, r1, h1, 0);
            //all point for upper layer
            List<Vec3D> to = getCirclePoints(numOfPointsForFloor, r2, h2, 2 * Math.PI / numOfPointsForFloor);


            for (int i = 0; i < numOfPointsForFloor; i++) {
                int nextInd = i + 1 < numOfPointsForFloor ? i + 1 : 0;

                Color color = getColor(hStep, i, numOfPointsForFloor);
                // build two triangle connecting one pair from one layer to another
                list.add(new Triangle(from.get(i).makePoint4D(),
                        to.get(i).makePoint4D(),
                        from.get(nextInd).makePoint4D(),
                        color));
                list.add(new Triangle(to.get(i).makePoint4D(),
                        from.get(nextInd).makePoint4D(),
                        to.get(nextInd).makePoint4D(),
                        color));
                // на диске l методичка курсяк (~выпускная работа) (30-40 страниц)
                //
            }
        }
    }

    // lat - широта ( -pi/2 -> pi/2)(высота на окружности) lon - долгота(-PI PI)(угол на эвкаторе)
    protected Color getColor(int lat, int lon, int lonDivides) {
        return spColor;
    }

    private static List<Vec3D> getCirclePoints(int numOfVertexes, double radius, double high, double offsetAngle) {
        List<Vec3D> list = new ArrayList<>();
        for (int i = 0; i < numOfVertexes; i++) {
            double angle = offsetAngle + 2 * Math.PI * i / numOfVertexes;
            list.add(new Vec3D(Math.cos(angle) * radius, Math.sin(angle) * radius, high));
        }
        return list;
    }
}
