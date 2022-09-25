package fr.arinonia.logger.discord;

public class Field {
    private final String name;
    private final String value;
    private final boolean inline;

    public Field(final String name, final String value, final boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isInline() {
        return this.inline;
    }
}
