package com.zup.page.americanas;

import com.zup.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultPage extends BasePage {
    private static final String PRODUCT_XPATH = "//h2[text()=\"%s\"]";

    @FindBy(css = "div[data-tracker=searchquery-main] h1")
    private WebElement pageTitle;

    @FindBy(xpath = "//div[contains(@class, \"EmptyPage__Container\")]")
    private WebElement emptyContainer;

    public SearchResultPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void selectProduct(String name){
        if (isProductAvailable(name)) {
            WebElement targetProduct = webDriver.findElement(By.xpath(String.format(PRODUCT_XPATH, name)));
            targetProduct.click();
        } else {
            throw new RuntimeException("Product not available on the results.");
        }
    }

    public String getTitle (){
        waitVisibilityOf(pageTitle);
        return pageTitle.getText();
    }

    public boolean isProductAvailable(String name){
        try {
            waitVisibilityOf(By.xpath(String.format(PRODUCT_XPATH, name)));
            return true;
        } catch (TimeoutException timeoutException){
            return false;
        }
    }

    public int productsCountWithoutCriterion(String criterion) {
        By noCriterionXpath = By.xpath("//div[@id='content-middle']//h2[not(contains(text(), '" + criterion + "'))]");
        return webDriver.findElements(noCriterionXpath).size();
    }

    public boolean isEmpty() {
        try {
            waitVisibilityOf(emptyContainer);
            return true;
        } catch (TimeoutException timeoutException){
            return false;
        }
    }
}
