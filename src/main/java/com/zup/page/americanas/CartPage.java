package com.zup.page.americanas;

import com.zup.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {
    private static final String PRODUCT_XPATH = "//img[@alt=\"%s\"]";
    private static final String REMOVE_XPATH = PRODUCT_XPATH.concat("/ancestor::li//span[text()=\"remover\"]");

    @FindBy(xpath = "//h2[text()=\"Sua cesta está vazia\"]")
    private WebElement emptyCartTitle;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isProductOnCart(String name) {
        try {
            waitVisibilityOf(By.xpath(String.format(PRODUCT_XPATH, name)));
            return true;
        } catch (TimeoutException timeoutException){
          return false;
        }
    }

    public boolean isCartEmpty() {
        try {
            waitVisibilityOf(emptyCartTitle);
            return true;
        } catch (TimeoutException timeoutException){
            return false;
        }
    }

    public void removeFromCart(String name) {
        waitVisibilityOf(By.xpath(String.format(REMOVE_XPATH, name)));
        WebElement productRemoveButton = webDriver.findElement(By.xpath(String.format(REMOVE_XPATH, name)));
        productRemoveButton.click();
    }
}
