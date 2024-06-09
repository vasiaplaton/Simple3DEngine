package SolarSystem;

import Graphics2d.Vec2D;
import SolarSystem.AstronomicBodies.AstronomicBody;
import SolarSystem.AstronomicBodies.ColorAstronomicBody;
import SolarSystem.AstronomicBodies.MapAstronomicBody;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StandartSolarSystemGenerator {
    static List<AstronomicBody> generateStdSystem(){
        List<AstronomicBody> solarSystem = new ArrayList<>();

        AstronomicBody sun = generateSun();
        solarSystem.add(sun);

        AstronomicBody earth = generateEarth(sun);
        solarSystem.add(earth);

        AstronomicBody moon = generateMoon(earth);
        solarSystem.add(moon);

        AstronomicBody mars = generateMars(sun);
        solarSystem.add(mars);

        solarSystem.add(generateAster(sun));

        solarSystem.add(generateMercury(sun));

        return solarSystem;
    }

    private static AstronomicBody generateSun(){
        double r = 2;

        double sun_m = 1.98892 * Math.pow(10, 30);

        AstronomicBody sun = new MapAstronomicBody("Sun", r, sun_m, new Vec2D(0.0,0.0), "planet_maps/sun_map.jpeg");
        return sun;
    }


    private static AstronomicBody generateEarth(AstronomicBody sun){
        double r = 1;

        double mass = 5.9742  * Math.pow(10, 24); // * 10 ^ 20
        double perigee = 147098290000.0; // meters
        double v = 29783;
        double inc = 7.155 * Math.PI / 180;

        Vec2D startPos = new Vec2D(perigee, 0);

        AstronomicBody earth = new MapAstronomicBody("Earth", r, mass, startPos, "planet_maps/earth_map.jpeg");

        earth.initAstro(new Vec2D(0 , v), sun, inc, Color.BLUE);

        return earth;
    }

    private static AstronomicBody generateMoon(AstronomicBody earth){
        double r = 0.2;

        double mass = 7.477  * Math.pow(10, 22);
        double perigee = 363300000.0 ; // meters
        double v = 1000;
        double inc = earth.getInclination() + 5.45 * Math.PI / 180;

        Vec2D startPos = new Vec2D(perigee, 0);

        AstronomicBody mun = new ColorAstronomicBody("Moon", r, mass, startPos, Color.GRAY);

        mun.initAstro(new Vec2D(0 , v), earth, inc, Color.GRAY);

        return mun;
    }

    private static AstronomicBody generateMars(AstronomicBody sun){
        double r = 0.7;

        double mass = 6.4171  * Math.pow(10, 23); // * 10 ^ 20
        double perigee = 2.06655  * Math.pow(10, 11); // meters
        double v = 24130;
        double inc = 1.85 * Math.PI / 180;

        Vec2D startPos = new Vec2D(perigee, 0);

        AstronomicBody mars = new MapAstronomicBody("Mars", r, mass, startPos, "planet_maps/mars_map.jpeg");

        mars.initAstro(new Vec2D(0 , v), sun, inc, Color.RED);

        return mars;
    }

    private static AstronomicBody generateAster(AstronomicBody sun){
        double r = 0.7;

        double mass = 2.7 * Math.pow(10, 10); // * 10 ^ 20
        double perigee = 111.611 * Math.pow(10, 9); // meters
        double v = 22000;
        double inc = 40 * Math.PI / 180;

        Vec2D startPos = new Vec2D(perigee, 0);

        AstronomicBody aster = new ColorAstronomicBody("Asteroid", r, mass, startPos, Color.BLACK);

        aster.initAstro(new Vec2D(0 , v), sun, inc, Color.CYAN);

        return aster;
    }

    private static AstronomicBody generateMercury(AstronomicBody sun){
        double r = 0.7;

        double mass = 3.33022  * Math.pow(10, 23); // * 10 ^ 20
        double perigee = 46001009  * Math.pow(10, 3); // meters
        double v = 47360;
        double inc = 7 * Math.PI / 180;

        Vec2D startPos = new Vec2D(perigee, 0);

        AstronomicBody mercury = new MapAstronomicBody("Mercury", r, mass, startPos, "planet_maps/mercury_map.jpeg");

        mercury.initAstro(new Vec2D(0 , v), sun, inc, Color.orange);

        return mercury;
    }

}
