package pwr.chessproject.logger;

import java.util.Arrays;

public class Logger {

    public enum Mode {
        debug, release
    }

    protected static Mode mode = Mode.release;

    /**
     * Prints message into standard output with fancy prefix if global mode is set to 'debug'. Uses .toString() to convert any object into string
     * @param message Message or variable to print
     */
    public static void debug(Object message) {
        if (mode.equals(Mode.debug)) {
            if (message == null )
                System.out.println("#\tnull");
            else
                System.out.println("#\t"+message.toString());
        }
    }

    /**
     * Prints message into standard output. Uses .toString() to convert any object into string
     * @param message Message or variable to print
     */
    public static void release(Object message) {
        if (mode.equals(Mode.release) || mode.equals(Mode.debug)) {
            if (message == null )
                System.out.println("#\tnull");
            else
                System.out.println(message.toString());
        }
    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        Logger.mode = mode;
    }
}
