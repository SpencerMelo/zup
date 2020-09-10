package com.zup.manager;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

public interface DriveManager {
    WebDriver getDriver() throws IOException;
}
