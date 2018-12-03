package com.madlan.selenium;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageFlights {
    WebDriver driver;
    LibraryUtils libraryUtils = new LibraryUtils();
    private final Logger logger = LoggerFactory.getLogger(PageFlights.class);
    String startDate;
    String endDate;
    String sourceAirport;
    String destinationAirport;
    @FindBy(how= How.XPATH,xpath = "//*[@id=\"body-section\"]/section/div[2]/div/div/div[2]/ul/li[2]/a")
    WebElement tabFlights;
    @FindBy(how = How.XPATH,xpath = "//*[@id=\"flights\"]/form/div[1]")
    WebElement clickOnSourceSearchBoxToBeVisible;
    @FindBy(how = How.XPATH, xpath = "//*[@id='select2-drop']/div/input")
    WebElement insertAirPort;
    @FindBy(how = How.XPATH, xpath = "//*[@id=\"flights\"]/form/div[2]")
    WebElement clickOnDestionationSearchBoxToBeVisible;
    @FindBy(how = How.XPATH, xpath = "//*[@id=\"flights\"]/form/div[9]/div[2]/div/div/ins")
    WebElement roundTrip;
    @FindBy(how = How.NAME, name = "cabinclass")
    WebElement openDropDownMenu;
    @FindBy(how = How.NAME, name = "departure")
    WebElement departure;
    @FindBy(how = How.NAME, name = "arrival")
    WebElement arrival;
    @FindBy(how = How.XPATH, xpath = "//*[@id=\"flights\"]/form/div[6]/button")
    WebElement clickOnSearchButton;

    public PageFlights(WebDriver driver, String sourceAirport, String destinationAirport, String startDate, String endDate){
        logger.info("Setting webdriver and page web elements: ");
        this.driver = driver;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.startDate = startDate;
        this.endDate = endDate;
        PageFactory.initElements(driver, this);
    }

    private void clickOnFlightsTab(){
        logger.info("Clicking on 'Flights' tab");
        libraryUtils.waitForElementToBeVisible(driver, tabFlights,15).click();
    }


    private void setInsertSourceAirPort() throws InterruptedException {
        logger.info("Setting source airport");
        libraryUtils.waitForElementToBeVisible(driver,clickOnSourceSearchBoxToBeVisible,15).click();
        libraryUtils.waitForElementToBeVisible(driver,insertAirPort,15).sendKeys(sourceAirport);
        Thread.sleep(2000);
        libraryUtils.waitForElementToBeVisible(driver,insertAirPort,15).sendKeys(Keys.ENTER);
    }


    private void setInsertDestinationAirPort() throws InterruptedException {
        logger.info("Setting destination airport");
        libraryUtils.waitForElementToBeVisible(driver, clickOnDestionationSearchBoxToBeVisible,15).click();
        libraryUtils.waitForElementToBeVisible(driver,insertAirPort,15).sendKeys(destinationAirport);
        Thread.sleep(2000);
        libraryUtils.waitForElementToBeVisible(driver,insertAirPort,15).sendKeys(Keys.ENTER);
    }

    private void clickOnRoundTrip(){
        logger.info("Setting 'Round Trip'");
        roundTrip.click();
    }

    private void setOpenDropDownMenuAndSelectBusiness(){
        logger.info("Setting 'Business' class");
        libraryUtils.waitForElementToBeVisible(driver,openDropDownMenu,15).click();
        new Select(libraryUtils.waitForElementToBeVisible(driver,openDropDownMenu,15)).selectByVisibleText("Business");

    }

    private void setDeparture(){
        logger.info("Setting departure date");
        //"2019-01-03"
        libraryUtils.waitForElementToBeVisible(driver,departure,15).sendKeys(startDate);

    }

    private void setArrival(){
        logger.info("Setting arrival date");
        //"2019-01-06"
        libraryUtils.waitForElementToBeVisible(driver,arrival,15).sendKeys(endDate);
    }

    private void setClickOnSearchButton(){
        logger.info("Clicking search button");
        libraryUtils.waitForElementToBeVisible(driver,clickOnSearchButton,15).click();
    }

    public void bookAFlight() throws InterruptedException {
        logger.info("Launching automated booking a flight");
        clickOnFlightsTab();
        setInsertSourceAirPort();
        setInsertDestinationAirPort();
//        driver.findElement(By.xpath("//*[@id=\"flights\"]/form/div[9]/div[2]/div/div/ins")).click();
        clickOnRoundTrip();
        setOpenDropDownMenuAndSelectBusiness();
        setDeparture();
        setArrival();
        setClickOnSearchButton();
        logger.info("Flight was booked successfully!!");
        logger.info("Loading search results...");

    }
}
