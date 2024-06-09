package SolarSystem;

import Graphics2d.Vec2;
import Engine.Camera;
import Engine.*;
import Graphics3d.IModel;
import Graphics3d.Vec3D;
import SolarSystem.AstronomicBodies.AstronomicBody;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem {
    public final Camera camera;

    public final Color backgroundColor = new Color(0, 0, 20, 255);

    public final Engine engine;


    public final List<AstronomicBody> bodies = new ArrayList<>();

    public static final double kOfDrawing = 7354914000.5;

    private final List<FarStars> farStars = new ArrayList<>();



    public SolarSystem() {
        Vec2<Integer> bounds = new Vec2<>(900, 900);

        camera = new Camera(bounds.x, bounds.y, 90, 0.1, 10000000);

        engine = new ThreadEngine(camera);

        camera.translate(new Vec3D(0.0, 20.0, -40.0));

        bodies.addAll(StandartSolarSystemGenerator.generateStdSystem());


        generateFarStars(1000);

    }


    private void generateFarStars(int amount){
        for (int i = 0; i < amount; i++) {
            farStars.add(new FarStars(1000000));
        }
    }

    public void update(boolean trajectories){
        List<IModel> models = new ArrayList<>();
        models.addAll(bodies);

        if(trajectories) {
           models.addAll(bodies.stream().map(AstronomicBody::getTrajectory).toList());
        }

        models.addAll(farStars);
        engine.update(models);
    }

    public void updateandDrawOnCanvas(Graphics2D graphics2D, boolean trajectories){
        update(trajectories);
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, graphics2D.getClipBounds().width, graphics2D.getClipBounds().height);
        engine.drawOnCanvas(graphics2D);
    }

    public void nextStep(double k, double trajectoryMaxLen){
        bodies.forEach(b -> b.update(24*60*60*k, trajectoryMaxLen));
    }
}
