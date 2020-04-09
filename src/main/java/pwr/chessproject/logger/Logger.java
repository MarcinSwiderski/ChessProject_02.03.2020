package pwr.chessproject.logger;

public class Logger {

    public enum Mode {
        debug, release
    }

    private static Mode mode = Mode.debug;

    public static void debug(String message) {
        if (mode.equals(Mode.debug))
            System.out.println("#\t"+message);

    }

    public static void release(String message) {
        if (mode.equals(Mode.release) || mode.equals(Mode.debug))
            System.out.println(message);

    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        Logger.mode = mode;
    }
}
