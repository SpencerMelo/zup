package com.zup.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected final int WAIT_TIMEOUT = 15;
    protected final WebDriver webDriver;
    protected final WebDriverWait webDriverWait;

    protected BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
        webDriverWait = new WebDriverWait(webDriver, WAIT_TIMEOUT);
    }

    protected void waitVisibilityOf(By by) {
        webDriverWait.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void waitVisibilityOf(WebElement element) {
        webDriverWait.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitElementToBeClickable(WebElement element){
        webDriverWait.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(element));
    }
}
