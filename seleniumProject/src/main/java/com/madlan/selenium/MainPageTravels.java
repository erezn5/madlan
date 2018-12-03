package com.madlan.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPageTravels {

    WebDriver driver;
    private LibraryUtils libraryUtils = new LibraryUtils();
    private final Logger logger = LoggerFactory.getLogger(MainPageTravels.class);
    @FindBy(how= How.XPATH, xpath = "(.//*[normalize-space(text()) and normalize-space(.)='USD'])[3]/following::a[11]")
    WebElement clickingOnLangVisibleDropLangMenu;
    @FindBy(how = How.XPATH, xpath = "(.//*[normalize-space(text()) and normalize-space(.)='Spanish'])[2]/img[1]")
    WebElement selectingLanguage;

    public MainPageTravels(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private void setClickingOnLangVisibleDropLangMenu(){
        clickingOnLangVisibleDropLangMenu.click();
    }

    private void setSelectingLanguage(){
        libraryUtils.waitForElementToBeVisible(driver,selectingLanguage,10).click();
    }

    public void changeLanguage(){
        logger.info("Changing language");
        setClickingOnLangVisibleDropLangMenu();
        setSelectingLanguage();
    }


}
