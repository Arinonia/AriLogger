package fr.arinonia.logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private final EnumLogger enumLogger;
    private final Class<?> clazz;
    private final String message;
    private final LoggerColor loggerColor;


    public Logger(final EnumLogger enumLogger, final Class<?> clazz, final String message, final LoggerColor loggerColor) {
        this.enumLogger = enumLogger;
        this.clazz = clazz;
        this.message = (!message.endsWith(System.getProperty("line.separator")) ? message + System.getProperty("line.separator") : message);
        this.loggerColor = loggerColor;

    }

    public Logger(final EnumLogger enumLogger, final Class<?> clazz, final String message) {
        this(enumLogger, clazz, message, null);
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
    @Override
    public String toString() {
        final String date = String.format("[%s]", new SimpleDateFormat("kk:mm:ss").format(new Date()));
        final String type = String.format("[%s]", enumLogger.name());
        final String clazz = String.format("[%s]", this.clazz.getSimpleName() + ".java");
        return (this.loggerColor == null ? date + type + clazz + " " +  message + LoggerColor.RESET.getColor() : this.loggerColor.getColor() + date + type + clazz + " " +  message + LoggerColor.RESET.getColor());
    }
}
