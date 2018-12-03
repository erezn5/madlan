package com.madlan.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class TestPhpTravelsPage {

        private WebDriver driver;
        private String baseUrl = "https://www.phptravels.net/";
        private boolean acceptNextAlert = true;
        private StringBuffer verificationErrors = new StringBuffer();
        private LibraryUtils libraryUtils = new LibraryUtils();
        private final Logger logger = LoggerFactory.getLogger(TestPhpTravelsPage.class);

        @BeforeClass(alwaysRun = true)
        public void setUp() throws Exception {
            System.setProperty("webdriver.chrome.driver","./src/main/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        @Test
        public void changeLanguageAndVerify() throws Exception {
            logger.info("Navigating to: " + baseUrl);
            driver.get(baseUrl);

            MainPageTravels mainPageTravels = new MainPageTravels(driver);
            mainPageTravels.changeLanguage();
            assertEquals(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='|'])[1]/preceding::span[1]")).getText(), "Llamar ahora: +92-3311442244");
        }

        @Test
        public void testBookAFlight() throws InterruptedException {
            driver.get(baseUrl);
            PageFlights pageFlights  = new PageFlights(driver,"tlv", "barce", "2019-01-03", "2019-01-06");
            pageFlights.bookAFlight();

            WebElement tableFlights = driver.findElement(By.id("load_data"));
            List<WebElement> tableNumberOfFlights = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(tableFlights, By.tagName("tr")));
            assertTrue(!tableNumberOfFlights.isEmpty());
            logger.info("Number of found flights: " + tableNumberOfFlights.size());

        }

        @AfterClass(alwaysRun = true)
        public void tearDown() throws Exception {
            driver.quit();
            String verificationErrorString = verificationErrors.toString();
            if (!"".equals(verificationErrorString)) {
                fail(verificationErrorString);
            }
        }

        private boolean isElementPresent(By by) {
            try {
                driver.findElement(by);
                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }

        private boolean isAlertPresent() {
            try {
                driver.switchTo().alert();
                return true;
            } catch (NoAlertPresentException e) {
                return false;
            }
        }

        private String closeAlertAndGetItsText() {
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                if (acceptNextAlert) {
                    alert.accept();
                } else {
                    alert.dismiss();
                }
                return alertText;
            } finally {
                acceptNextAlert = true;
            }
        }
    }


