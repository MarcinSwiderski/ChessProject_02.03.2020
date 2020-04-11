package pwr.chessproject.logger;

public class Logger {

    public enum Mode {
        debug, release
    }

    private static Mode mode = Mode.debug;

    /**
     * Prints message into standard output with fancy prefix if global mode is set to 'debug'. Uses .toString() to convert any object into string
     * @param message Message or variable to print
     */
    public static void debug(Object message) {
        if (mode.equals(Mode.debug))
            System.out.println("#\t"+message.toString());

    }

    /**
     * Prints message into standard output. Uses .toString() to convert any object into string
     * @param message Message or variable to print
     */
    public static void release(Object message) {
        if (mode.equals(Mode.release) || mode.equals(Mode.debug))
            System.out.println(message.toString());

    }

    public static Mode getMode() {
        return mode;
    }

}
