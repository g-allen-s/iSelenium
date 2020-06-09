package com.my.webui;

import com.my.base.BasePage;
import com.my.base.BaseTest;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;


public class TestFuck extends BaseTest {
    private Logger logger = Logger.getLogger(TestFuck.class);
    
    @Test
    public void fuckGo() throws Exception{
        goFuck();
    }

    public void goFuck() throws Exception {
        logger.info("Go to fuck it up.");
        // 打开浏览器
        String url = prop.getProperty("FUCK_URL");
        navigation.to(url);
        wait2s();
        wait2s();
        wait2s();
        List<WebElement> elements = BasePage.findElementsByClass("ParagraphDiv__text___n6KdI", driver);
        elements.get(0).click();
        int i = 0;
        for (WebElement element : elements) {
            doEach(element);
            i ++;
            wait(200);
        }
        List<WebElement> post_elements = BasePage.findElementsByClass("ParagraphDiv__text___n6KdI", driver);
        int j = 0;
        for (WebElement element : post_elements) {
            if (j >= i) {
                doEach(element);
            }
            j++;
			wait(200);
        }
    }

    private void doEach(WebElement element) throws Exception {
        String fullText = element.getText();
        String[] texts = fullText.split("？    A");
        String summary = texts[0].trim();
        String selections = texts[1];
        String aSelection = selections.split("B")[0].trim();
        String xSelection = selections.split("B")[1].trim();
        String bSelection = xSelection.split("C")[0].trim();

        if (summary.contains(aSelection) && summary.contains(bSelection)){
            BasePage.click(By.name("C"), driver);
            System.out.println("choose [C]");
        } else if (summary.contains(aSelection)) {
            BasePage.click(By.name("A"), driver);
            System.out.println("choose [A]");
        } else if (summary.contains(bSelection)) {
            BasePage.click(By.name("B"), driver);
            System.out.println("choose [B]");
        } else {
            BasePage.click(By.name("D"), driver);
            System.out.println("choose [D]");
        }
    }
}
