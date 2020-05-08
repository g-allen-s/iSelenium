package com.my.webui;

import com.my.base.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;


public class RunCmdc extends BaseTest {
    private Logger logger = Logger.getLogger(RunCmdc.class);

    private static int testByTE = 0;
    private static int testByPD = 1;


    @Test
    public void cmdcGo() throws Exception{
        setParamter("cmdcCode");
        setParamter("testBy");
        String cmdcCodes = String.valueOf(paramters.get("cmdcCode"));
        if (cmdcCodes == null || cmdcCodes.equalsIgnoreCase("")) return;
        String[] codes = cmdcCodes.split(",");

        String testBys = String.valueOf(paramters.get("testBy"));
        if (testBys == null || testBys.equalsIgnoreCase("")) return;
        String[] bys = testBys.split(",");

        for(int i = 0; i < codes.length; i++) {
            goCmdc(codes[i], Integer.parseInt(bys[i]));
        }
    }

    public void goCmdc(String cmdcCode, int testBy) throws Exception {
        logger.info("Go to " + cmdcCode + " and fuck it up.");
        // 打开浏览器
        String url = prop.getProperty("CMDC_URL") + "=" + cmdcCode;
        navigation.to(url);
        wait2s();
        // 【测试确认】
        BasePage.click(By.className("popTeConf"), driver);
        // 测试环境
        BasePage.clickTheElement(By.className("default-val"), 11, driver);
        BasePage.clickTheElement(By.tagName("span"), "sit/pre", driver);
        // 压测环境
        BasePage.clickTheElement(By.className("default-val"), 12, driver);
        BasePage.clickTheElement(By.tagName("span"), "无压测", driver);
        if (testBy == testByTE) {
            // do nothing
        }
        if (testBy == testByPD) {
            // 是否产品自测
            BasePage.clickTheElement(By.className("default-val"), 13, driver);
            BasePage.clickTheElement(By.tagName("span"), "是", driver);
            // 产品自测原因
            BasePage.clickTheElement(By.className("default-val"), 14, driver);
            BasePage.clickTheElement(By.tagName("span"), "其它", driver);
        }
        // 【确定】
//        WebUITasks.click(By.className("popWinBtnYes"), driver);
        wait2s();
        logger.info("[[" + cmdcCode + "]] Mission complicated.");
    }
}
