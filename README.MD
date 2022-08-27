# AriLogger

![Wip](https://img.shields.io/badge/WIP-Yes-cyan.svg?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-Snapshot_1.0.0-green.svg?style=for-the-badge)
![License](https://img.shields.io/badge/License-GPL--3.0-orange.svg?style=for-the-badge)

## How to use

- Setup the logger
````java
//Folder where log will be writte
final File rootFolder = new File("c:/", "logs");
if (!rootFolder.exists()) {
    if (!rootFolder.mkdirs()) {
        System.err.println("Cannot create logs folder!");
        return;
    }
}
LoggerManager.start(rootFolder);
LoggerManager.clearOldLogs(rootFolder);
````

- Use the logger

````java
LoggerManager.error(EnumLogger.AUTH, this.getClass(), "Bad response for url :");
LoggerManager.log(EnumLogger.DESIGN, this.getClass(), "Try to refresh panel!", LoggerColor.PURPLE);
LoggerManager.log(this.getClass(), "Something useful UwU");
LoggerManager.log(this.getClass(), "Something useful with color Owo", LoggerColor.GREEN);
LoggerManager.log(EnumLogger.AUTH, this.getClass(), "User is connected :3");
LoggerManager.log(new Logger(EnumLogger.FILES, this.getClass(), "Idk why tf i create this methode"));
LoggerManager.log(new Logger(EnumLogger.UPDATE, this.getClass(), "Same for this one (D-Frek > all)", LoggerColor.YELLOW));
try {
    //something();
} catch (final Throwable t) {
    //Log an error
    LoggerManager.logStackTrace(EnumLogger.LAUNCH, this.getClass(), t);
}
````

Don't forget to give me a haaaannnn yeaaaaa.. A NO A STAR I MEAN!