package fr.arinonia.logger.discord;

public class Footer {
    private final String text;
    private final String iconUrl;

    public Footer(final String text, final String iconUrl) {
        this.text = text;
        this.iconUrl = iconUrl;
    }

    public String getText() {
        return this.text;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }
}
