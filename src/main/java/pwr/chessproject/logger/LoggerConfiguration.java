package pwr.chessproject.logger;

import java.util.Arrays;

/**
 * This class configures the static logger
 */
public class LoggerConfiguration {
    /**
     * Tries to read mode from java command line arguments, then tries to read it from environment variable 'MODE'.
     * If all of it fails sets mode to 'Logger.mode.release'.
     * @param args Presumably command line arguments
     * @return The mode specified in environment or 'Logger.mode.release'.
     */
    public Logger.Mode configureLogMode(String[] args) {
        if (args.length > 0) {
            String argsLine = Arrays.toString(args).toLowerCase();
            if (argsLine.contains("debug"))
                return Logger.Mode.debug;
            else if (argsLine.contains("release") || argsLine.contains("production"))
                return Logger.Mode.release;
        }
            try {
                return Logger.Mode.valueOf(System.getenv("MODE").toLowerCase());
            }
            catch (IllegalArgumentException | NullPointerException ex) {
                Logger.debug(ex);
            }
        return Logger.Mode.release;
    }
}
