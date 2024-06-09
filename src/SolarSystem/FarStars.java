package SolarSystem;

import Graphics2d.Vec2;
import Graphics2d.Vec2D;
import Graphics3d.IModel;
import Graphics3d.Triangle;
import Graphics3d.Vec3D;

import java.awt.*;
import java.util.Random;

public class FarStars extends IModel {
    public Color color;
    public FarStars(double far){

        Random random = new Random();
        color = new Color(200 + random.nextInt(55), 200 + random.nextInt(55), 200 + random.nextInt(55));
        double phi = random.nextDouble(Math.PI * 2);
        double theta = random.nextDouble(-Math.PI / 2, Math.PI / 2);

        Vec3D point = new Vec3D(Math.cos(phi) * Math.cos(theta) * far,
                Math.cos(theta) * Math.sin(phi) * far,
                Math.sin(theta) * far);

        Vec3D point1 = new Vec3D(point.x + point.x/far*1000, point.y, point.z);


        list.add(new Triangle(point.makePoint4D(), point1.makePoint4D(), point.makePoint4D(), color));
    }
}
