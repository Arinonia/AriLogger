package fr.arinonia.logger.discord;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class EmbedObject {

    private String title;
    private String description;
    private String url;
    private Color color;

    private Footer footer;
    private Thumbnail thumbnail;
    private Image image;
    private Author author;
    private final List<Field> fields = new ArrayList<>();

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrl() {
        return this.url;
    }

    public Color getColor() {
        return this.color;
    }

    public Footer getFooter() {
        return this.footer;
    }

    public Thumbnail getThumbnail() {
        return this.thumbnail;
    }

    public Image getImage() {
        return this.image;
    }

    public Author getAuthor() {
        return this.author;
    }

    public List<Field> getFields() {
        return this.fields;
    }

    public EmbedObject setTitle(final String title) {
        this.title = title;
        return this;
    }

    public EmbedObject setDescription(final String description) {
        this.description = description;
        return this;
    }

    public EmbedObject setUrl(final String url) {
        this.url = url;
        return this;
    }

    public EmbedObject setColor(final Color color) {
        this.color = color;
        return this;
    }

    public EmbedObject setFooter(final String text, final String icon) {
        this.footer = new Footer(text, icon);
        return this;
    }

    public EmbedObject setThumbnail(final String url) {
        this.thumbnail = new Thumbnail(url);
        return this;
    }

    public EmbedObject setImage(final String url) {
        this.image = new Image(url);
        return this;
    }

    public EmbedObject setAuthor(final String name, final String url, final String icon) {
        this.author = new Author(name, url, icon);
        return this;
    }

    public EmbedObject addField(final String name, final String value, final boolean inline) {
        this.fields.add(new Field(name, value, inline));
        return this;
    }

}
