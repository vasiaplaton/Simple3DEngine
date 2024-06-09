package Engine;

import Graphics3d.IModel;
import Graphics3d.Triangle;
import Time.Stopwatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadEngine implements Engine{
    private final Camera camera;

    private List<Triangle> projected = new ArrayList<>();

    protected Stopwatch stopwatch = new Stopwatch();

    protected final int nThreads = 8;

    protected ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

    /**
     * Constructs a BaseEngine with the given camera.
     *
     * @param camera the camera used for rendering
     */
    public ThreadEngine(Camera camera) {
        this.camera = camera;
    }

    /**
     * Updates the engine with the given list of 3D models and project by camera.
     * Uses multi-thread CPU computation
     *
     * @param models the list of 3D models to update
     */
    public void update(List<IModel> models){
        stopwatch.start();

        List<Callable<List<Triangle>>> tasks = new ArrayList<>();

        for (final IModel model : models) {
            tasks.add(() -> camera.project(model));
        }

        try {
            List<Future<List<Triangle>>> results = executorService.invokeAll(tasks);

            List<Triangle> projected = new ArrayList<>();
            for (Future<List<Triangle>> result : results) {
                projected.addAll(result.get());
            }

            Camera.sorted(projected);
            this.projected = projected;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
