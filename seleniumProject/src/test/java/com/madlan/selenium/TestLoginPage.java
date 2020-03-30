package com.madlan.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class TestLoginPage {
        private final Logger logger = LoggerFactory.getLogger(TestLoginPage.class);
        private String validateCredentials = "https://opensource-demo.orangehrmlive.com/index.php/auth/validateCredentials";
        private WebDriver driver;
        private String baseUrl;
        private boolean acceptNextAlert = true;
        private StringBuffer verificationErrors = new StringBuffer();
        private LibraryUtils libraryUtils = new LibraryUtils();
        private String[] extractedCredentials = new String[2];
        private String[] credentials = new String[2];


        @BeforeClass(alwaysRun = true)
        public void setUp() throws Exception {
            System.setProperty("webdriver.chrome.driver","./src/main/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        @Test
        public void testLogin() throws Exception {
            driver.get("https://opensource-demo.orangehrmlive.com/");
            driver.manage().window().maximize();
            OrangehrmliveLogin orangehrmliveLogin = new OrangehrmliveLogin(driver);
            extractedCredentials=orangehrmliveLogin.gettingUserNameAndPassword();
            orangehrmliveLogin.clickLogin();
            assertEquals(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Directory'])[1]/following::h1[1]")).getText(), "Dashboard");
            HttpURLConnectionClass httpURLConnectionClass = new HttpURLConnectionClass(validateCredentials);
            String res=httpURLConnectionClass.sendPost();
            credentials=libraryUtils.gettingUsernameAndPasswordFromValidateCredentials(res);
            logger.info("Starting to compare 'username' and 'password'...");
            logger.info("Original 'username' is " + credentials[0]);
            logger.info("Original 'password' is: " + credentials[1]);

            logger.info("Extracted 'username' is " + extractedCredentials[0]);
            logger.info("Extracted 'password' is: " + extractedCredentials[1]);
            //Check that credentials are correct and equal
            assertEquals(extractedCredentials[0], credentials[0]);
            assertEquals(extractedCredentials[1], credentials[1]);
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


