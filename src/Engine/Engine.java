package Engine;

import Graphics3d.IModel;

import java.awt.*;
import java.util.List;

/**
 * The Engine interface represents a graphics rendering engine.
 * Implementations of this interface are responsible for updating
 * and rendering 3D models onto a canvas.
 */
public interface Engine {

    /**
     * Updates the engine with the given list of 3D models and project that models.
     *
     * @param models the list of 3D models to update
     */
    void update(List<IModel> models);

    /**
     * Gets the total number of triangles currently being rendered by the engine.
     *
     * @return the number of triangles
     */
    int getTrianglesAmount();

    /**
     * Draws the rendered scene onto the given graphics object.
     *
     * @param imageGraphics the graphics object to draw on
     */
    void drawOnCanvas(Graphics2D imageGraphics);

}
