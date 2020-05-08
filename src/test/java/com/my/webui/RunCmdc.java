package com.my.webui;

import com.my.base.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;


public class RunCmdc extends WebUIBase {
    private Logger logger = Logger.getLogger(RunCmdc.class);

    private static int testByTE = 0;
    private static int testByPD = 1;


    @Test
    public void cmdcGo() throws Exception{
        logger.info("Go to cmdc and fuck it up.");
        setParamter("cmdcCode");
        setParamter("testBy");
        goCmdc(String.valueOf(paramters.get("cmdcCode")),
                Integer.parseInt(String.valueOf(paramters.get("testBy"))));
    }

    public void goCmdc(String cmdcCode, int testBy) throws Exception {
        // 打开浏览器
        String url = "http://cmdc.cnsuning.com/cmdc/demandManage/demandInfo.action?brCode=" + cmdcCode;
        logger.info("Open the fucking cmdc");
        navigation.to(url);
        wait2s();
        // 登录
        WebUITasks.input(By.id("userId"), "12010077", driver);
        WebUITasks.input(By.id("password"), "Sn@197777", driver);
        WebUITasks.click(By.id("submit_btn"), driver);
        logger.info("Login successfully.");
        wait2s();
        // 【测试确认】
        WebUITasks.click(By.className("popTeConf"), driver);
        // 测试环境
        WebUITasks.clickTheElement(By.className("default-val"), 11, driver);
        WebUITasks.clickTheElement(By.tagName("span"), "sit/pre", driver);
        // 压测环境
        WebUITasks.clickTheElement(By.className("default-val"), 12, driver);
        WebUITasks.clickTheElement(By.tagName("span"), "无压测", driver);
        if (testBy == testByTE) {
            // do nothing
        }
        if (testBy == testByPD) {
            // 是否产品自测
            WebUITasks.clickTheElement(By.className("default-val"), 13, driver);
            WebUITasks.clickTheElement(By.tagName("span"), "是", driver);
            // 产品自测原因
            WebUITasks.clickTheElement(By.className("default-val"), 14, driver);
            WebUITasks.clickTheElement(By.tagName("span"), "其它", driver);
        }
        // 【确定】
        WebUITasks.click(By.className("popWinBtnYes"), driver);
        wait2s();
        logger.info("[[" + cmdcCode + "]] Mission complicated.");
    }
}