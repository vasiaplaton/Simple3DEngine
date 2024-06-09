package SolarSystem.AstronomicBodies;

import Graphics2d.Vec2D;
import Graphics3d.Vec3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapAstronomicBody extends AstronomicBody {
    final BufferedImage map;
    public MapAstronomicBody(String name, double radius, double mass, Vec2D astroPos, String path) {
        super(name, radius, mass, astroPos);
        try {
            File img = new File(path);
            map = ImageIO.read(img);
        } catch (IOException e) {
            throw new IllegalArgumentException("cant open file");
        }
        rebuildSphere(radius);
        rotate(new Vec3D(- Math.PI / 2, 0.0, 0.0));
    }

    @Override
    // lat - широта ( -pi/2 -> pi/2)(высота на окружности) lon - долгота(-PI PI)(угол на эвкаторе)
    protected Color getColor(int lat, int lon, int lonDivides) {
        int imgStartX = map.getWidth() * lon / (lonDivides);
        int imgEndX = map.getWidth() * (lon + 1) / (lonDivides);

        int imgStartY = map.getHeight() * lat / (latDivides);
        int imgEndY = map.getHeight() * (lat + 1) / (latDivides);
        long r = 0;
        long g = 0;
        long b = 0;
        int c = 0;

        for (int x = imgStartX; x < imgEndX; x++) {
            for (int y = imgStartY; y < imgEndY; y++) {
                Color color = new Color(map.getRGB(x, y));
                r += color.getRed();
                g += color.getGreen();
                b += color.getBlue();
                c += 1;
            }
        }
        int r_res =(int) Math.floor((double) r/c);
        int g_res =(int) Math.floor((double) g/c);
        int b_res =(int) Math.floor((double) b/c);
        return new Color(r_res, g_res, b_res);
    }
}
