package com.zup.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;
import java.io.IOException;

public class FirefoxManager implements DriveManager {
    private GeckoDriverService driverService;

    protected void startService() throws IOException {
        driverService = new GeckoDriverService.Builder()
                .usingDriverExecutable(new File("geckodriver.exe"))
                .usingAnyFreePort()
                .build();
        driverService.start();
    }

    @Override
    public WebDriver getDriver() throws IOException {
        startService();
        return new FirefoxDriver(driverService);
    }
}
