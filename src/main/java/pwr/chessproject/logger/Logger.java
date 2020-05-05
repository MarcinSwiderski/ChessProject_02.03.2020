package pwr.chessproject.logger;

import java.util.Arrays;

public class Logger {

    public enum Mode {
        debug, release
    }

    private static Mode mode = Mode.release;

    /**
     * Prints message into standard output with fancy prefix if global mode is set to 'debug'. Uses .toString() to convert any object into string
     * @param message Message or variable to print
     */
    public static void debug(Object message) {
        if (message == null ) {
            System.out.println("#\tnull");
            return;
        }
        if (mode.equals(Mode.debug))
            System.out.println("#\t"+message.toString());

    }

    /**
     * Prints message into standard output. Uses .toString() to convert any object into string
     * @param message Message or variable to print
     */
    public static void release(Object message) {
        if (message == null ) {
            System.out.println("#\tnull");
            return;
        }
        if (mode.equals(Mode.release) || mode.equals(Mode.debug))
            System.out.println(message.toString());

    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        Logger.mode = mode;
    }

    /**
     * Tries to read mode from java command line arguments, then tries to read it from environment variable 'MODE'.
     * If all of it fails sets mode to 'Logger.mode.release'.
     * Prints information about mode when in debug mode.
     * @param args Presumably command line arguments
     */
    public static void configureLogMode(String[] args) {
        if (args.length > 0) {
            String argsLine = Arrays.toString(args).toLowerCase();
            if (argsLine.contains("debug"))
                Logger.setMode(Logger.Mode.debug);
            else if (argsLine.contains("release") || argsLine.contains("production"))
                Logger.setMode(Logger.Mode.release);
        }
        else {
            try {
                Logger.setMode(Logger.Mode.valueOf(System.getenv("MODE").toLowerCase()));
            }
            catch (IllegalArgumentException | NullPointerException ex) {
                Logger.debug(ex);
                Logger.setMode(Logger.Mode.release);
            }
        }

        Logger.debug("Mode: "+Logger.getMode());
    }
}
