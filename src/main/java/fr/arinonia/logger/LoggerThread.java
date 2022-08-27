package fr.arinonia.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class LoggerThread extends Thread {
    private final BlockingQueue<Logger> queue;
    private final LogEventWriter writer;

    private static final String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    private static final String fileName = "launcher" + "-log_" + date + ".txt";

    public LoggerThread(final BlockingQueue<Logger> queue, final File folder) {
        super("Logger-Thread");
        this.queue = queue;

        final File file = new File(folder, fileName);

        try {
            this.writer = new LogEventWriter(new FileWriter(file));
            this.writer.write("Generated on " + date + System.getProperty("line.separator"));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    writer.close();
                }
                catch (final IOException exception) {
                    exception.printStackTrace();
                }
            }));
        } catch (final IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Couldn't create LogEventWriter");
        }
    }

    @Override
    public void run() {
        while (true) {
            Logger next;
            try {
                next = this.queue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            if (next != null) {
                next.post(this.writer);
            }
        }
    }
}
