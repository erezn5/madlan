package com.madlan.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrangehrmliveLogin {
    private final Logger logger = LoggerFactory.getLogger(OrangehrmliveLogin.class);
    @FindBy(how= How.XPATH,xpath = "(.//*[normalize-space(text()) and normalize-space(.)='LOGIN Panel'])[1]/preceding::span[1]")
    WebElement data;
    @FindBy(how=How.ID, id = "btnLogin" )
    WebElement btnLogin;
    @FindBy(how=How.ID, id = "txtUsername")
     WebElement userName;
    @FindBy(how=How.ID,id = "txtPassword")
            WebElement password;
    WebDriver driver;
    String[] arr = new String[2];

    public OrangehrmliveLogin(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public String[] gettingUserNameAndPassword() {
        logger.info("Parsing data string to 'username' and 'password'");
        String str = data.getText();
        String userName = str.split("\\|")[0].substring(13).trim();
        String passwrod = str.split("\\|")[1].substring(11).replace(")","").trim();
        arr[0]=userName;
        arr[1]=passwrod;
        if(arr.length!=0){
            logger.info("Parsing username and password was successful!");
        }
        return arr;
    }

    private void setUserName(String stringUserName){
        logger.info("Sending username");
        userName.sendKeys(stringUserName);
    }

    private void setPassword(String StringPassword){
        logger.info("Sending password");
        password.sendKeys(StringPassword);
    }

    public void clickLogin(){
        logger.info("Starting the login process...");
        gettingUserNameAndPassword();
        setUserName(arr[0]);
        setPassword(arr[1]);
        btnLogin.click();
    }
}
