package Engine;

import Graphics3d.IModel;
import Graphics3d.Triangle;
import Time.Stopwatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The BaseEngine class is a basic implementation of the Engine interface.
 * It provides functionality for updating and rendering 3D models.
 */
public class BaseEngine implements Engine {
    private final Camera camera;

    private List<Triangle> projected = new ArrayList<>();

    protected Stopwatch stopwatch = new Stopwatch();

    /**
     * Constructs a BaseEngine with the given camera.
     *
     * @param camera the camera used for rendering
     */
    public BaseEngine(Camera camera) {
        this.camera = camera;
    }

    /**
     * Updates the engine with the given list of 3D models and project by camera.
     * Uses one-thread CPU computation
     *
     * @param models the list of 3D models to update
     */
    public void update(List<IModel> models){
        stopwatch.start();

        List<Triangle> projected = new ArrayList<>();
        for(IModel model: models){
            projected.addAll(camera.project(model));
        }
        Camera.sorted(projected);
        this.projected = projected;

        System.out.println("project: " + stopwatch.stop());
    }

    public int getTrianglesAmount(){
        return this.projected.size();
    }

    public void drawOnCanvas(Graphics2D imageGraphics){
        stopwatch.start();

        List<Triangle> triangles2d = projected;

        for (Triangle triangle : triangles2d) {
            imageGraphics.setColor(triangle.color);
            imageGraphics.setStroke(new BasicStroke(2));

            imageGraphics.drawLine(triangle.getPoint(0).x.intValue(),triangle.getPoint(0).y.intValue(),
                    triangle.getPoint(1).x.intValue(), triangle.getPoint(1).y.intValue());

            imageGraphics.drawLine(triangle.getPoint(1).x.intValue(), triangle.getPoint(1).y.intValue(),
                    triangle.getPoint(2).x.intValue(), triangle.getPoint(2).y.intValue());

            imageGraphics.drawLine(triangle.getPoint(2).x.intValue(), triangle.getPoint(2).y.intValue(),
                    triangle.getPoint(0).x.intValue(), triangle.getPoint(0).y.intValue());


            imageGraphics.setColor(triangle.color);
            Polygon polygon = new Polygon();
            polygon.addPoint(triangle.getPoint(0).x.intValue(),triangle.getPoint(0).y.intValue());
            polygon.addPoint(triangle.getPoint(1).x.intValue(),triangle.getPoint(1).y.intValue());
            polygon.addPoint(triangle.getPoint(2).x.intValue(),triangle.getPoint(2).y.intValue());
            imageGraphics.fillPolygon(polygon);
        }

        System.out.println("draw: " + stopwatch.stop());
    }

}
