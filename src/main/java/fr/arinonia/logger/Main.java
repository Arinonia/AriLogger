package fr.arinonia.logger;

import fr.arinonia.logger.discord.DiscordWebhook;
import fr.arinonia.logger.discord.EmbedObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        try {
            new Main();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public Main() throws UnknownHostException {
        LoggerManager.start(new File("c:/", "logs"));
        LoggerManager.clearOldLogs(1);
        LoggerManager.log(this.getClass(), "Ayooooo");
        LoggerManager.log(this.getClass(), "Ayooooo", LoggerColor.GREEN);
        LoggerManager.log(this.getClass(), "Ayooooo", LoggerColor.GREEN, LoggerType.DEBUG);
        LoggerManager.log(EnumLogger.DESIGN, this.getClass(), "Refresh news", LoggerColor.BLUE, LoggerType.DEBUG);
        LoggerManager.debug(EnumLogger.FILES, this.getClass(), "Clearing useless files", LoggerColor.YELLOW);
        LoggerManager.debug(EnumLogger.FILES, this.getClass(), "Save settings");
        LoggerManager.log(this.getClass(), "Useless message", LoggerColor.PURPLE, LoggerType.FATAL);

        final DiscordWebhook discordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/1014247429807276104/LK69wMpeMr0IuQ7GK7pFqlgZCMoqmWXlwhTeoFWvv-_K2rbRhaLfduBRyqAf1m1bzsX2");
        discordWebhook.setUsername("[Dragonia-Launcher]");
        EmbedObject embedObject = new EmbedObject();
        embedObject.setTitle("[Dragonia-Launcher]");
        embedObject.setThumbnail("https://downloadable.arinonia.fr/Dragonia/images/icon.png");
        embedObject.addField("Ip", InetAddress.getLocalHost().getHostAddress(), true);
        embedObject.addField("Username", "Arinonia", true);
        embedObject.setAuthor("Arinonia", null, "https://downloadable.arinonia.fr/Dragonia/images/icon.png");
        embedObject.setColor(Color.RED);
        embedObject.setDescription("Stacktrace: Java.lang.NullPointerException at Main.java line 27");
        embedObject.setFooter("@Dragonia", "https://downloadable.arinonia.fr/Dragonia/images/icon.png");
        discordWebhook.addEmbed(embedObject);

        LoggerManager.log(this.getClass(), "ptn ", LoggerColor.BLUE, discordWebhook);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}
