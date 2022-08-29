package fr.arinonia.logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private final EnumLogger enumLogger;
    private final Class<?> clazz;
    private final String message;
    private final LoggerColor loggerColor;
    private final LoggerType loggerType;

    public static DebugPriority debugPriority = DebugPriority.LOW;

    public Logger(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor loggerColor, final LoggerType loggerType) {
        this.enumLogger = enumLogger;
        this.clazz = clazz;
        this.message = (!message.endsWith(System.getProperty("line.separator")) ? message + System.getProperty("line.separator") : message);
        this.loggerColor = loggerColor;
        this.loggerType = loggerType;
    }

    public Logger(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor loggerColor) {
        this(enumLogger, clazz, message, loggerColor, LoggerType.NULL);
    }

    public Logger(final EnumLogger enumLogger, final Class<?> clazz, final String message) {
        this(enumLogger, clazz, message, null, LoggerType.NULL);
    }

    public Logger(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerType loggerType) {
        this(enumLogger, clazz, message, null, loggerType);
    }


    public void post(final LogEventWriter writer) {
        System.out.print(this);

        try {
            String content = this.toString();

            for (final LoggerColor loggerColor1 : LoggerColor.values()) {
                if (content.contains(loggerColor1.getColor())) {
                    content = content.replace(loggerColor1.getColor(), "");
                }
            }
            writer.write(content);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public EnumLogger getEnumLogger() {
        return this.enumLogger;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public String getMessage() {
        return this.message;
    }

    public LoggerColor getLoggerColor() {
        return this.loggerColor;
    }

    public LoggerType getLoggerType() {
        return this.loggerType;
    }

    public static DebugPriority getDebugPriority() {
        return debugPriority;
    }

    @Override
    public String toString() {
        final String date = String.format("[%s]", new SimpleDateFormat("kk:mm:ss").format(new Date()));
        final String type = String.format("[%s]", this.loggerType.name());
        final String category = String.format("[%s]", enumLogger.name());
        final String clazz = String.format("[%s]", this.clazz.getSimpleName() + ".java");

        final StringBuilder builder = new StringBuilder();

        if (this.loggerColor != null) {
            builder.append(this.loggerColor.getColor());
        }
        builder.append(date);
        if (this.loggerType != LoggerType.NULL) {
            builder.append(type);
        }

        if (this.enumLogger != EnumLogger.DEFAULT) {
            builder.append(category);
        }

        builder.append(clazz)
                .append(" ")
                .append(this.message)
                .append(LoggerColor.RESET.getColor());


        return builder.toString();
    }
}
