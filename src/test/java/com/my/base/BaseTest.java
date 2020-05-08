package com.my.base;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;


public abstract class BaseTest {
    private Logger logger = Logger.getLogger(BaseTest.class);
    private String propFileName = "cmdc.properties";

    protected String curBrowser = "firefox";
    protected WebDriver driver;
    protected WebDriver.Navigation navigation;
    protected String firefoxDriver = "";
    protected String chromeDriver = "";

    protected int waitTime = 10;

    protected Properties prop = null;
    protected HashMap paramters = new HashMap<String, Object>();

    @Before
    public void begin() throws Exception {
        //加载配置文件，注意需要事先将配置文件放到user.home下
        logger.info("Load properties file:" + propFileName);
        loadFromEnvProperties(propFileName);

        //获取浏览器driver路径
        logger.info("Load webdriver");
        firefoxDriver = prop.getProperty("FIREFOX_DRIVER");
        chromeDriver = prop.getProperty("CHROME_DRIVER");
        logger.info("firefoxDriver = " + firefoxDriver);
        logger.info("chromePath = " + chromeDriver);

        //设定当前运行的浏览器
        setCurBrowser();
        logger.info("Current browser is " + curBrowser);

        //构造webdriver
        if (curBrowser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", firefoxDriver);
            driver = new FirefoxDriver();
        } else if (curBrowser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", chromeDriver);
            driver = new ChromeDriver();
        } else if (curBrowser.equalsIgnoreCase("nogui")) {
            System.setProperty("webdriver.chrome.driver", chromeDriver);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);
        } else {
            logger.error("Current browser is illegal");
            return;
        }

        WebDriver.Timeouts timeout = driver.manage().timeouts();
        timeout.setScriptTimeout(waitTime, java.util.concurrent.TimeUnit.SECONDS);
        timeout.pageLoadTimeout(waitTime, java.util.concurrent.TimeUnit.SECONDS);
        timeout.implicitlyWait(waitTime, java.util.concurrent.TimeUnit.SECONDS);
        driver.manage().window().maximize();
        navigation = driver.navigate();

        // 登录
        logger.info("Open the fucking cmdc");
        navigation.to(prop.getProperty("HOST"));
        wait2s();
        BasePage.input(By.id("userId"), prop.getProperty("USERNAME"), driver);
        BasePage.input(By.id("password"), prop.getProperty("PASSWORD"), driver);
        BasePage.click(By.id("submit_btn"), driver);
        logger.info("Login successfully.");
        wait2s();
    }

    @After
    public void tearDown() {
        logger.info("See you next time!");

        if (driver == null) {
            return;
        }

        wait2s();
        driver.quit();
    }

    //加载配置文件
    private void loadFromEnvProperties(String propFileName) {

        String path = System.getProperty("user.home");

        try {
            prop = new Properties();
            InputStream in = new BufferedInputStream(
                    new FileInputStream(path + "\\IdeaProjects\\" + propFileName));
            prop.load(in);
            in.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
            logger.error("Load config file fail, please check " + path + " to confirm if the "
                    + propFileName + " file exist!");
        }

    }

    private void setCurBrowser() {
        String value = System.getenv("currentBrowser");
        if (value == null || value.equalsIgnoreCase("")) {
            return;
        }

        if (value.equalsIgnoreCase("firefox") || value.equalsIgnoreCase("chrome")
                || value.equalsIgnoreCase("nogui")) {
            curBrowser = value.toLowerCase();
        }
    }

    protected void setParamter(String key) {
        String value = System.getenv(key);
        if (value == null || value.equalsIgnoreCase("")) {
            String propValue = prop.getProperty(key);
            if (propValue == null || propValue.equalsIgnoreCase("")) {
                paramters.put(key, "");
                return;
            }
            else
                paramters.put(key, propValue);
            return;
        }
        paramters.put(key, value);
    }

    protected void wait2s() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {

        }
    }
}
