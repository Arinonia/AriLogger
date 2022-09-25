package fr.arinonia.logger;

import fr.arinonia.logger.discord.DiscordWebhook;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LoggerManager {
    private static final BlockingQueue<Logger> queue = new ArrayBlockingQueue<>(128);
    private static boolean started = false;
    private static File _folder;


    /**
     *
     * @param folder where logs will be written
     */
    public static void start(final File folder) {
        if (!started) {
            _folder = folder;
            started = true;
            new LoggerThread(queue, folder).start();
        } else {
            System.err.println("'[LoggerManager]' StartException: the logger is already started");
        }
    }

    public static void log(final Logger logger) {
        if (!queue.offer(logger)) {
            System.err.println("'[LoggerManager]' Can't add '" + logger.getMessage() + "' in log queue");
        }
    }
    public static void log(EnumLogger enumLogger, Class<?> clazz, String message, LoggerColor color, LoggerType type) {
        log(new Logger(enumLogger, clazz, message, color, type));
    }
    public static void log(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor color) {
        log(new Logger(enumLogger, clazz, message, color));
    }

    public static void log(final EnumLogger enumLogger, final Class<?> clazz, final String message) {
        log(new Logger(enumLogger, clazz, message));
    }

    public static void log(final Class<?> clazz, final String message, final LoggerColor color) {
        log(new Logger(EnumLogger.DEFAULT, clazz, message, color));
    }
    public static void log(final Class<?> clazz, final String message) {
        log(new Logger(EnumLogger.DEFAULT, clazz, message));
    }
    public static void log(final Class<?> clazz, final String message, final LoggerColor loggerColor, final LoggerType type) {
        log(new Logger(EnumLogger.DEFAULT, clazz, message, loggerColor, type));
    }

    public static void log(final Logger logger, final DiscordWebhook discordWebhook) {
        log(logger);
        try {
            discordWebhook.execute();
        } catch (IOException e) {
            System.err.println("'[LoggerManager]' Can't execute discord webhook");
        }
    }
    public static void log(EnumLogger enumLogger, Class<?> clazz, String message, LoggerColor color, LoggerType type, final DiscordWebhook discordWebhook) {
        log(new Logger(enumLogger, clazz, message, color, type), discordWebhook);
    }
    public static void log(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor color, final DiscordWebhook discordWebhook) {
        log(new Logger(enumLogger, clazz, message, color), discordWebhook);
    }

    public static void log(final EnumLogger enumLogger, final Class<?> clazz, final String message, final DiscordWebhook discordWebhook) {
        log(new Logger(enumLogger, clazz, message), discordWebhook);
    }

    public static void log(final Class<?> clazz, final String message, final LoggerColor color, final DiscordWebhook discordWebhook) {
        log(new Logger(EnumLogger.DEFAULT, clazz, message, color), discordWebhook);
    }
    public static void log(final Class<?> clazz, final String message, final DiscordWebhook discordWebhook) {
        log(new Logger(EnumLogger.DEFAULT, clazz, message), discordWebhook);
    }
    public static void log(final Class<?> clazz, final String message, final LoggerColor loggerColor, final LoggerType type, final DiscordWebhook discordWebhook) {
        log(new Logger(EnumLogger.DEFAULT, clazz, message, loggerColor, type), discordWebhook);
    }
    public static void error(final EnumLogger enumLogger, final Class<?> clazz, final String message) {
        log(new Logger(enumLogger, clazz, message, LoggerColor.RED));
    }

    public static void error(final EnumLogger enumLogger, final Class<?> clazz, final String message, final DiscordWebhook discordWebhook) {
        log(new Logger(enumLogger, clazz, message, LoggerColor.RED), discordWebhook);
    }
    public static void debug(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor loggerColor) {
        log(enumLogger, clazz, message, loggerColor, LoggerType.DEBUG);
    }
    public static void debug(final EnumLogger enumLogger, final Class<?> clazz, final String message) {
        log(enumLogger, clazz, message, null, LoggerType.DEBUG);
    }
    public static void debug(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor loggerColor, final DiscordWebhook discordWebhook) {
        log(enumLogger, clazz, message, loggerColor, LoggerType.DEBUG, discordWebhook);
    }
    public static void debug(final EnumLogger enumLogger, final Class<?> clazz, final String message, final DiscordWebhook discordWebhook) {
        log(enumLogger, clazz, message, null, LoggerType.DEBUG, discordWebhook);
    }
    public static void logStackTrace(final EnumLogger enumLogger, final Class<?> clazz, Throwable t) {
        t.printStackTrace();
        try (final CharArrayWriter writer = new CharArrayWriter()) {
            t.printStackTrace(new PrintWriter(writer));
            error(enumLogger, clazz, writer.toString());
        }
    }
    public static void logStackTrace(final EnumLogger enumLogger, final Class<?> clazz, Throwable t, final DiscordWebhook discordWebhook) {
        t.printStackTrace();
        try (final CharArrayWriter writer = new CharArrayWriter()) {
            t.printStackTrace(new PrintWriter(writer));
            error(enumLogger, clazz, writer.toString(), discordWebhook);
        }
    }

    /**
     * Delete old logs files (7 day after creating)
     */
    public static void clearOldLogs() {
        clearOldLogs(7);
    }

    /**
     *
     * @param days days after logs file will be deleted
     */
    public static void clearOldLogs(final int days) {
        final File[] logs = _folder.listFiles();

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
