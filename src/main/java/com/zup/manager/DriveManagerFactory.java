package com.zup.manager;

public class DriveManagerFactory {
    public static DriveManager getDriverManager(String browser) {
        switch (browser) {
            case "firefox":
                return new FirefoxManager();
            case "chrome":
            default:
                return new ChromeManager();
        }
    }
}
