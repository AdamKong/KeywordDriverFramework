package com.adam.config;

import com.adam.executionEngine.DriverScript;
import com.adam.utility.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static com.adam.executionEngine.DriverScript.OR;

/**
 * @program : KeywordDriverFramework
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 10:07 2021/11/16 2021
 * @ Description ：This is for keyword actions
 * @Version : 1.0$
 */

public class ActionsKeywords {

    public static WebDriver driver;

    public static void openBrowser(String object, String data) {

        try{
            Log.info("启动chrome浏览器。");
            System.setProperty("webdriver.chrome.driver", Constants.chromeWebDriver);
            ChromeOptions option = new ChromeOptions();
            option.setBinary(Constants.chromeExe);
            driver = new ChromeDriver(option);
        }catch(Exception e){
            Log.info("无法启动浏览器 --- " + e.getMessage());
            DriverScript.bResult = false;
        }



    }

    public static void openUrl(String object, String data) {

        try{
            Log.info("浏览器窗口最大化。");
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            Log.info("打开测试服务器地址。");
            driver.get(Constants.URL);
        }catch(Exception e){
            Log.info("无法打开测试环境地址 --- " + e.getMessage());
            DriverScript.bResult = false;
        }

    }

    public static void click(String object, String data) {

        try{
            Log.info("点击元素：" + object);
            driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch (Exception e){
            Log.error("无法点击元素--- " + e.getMessage());
            DriverScript.bResult = false;
        }
    }



//    public static void inputUsername(String object){
//        try{
//            Log.info("在用户名输入框输入文字");
//            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.UserName);
//        }catch(Exception e){
//            Log.error("无法输入用户名 --- " + e.getMessage());
//            DriverScript.bResult = false;
//        }
//    }
//
//    public static void inputPassword(String object) {
//        try {
//            Log.info("密码框输入...");
//            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.Password);
//        } catch (Exception e) {
//            Log.error("密码框无法输入--- " + e.getMessage());
//            DriverScript.bResult = false;
//        }
//
//    }



    public static void input(String object, String data){
        try{
            Log.info("开始在 " + object +"输入文本");
            driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
        }catch(Exception e){
            Log.error("无法输入文本 --- " + e.getMessage());
            DriverScript.bResult = false;
        }

    }


    public static void waitFor(String object, String data){
        try{
            Log.info("等待五秒");
            Thread.sleep(5000);
        }catch(Exception e){
            Log.error("无法等待 --- " + e.getMessage());
            DriverScript.bResult = false;
        }
    }



    public static void closeBrowser(String object, String data){
        waitFor("",""); // wait for 5s so that we can observe the page before closing.
        try{
            Log.info("关闭并退出浏览器");
            driver.quit();
        }catch(Exception e){
            Log.error("无法关闭浏览器--- " + e.getMessage());
            DriverScript.bResult = false;
        }
    }


}



























