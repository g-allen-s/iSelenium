package com.my.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class WebUITasks {
    private static int waitShot = 30;
    private static Logger logger = Logger.getLogger(WebUITasks.class);

    public static void input(By by, String txt, WebDriver driver) throws Exception{
        WebElement ele = driver.findElement(by);
        ele.sendKeys(txt);
        logger.info("input text: " + txt);
    }

    public static void click(By by, WebDriver driver) throws Exception{
        WebElement ele = driver.findElement(by);
        ele.click();
        logger.info("click: " + by.toString());
    }

    public static void clickTheElement(By by, int index, WebDriver driver) throws Exception{
        List<WebElement> eles = driver.findElements(by);
        WebElement ele = eles.get(index);
        ele.click();
        logger.info("click: " + ele.toString());
    }

    public static void clickTheElement(By by, String txt, WebDriver driver) throws Exception{
        List<WebElement> eles = driver.findElements(by);
        for (int i = 0; i < eles.size(); i++) {
            WebElement e = eles.get(i);
            if (e.getText().equals(txt))
                e.click();
            logger.info("click: " + e.toString());
        }
    }

    public static void inputText(String txt, WebDriver driver) throws Exception{
        WebElement searchInput = findElementByXpath("//input[@name='wd' and @id='kw']",driver);
        searchInput.sendKeys(txt);
        logger.info("Search text: " + txt);
    }

    public static void clickSearchBtn(WebDriver driver) throws Exception{
        WebElement searchBtn = findElementByXpath("//input[@type='submit' and @id='su']",driver);
        searchBtn.click();
        logger.info("Click the search button.");
    }

    private static WebElement findElementByXpath(String objXpath, WebDriver driver) throws Exception{
        WebElement wele = null;
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while(((now - start) < waitShot * 1000) && (wele == null)){
            wait(1);
            wele = driver.findElement(By.xpath(objXpath));
            now = System.currentTimeMillis();
        }

        if(wele == null){
            throw new Exception("Can not find out the element: " + objXpath);
        }

        return wele;
    }

    protected static void wait(int sec){
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {

        }
    }
}
