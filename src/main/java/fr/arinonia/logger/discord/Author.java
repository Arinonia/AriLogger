package fr.arinonia.logger.discord;

public class Author {
    private final String name;
    private final String url;
    private final String iconUrl;

    public Author(final String name, final String url, final String iconUrl) {
        this.name = name;
        this.url = url;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }
}
