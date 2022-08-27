package fr.arinonia.logger;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class LogEventWriter implements Closeable, Flushable {

    private final Writer writer;

    public LogEventWriter(final Writer writer) {
        this.writer = writer;
    }

    public void write(final String message) throws IOException {
        this.writer.write(message);
    }
    public void writeComment(final String comment) throws IOException{
        this.writer.write((!comment.startsWith("#") ? "#" + comment : comment));
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }
}
