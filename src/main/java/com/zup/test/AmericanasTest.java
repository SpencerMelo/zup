package com.zup.test;

import com.zup.extension.Report;
import com.zup.manager.DriveManagerFactory;
import com.zup.page.americanas.CartPage;
import com.zup.page.americanas.HomePage;
import com.zup.page.americanas.ProductPage;
import com.zup.page.americanas.SearchResultPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AmericanasTest {

    @Nested
    @TestMethodOrder(OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(Report.class)
    class CartFlow {
        private final String PRODUCT_EXAMPLE = "Zup Zup - 0869 Calesita";

        private WebDriver webDriver;
        private CartPage cartPage;
        private HomePage homePage;
        private ProductPage productPage;
        private SearchResultPage searchResultPage;

        @BeforeAll
        void beforeAllSetup() throws IOException {
            webDriver = DriveManagerFactory.getDriverManager("chrome").getDriver();
            cartPage = new CartPage(webDriver);
            homePage = new HomePage(webDriver);
            productPage = new ProductPage(webDriver);
            searchResultPage = new SearchResultPage(webDriver);
        }

        @Order(1)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToSearchForProduct(String productName) {
            homePage.goTo();
            homePage.searchBy(productName);
            homePage.submit();
            assertEquals(productName.toLowerCase(), searchResultPage.getTitle().toLowerCase());
            assertTrue(searchResultPage.isProductAvailable(productName));
        }

        @Order(2)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToSelectAnItem(String productName) {
            searchResultPage.selectProduct(productName);
            assertEquals(productName, productPage.getProductName());
        }

        @Order(3)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToAddItemToTheCart(String productName) {
            productPage.clickBuy();
            assertTrue(cartPage.isProductOnCart(productName));
        }

        @Order(4)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToRemoveItemFromTheCart(String productName) {
            cartPage.removeFromCart(productName);
            assertTrue(cartPage.isCartEmpty());
        }

        @AfterAll
        void tearDown() {
            if (webDriver != null) webDriver.quit();
        }
    }

    @Nested
    @TestMethodOrder(OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(Report.class)
    class CartFlowWithConfirmation {
        private final String PRODUCT_EXAMPLE = "Blusa Frio Moletom Grupo Bts Galáxia";

        private WebDriver webDriver;
        private CartPage cartPage;
        private HomePage homePage;
        private ProductPage productPage;
        private SearchResultPage searchResultPage;

        @BeforeAll
        void beforeAllSetup() throws IOException {
            webDriver = DriveManagerFactory.getDriverManager("chrome").getDriver();
            cartPage = new CartPage(webDriver);
            homePage = new HomePage(webDriver);
            productPage = new ProductPage(webDriver);
            searchResultPage = new SearchResultPage(webDriver);
        }

        @Order(1)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToSearchForProduct(String productName) {
            homePage.goTo();
            homePage.searchBy(productName);
            homePage.submit();
            assertEquals(productName.toLowerCase(), searchResultPage.getTitle().toLowerCase());
            assertTrue(searchResultPage.isProductAvailable(productName));
        }

        @Order(2)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToSelectAnItem(String productName) {
            searchResultPage.selectProduct(productName);
            assertEquals(productName, productPage.getProductName());
        }

        @Order(3)
        @ParameterizedTest
        @ValueSource(strings = "GG")
        void userShouldBeAbleToSelectTheSize(String productSize) {
            productPage.selectSize(productSize);
            assertEquals(productSize.toLowerCase(), productPage.getSelectedSize().toLowerCase());
        }

        @Order(4)
        @Test
        void userShouldBeAbleToAddItemToTheCart() {
            productPage.clickBuy();
            assertTrue(productPage.isConfirmationPopupOpen());
        }

        @Order(5)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToConfirmTheBuyAction(String productName) {
            productPage.confirmBuy();
            assertTrue(cartPage.isProductOnCart(productName));
        }

        @Order(6)
        @ParameterizedTest
        @ValueSource(strings = PRODUCT_EXAMPLE)
        void userShouldBeAbleToRemoveItemFromTheCart(String productName) {
            cartPage.removeFromCart(productName);
            assertTrue(cartPage.isCartEmpty());
        }

        @AfterAll
        void tearDown() {
            if (webDriver != null) webDriver.quit();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(Report.class)
    class UnhappySearch {
        private final String UNHAPPY_PRODUCT_EXAMPLE = "----------------------------------";

        private WebDriver webDriver;
        private HomePage homePage;
        private SearchResultPage searchResultPage;

        @BeforeAll
        void beforeAllSetup() throws IOException {
            webDriver = DriveManagerFactory.getDriverManager("chrome").getDriver();
            homePage = new HomePage(webDriver);
            searchResultPage = new SearchResultPage(webDriver);
        }

        @ParameterizedTest
        @ValueSource(strings = UNHAPPY_PRODUCT_EXAMPLE)
        void searchShouldBeAbleToHandleSpecialCharacters(String productName) {
            homePage.goTo();
            homePage.searchBy(productName);
            homePage.submit();
            /*
            It will wait for 'N' seconds as per explicit wait from selenium, for speed improvement, we could:
            1- use rest-assured to get the status before moving with the test (4** or 5** == throw).
                a. it is faster as we don't need to wait the page load.
                b. it seems to be more stable.
            2- use selenium to search for the 4** or something on the UI in case it has an redirect page.
                a. it will not require any new dependencies.
                b. it doesn't seems to be stable.
            */
            assertTrue(searchResultPage.isEmpty(), "'No results' screen, not found.");
        }

        @AfterAll
        void tearDown() {
            if (webDriver != null) webDriver.quit();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(Report.class)
    class PartialMatchForCriteria {
        private final String NOT_SO_HAPPY_PRODUCT_EXAMPLE = "I'm here for some purpose that I don't really know.";

        private WebDriver webDriver;
        private HomePage homePage;
        private SearchResultPage searchResultPage;

        @BeforeAll
        void beforeAllSetup() throws IOException {
            webDriver = DriveManagerFactory.getDriverManager("chrome").getDriver();
            homePage = new HomePage(webDriver);
            searchResultPage = new SearchResultPage(webDriver);
        }

        @ParameterizedTest
        @ValueSource(strings = NOT_SO_HAPPY_PRODUCT_EXAMPLE)
        void userShouldBeAbleToSomeResultInCaseOfPartialMatch(String productName) {
            homePage.goTo();
            homePage.searchBy(productName);
            homePage.submit();
            assertFalse(searchResultPage.isProductAvailable(productName));
        }

        @AfterAll
        void tearDown() {
            if (webDriver != null) webDriver.quit();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(Report.class)
    class NoMatchForCriteria {
        private final String NOT_SO_HAPPY_PRODUCT_EXAMPLE = "aehaaeha aehaehaeha";

        private WebDriver webDriver;
        private HomePage homePage;
        private SearchResultPage searchResultPage;

        @BeforeAll
        void beforeAllSetup() throws IOException {
            webDriver = DriveManagerFactory.getDriverManager("chrome").getDriver();
            homePage = new HomePage(webDriver);
            searchResultPage = new SearchResultPage(webDriver);
        }

        @ParameterizedTest
        @ValueSource(strings = NOT_SO_HAPPY_PRODUCT_EXAMPLE)
        void userShouldBeAbleToSeTheEmptyResultInCaseOfNoMatch(String productName) {
            homePage.goTo();
            homePage.searchBy(productName);
            homePage.submit();
            assertFalse(searchResultPage.isProductAvailable(productName));
        }

        @AfterAll
        void tearDown() {
            if (webDriver != null) webDriver.quit();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(Report.class)
    class GenericSearch {
        private final String GENERIC_PRODUCT_EXAMPLE = "Blusa";

        private WebDriver webDriver;
        private HomePage homePage;
        private SearchResultPage searchResultPage;

        @BeforeAll
        void beforeAllSetup() throws IOException {
            webDriver = DriveManagerFactory.getDriverManager("chrome").getDriver();
            homePage = new HomePage(webDriver);
            searchResultPage = new SearchResultPage(webDriver);
        }

        @ParameterizedTest
        @ValueSource(strings = GENERIC_PRODUCT_EXAMPLE)
        void allResultsShouldContainTheGenericCriterion(String productName) {
            homePage.goTo();
            homePage.searchBy(productName);
            homePage.submit();
            assertEquals(0, searchResultPage.productsCountWithoutCriterion(productName));
        }

        @AfterAll
        void tearDown() {
            if (webDriver != null) webDriver.quit();
        }
    }
}
