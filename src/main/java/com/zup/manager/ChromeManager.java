package com.zup.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;

public class ChromeManager implements DriveManager {
    private ChromeDriverService driverService;

    protected void startService() throws IOException {
        driverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        driverService.start();
    }

    @Override
    public WebDriver getDriver() throws IOException {
        startService();
        return new ChromeDriver(driverService);
    }
}
