package com.zup.page.americanas;

import com.zup.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    @FindBy(id = "product-name-default")
    private WebElement productName;

    @FindBy(xpath = "//*[text()=\"comprar\"]")
    private WebElement buyButton;

    @FindBy(xpath = "//span[text()=\"Sim, continuar\"]")
    private WebElement confirmBuyButton;

    @FindBy(xpath = "//div[@type=\"Tamanho\"]/span/span")
    private WebElement sizeLabel;

    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getProductName() {
        waitVisibilityOf(productName);
        return productName.getText();
    }

    public void clickBuy() {
        waitElementToBeClickable(buyButton);
        buyButton.click();
    }

    public boolean isConfirmationPopupOpen() {
        try {
            waitElementToBeClickable(confirmBuyButton);
            return true;
        } catch (TimeoutException timeoutException) {
            return false;
        }
    }

    public void selectSize(String value) {
        By optionXpath = By.xpath(String.format("//div[@type=\"Tamanho\"]//span[text()=\"%s\"]", value));
        waitVisibilityOf(optionXpath);
        WebElement sizeOption = webDriver.findElement(optionXpath);
        sizeOption.click();
    }

    public String getSelectedSize(){
        waitVisibilityOf(sizeLabel);
        return sizeLabel.getText();
    }

    public void confirmBuy() {
        waitElementToBeClickable(confirmBuyButton);
        confirmBuyButton.click();
    }
}
