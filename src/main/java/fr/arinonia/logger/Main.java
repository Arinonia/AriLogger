package fr.arinonia.logger;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        new Main();
    }
    public Main() {
        LoggerManager.start(new File("c:/", "logs"));
        LoggerManager.clearOldLogs(1);
        LoggerManager.log(this.getClass(), "Ayooooo");
        LoggerManager.log(this.getClass(), "Ayooooo", LoggerColor.GREEN);
        LoggerManager.log(this.getClass(), "Ayooooo", LoggerColor.GREEN, LoggerType.DEBUG);
        LoggerManager.log(EnumLogger.DESIGN, this.getClass(), "Refresh news", LoggerColor.BLUE, LoggerType.DEBUG);
        LoggerManager.debug(EnumLogger.FILES, this.getClass(), "Clearing useless files", LoggerColor.YELLOW);
        LoggerManager.debug(EnumLogger.FILES, this.getClass(), "Save settings");
        LoggerManager.log(this.getClass(), "Useless message", LoggerColor.PURPLE, LoggerType.FATAL);

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

}
