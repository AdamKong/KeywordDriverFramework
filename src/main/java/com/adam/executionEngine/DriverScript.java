package com.adam.executionEngine;

import com.adam.config.ActionsKeywords;
import com.adam.config.Constants;
import com.adam.utility.ExcelUtils;
import com.adam.utility.Log;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @program : KeywordDriverFramework
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 9:58 2021/11/16 2021
 * @ Description ：This is a driver engine
 * @Version : 1.0$
 */

public class DriverScript {

    public static ActionsKeywords actionKeywords;
    public static String sActionKeyword;
    public static Method method[];
    public static Properties OR;
    public static String sPageObject;

    public static int iTestStep = 0;
    public static int iTestLastStep = 0;
    public static String sTestCaseID = "";
    public static String sRunMode = "";
    public static Boolean bResult = true;
    public static String sData;


    public DriverScript() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
        Class<?> actionKeywords = Class.forName("com.adam.config.ActionsKeywords");
        method = actionKeywords.getMethods();
    }

    public static void main(String[] args) throws Exception {

        // 这样一定要加，否则报log4j初始化的警告
        DOMConfigurator.configure(System.getProperty("user.dir") + "\\log4j.xml");
        Log.startTestCase("Login_01");
        Log.info("加载和读取Excel数据文件");

        ExcelUtils.setExcelFile(Constants.Path_TestData);

        FileInputStream fs = new FileInputStream(Constants.Path_OR);
        OR = new Properties(System.getProperties());
        OR.load(fs);

        DriverScript startEngine = new DriverScript();
        Log.info("开始执行测试用例。");
        startEngine.execute_TestCase();
        Log.info("测试用例执行结束。");
    }



    private void execute_TestCase() throws Exception {
        //获取测试用例steps数量
        int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
        //外层for循环，有多少个测试用例就执行多少次循环
        for(int iTestcase=1;iTestcase< iTotalTestCases;iTestcase++){

            bResult = true;
            //从Test Case表获取测试ID
            sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
            //获取当前测试用例的Run Mode的值
            sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
            // Run Mode的值控制用例是否被执行
            if (sRunMode.equals("Yes")){
                // 只有当Run Mode的单元格数据是Yes，下面代码才会被执行
                iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
                iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);

                bResult = true;
                //下面这个for循环的次数就等于测试用例的步骤数
                for (;iTestStep < iTestLastStep; iTestStep++){
                    sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
                    sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
                    sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);

                    execute_Actions();

                    if(bResult==false){
                        //If 'false' then store the test case result as Fail
                        ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
                        //End the test case in the logs
                        Log.endTestCase(sTestCaseID);
                        //By this break statement, execution flow will not execute any more test step of the failed test case
                        break;
                    }
                }

                //This will only execute after the last step of the test case, if value is not 'false' at any step
                if(bResult==true){
                    //Storing the result as Pass in the excel sheet
                    ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
                    Log.endTestCase(sTestCaseID);
                }

            }
        }
    }

    private static void execute_Actions() throws Exception {
        for(int i = 0;i < method.length;i++){
            if(method[i].getName().equals(sActionKeyword)){
                method[i].invoke(actionKeywords, sPageObject, sData);
                //This code block will execute after every test step
                if(bResult==true){
                    //If the executed test step value is true, Pass the test step in Excel sheet
                    ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
                    break;
                }else{
                    //If the executed test step value is false, Fail the test step in Excel sheet
                    ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
                    //In case of false, the test execution will not reach to last step of closing browser
                    //So it makes sense to close the browser before moving on to next test case
                    ActionsKeywords.closeBrowser("","");
                    break;
                }

            }
        }
    }



}








































