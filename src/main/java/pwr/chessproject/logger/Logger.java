package pwr.chessproject.logger;

public class Logger {

    public enum Mode {
        debug, release
    }

    private static Mode mode = Mode.debug;

    public static void debug(Object message) {
        if (mode.equals(Mode.debug))
            System.out.println("#\t"+message.toString());

    }

    public static void release(Object message) {
        if (mode.equals(Mode.release) || mode.equals(Mode.debug))
            System.out.println(message.toString());

    }

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        Logger.mode = mode;
    }
}
