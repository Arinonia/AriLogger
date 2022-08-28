package fr.arinonia.logger;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LoggerManager {
    private static final BlockingQueue<Logger> queue = new ArrayBlockingQueue<Logger>(128);


    /**
     *
     * @param folder where logs will be written
     */
    public static void start(final File folder) {
        new LoggerThread(queue, folder).start();
    }

    public static void log(final Logger logger) {
        queue.offer(logger);
    }

    public static void log(final EnumLogger enumLogger, final Class<?> clazz, final String message) {
        queue.offer(new Logger(enumLogger, clazz, message));
    }
    public static void log(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor color) {
        queue.offer(new Logger(enumLogger, clazz, message, color));
    }
    public static void log(final Class<?> clazz, final String message, final LoggerColor color) {
        queue.offer(new Logger(EnumLogger.UTILS, clazz, message, color));
    }
    public static void log(final Class<?> clazz, final String message) {
        queue.offer(new Logger(EnumLogger.UTILS, clazz, message));
    }
    public static void error(final EnumLogger enumLogger, final Class<?> clazz, final String message) {
        queue.offer(new Logger(enumLogger, clazz, message, LoggerColor.RED));
    }
    public static void logStackTrace(final EnumLogger enumLogger, final Class<?> clazz, Throwable t) {
        t.printStackTrace();
        try (final CharArrayWriter writer = new CharArrayWriter()) {
            t.printStackTrace(new PrintWriter(writer));
            error(enumLogger, clazz, writer.toString());
        }
    }

    /**
     * Delete old logs files (7 day after creating)
     * @param logsDir folder where logs are written
     */
    public static void clearOldLogs(final File logsDir) {
        clearOldLogs(logsDir, 7);
    }

    /**
     *
     * @param logsDir folder where logs are written
     * @param days after logs file will be deleted
     */
    public static void clearOldLogs(final File logsDir, final int days) {
        final File[] logs = logsDir.listFiles();

        if (logs != null) {
            Date toDeleteAfter = new Date();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDeleteAfter);
            calendar.add(Calendar.DATE, -days);
            toDeleteAfter = calendar.getTime();

            for (final File log_file : logs) {
                try {
                    final Date date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").parse(log_file.getName().replace("launcher-log_", "").replace(".txt", ""));
                    if (date.before(toDeleteAfter)) {
                        if (log_file.exists()) {
                            if (!log_file.delete()) {
                                System.err.println("File " + log_file.getAbsolutePath() + " couldn't be " + "deleted");
                            }
                        }
                    }
                } catch (final ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
