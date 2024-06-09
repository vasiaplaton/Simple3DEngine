package Time;

/**
 * A simple stopwatch for measuring elapsed time.
 */
public class Stopwatch {
    protected long time = 0;

    /**
     * Starts the stopwatch.
     */
    public void start(){
        time = System.currentTimeMillis();
    }

    /**
     * Stops the stopwatch and returns the elapsed time in milliseconds.
     *
     * @return the elapsed time in milliseconds.
     */
    public long stop(){
        return System.currentTimeMillis() - time;
    }
}
