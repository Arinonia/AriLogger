package fr.arinonia.logger;

public enum LoggerColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    ORANGE("\u001B[36m");

    private final String color;

    LoggerColor(final String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
