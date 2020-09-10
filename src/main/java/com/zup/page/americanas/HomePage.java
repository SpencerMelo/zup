package com.zup.page.americanas;

import com.zup.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    private static final String URL = "https://www.americanas.com.br/";

    @FindBy(id = "h_search-input")
    private WebElement searchInput;

    @FindBy(id = "h_search-btn")
    private WebElement searchButton;

    @FindBy(id = "h_minicart")
    private WebElement cartIcon;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void goTo() {
        webDriver.get(URL);
    }

    public void searchBy(String text) {
        waitVisibilityOf(searchInput);
        searchInput.sendKeys(text);
    }

    public void submit() {
        waitElementToBeClickable(searchButton);
        searchButton.click();
    }
}
