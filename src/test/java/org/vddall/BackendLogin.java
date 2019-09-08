package org.vddall;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import net.sourceforge.tess4j.*;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;

public class BackendLogin {


    @Test
    public void readVerifyCode(){
        String imagePath = "images/verfycode.png";
        File verfyCodeFile = new File(imagePath);

        ITesseract iTesseract = new Tesseract();
        iTesseract.setDatapath("tessdata");

        try{
           String verfyCode =  iTesseract.doOCR(verfyCodeFile);

           String expectedCode = "yjjaa";
           assertEquals(expectedCode,verfyCode);
        }
        catch (TesseractException e){
            System.err.println(e.getMessage());
        }

    }

    @Test
    public void inputHTMLVeryCode(){

        System.setProperty("webdriver.chrome.driver","Drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        String loginURL = "https://www.html-js.cn/dr-admin";
        driver.get(loginURL);

        Thread.sleep(5000);
        File srcImage = driver.findElement(By.className("imageCode-img")).getScreenshotAs(OutputType.FILE);
        String imagePath = System.getProperty("user.dir")+"screenshots/verfycode.png";

        FileHandler.copy(srcImage,new File(imagePath));

        ITesseract iTesseract = new Tesseract();
        iTesseract.setDatapath("tessdata");

        try{
            String verfyCode =  iTesseract.doOCR(imagePath);

            driver.findElement(By.name("imageCode")).sendKeys(verfyCode);
            assertEquals(expectedCode,verfyCode);
        }
        catch (TesseractException e){
            System.err.println(e.getMessage());
        }


    }

}
