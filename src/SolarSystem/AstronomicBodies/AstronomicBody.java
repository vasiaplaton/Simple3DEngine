package SolarSystem.AstronomicBodies;

import Graphics2d.Vec2D;
import Graphics3d.IModel;
import Graphics3d.Triangle;
import Graphics3d.Vec3D;
import SolarSystem.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AstronomicBody extends Sphere{
    public final double radius;
    private final double mass; // mass * 10^24 = mass in kg

    public final String name;

    private Vec2D relParentPos;

    private boolean astroInit = false;
    private Vec2D velocity;

    private double inclination = 0.0;
    private AstronomicBody parent;

    private final IModel trajectory = new IModel();
    private final List<Double> trajectoryAngles = new ArrayList<>();

    private Color trajectoryC;

    private double angledLenTrajectory = 0;

    public AstronomicBody(String name, double radius, double mass, Vec2D relParentPos) {
        this.name = name;
        this.radius = radius;
        this.mass = mass;
        setAstroPos(relParentPos);
    }

    public void initAstro(Vec2D startVel, AstronomicBody whichSatellite, double inclination, Color trajectoryC){
        this.astroInit = true;
        this.velocity = startVel;
        this.parent = whichSatellite;
        this.inclination = inclination;
        this.trajectoryC = trajectoryC;
        setAstroPos(relParentPos);
    }


    public void update(double seconds, double trajectoryMaxLen) {
        if(!astroInit) return;

        double min_step = 24 * 60 *60 * 2;
        if(seconds > min_step) {
            double step = min_step / 2;
            int steps = (int) Math.floor(seconds / step);
            double last_step = seconds - step * steps;
            for (int i = 0; i < steps; i++) {
                update(step, trajectoryMaxLen);
            }
            update(last_step, trajectoryMaxLen);
            return;
        }

        rotate(new Vec3D(0.0, 0.1*seconds/(24*60*60), 0.0));

        double G = Consts.G;

        Vec2D zeroParent = relParentPos;

        double toParent = zeroParent.abs();
        Vec2D vecToParent = zeroParent.neg().normalized();

        double g = (G * parent.getMass()) / Math.pow(toParent, 2);
        velocity = velocity.add(vecToParent.multiple(g * seconds));


        Vec2D newPos = relParentPos.add(velocity.multiple(seconds));

        Vec3D oldAbsolutePos = position;
        double angleToAdd = Math.abs(Math.asin((newPos.x - relParentPos.x) / newPos.abs()));

        setAstroPos(newPos);

        addToTrajectory(oldAbsolutePos, position, angleToAdd, trajectoryMaxLen);
    }

    public double getMass(){
        return mass;
    }

    public Vec2D getAbsolutePos(){
        if (parent != null) {
            return relParentPos.add(parent.getAbsolutePos());
        }
        return relParentPos;
    }

    public void setAstroPos(Vec2D relParentPos) {
        this.relParentPos = relParentPos;

        double kOfDrawing = SolarSystem.kOfDrawing;

        Vec3D planePos = new Vec3D(getAbsolutePos().x / kOfDrawing, 0.0,
                getAbsolutePos().y / kOfDrawing);
        planePos = planePos.rotate(new Vec3D(0.0, 0.0, inclination));
        position = planePos;
    }


    public void addToTrajectory(Vec3D oldPos, Vec3D nowPos, double angle, double trajectoryMaxLen) {
       trajectory.list.add(new Triangle(nowPos.makePoint4D(), oldPos.makePoint4D(), nowPos.makePoint4D(), trajectoryC));
       trajectoryAngles.add(angle);
       angledLenTrajectory += angle;

       if(trajectory.list.size() > 1000){
           trajectory.list.remove(0);
       }
       //double trajectoryMaxLen = 4 * Math.PI;
       while (angledLenTrajectory > trajectoryMaxLen && trajectory.list.size() > 1) {
           trajectory.list.remove(0);
           angledLenTrajectory -= trajectoryAngles.get(0);
           trajectoryAngles.remove(0);
       }


    }

    public IModel getTrajectory() {
        return trajectory;
    }

    public boolean isAstroInit() {
        return astroInit;
    }

    public double getInclination() {
        return inclination;
    }

    @Override
    public String toString() {
        return name;
    }
}
