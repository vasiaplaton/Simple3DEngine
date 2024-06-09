package Time;

/**
 * A class for counting frames per second (FPS).
 */
public class FPSCounter {

    protected int frames;
    protected long lastMilliseconds;

    protected int fps;

    /**
     * Constructs an FPSCounter object.
     */
    public FPSCounter(){
        lastMilliseconds = System.currentTimeMillis();
    }


    /**
     * Updates the FPS count.
     */
    public void tick(){
        long time = System.currentTimeMillis() - lastMilliseconds;
        frames++;
        if (time > 1500){
            fps = (int) (1.0 * frames / (time / 1000.0));
            lastMilliseconds = System.currentTimeMillis();
            frames = 0;
        }
    }

    /**
     * Gets the current FPS.
     *
     * @return the current FPS.
     */
    public int getFPS(){
        return fps;
    }
}
