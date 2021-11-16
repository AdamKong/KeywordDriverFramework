package com.adam.config;

/**
 * @program : KeywordDriverFramework
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 11:14 2021/11/16 2021
 * @ Description ：This is to save the constant variables
 * @Version : 1.0$
 */

public class Constants {

    // url and excel location
    public static final String URL = "https://www.baidu.com";
    public static final String Path_TestData = System.getProperty("user.dir") + "\\dataEngine\\dataEngine.xlsx";
    public static final String File_TestData = "dataEngine.xlsx";
    // Object Repository location
    public static final String Path_OR = System.getProperty("user.dir") + "\\objects\\OR.txt";


    // driver location
    public static final String chromeExe = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
    public static final String chromeWebDriver = "D:\\111\\browserdrivers\\chromedriver.exe";

    // indexes in dataEngine.xlsx
    public static final int Col_TestCaseID = 0;
    public static final int Col_TestScenarioID = 1 ;
    public static final int Col_PageObject = 3 ;
    public static final int Col_ActionKeyword = 4 ;
    public static final int Col_RunMode = 2; // in the second sheet "Test Cases"
    public static final int Col_Result = 3 ;
    public static final int Col_DataSet = 5;
    public static final int Col_TestStepResult = 6;

    // test result status
    public static final String KEYWORD_FAIL = "FAIL";
    public static final String KEYWORD_PASS = "PASS";

    // Sheet name in DataEngmine.xlsx
    public static final String Sheet_TestSteps = "Test Steps";
    public static final String Sheet_TestCases = "Test Cases";

    // username and password
//    public static final String UserName = "xxx";
//    public static final String Password = "xxx";
}































