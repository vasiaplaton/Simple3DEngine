package SolarSystem.AstronomicBodies;

import Graphics2d.Vec2D;

import java.awt.*;

public class ColorAstronomicBody extends AstronomicBody {
    public final Color color;

    public ColorAstronomicBody(String name, double r, double mass, Vec2D astroPos, Color color) {
        super(name, r, mass, astroPos);
        this.color = color;
        rebuildSphere(r);
    }

    protected Color getColor(int lat, int lon, int lonDivides) {
        double lat1 = (double) lat / (latDivides - 1);
        double toWhite = Math.abs(lat1 * 2 - 1); // 0 - 1
        int r = (int) (color.getRed()*0.7 + toWhite * 255 * 0.3);
        int g = (int) (color.getGreen()*0.7 + toWhite * 255 * 0.3);
        int b = (int) (color.getBlue()*0.7 + toWhite * 255 * 0.3);

        return new Color(r, g, b);
    }
}
