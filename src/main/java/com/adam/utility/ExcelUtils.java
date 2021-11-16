package com.adam.utility;

import com.adam.config.Constants;
import com.adam.executionEngine.DriverScript;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @program : KeywordDriverFramework
 * @ Author      ：Fanyong Kong
 * @ Date        ：Created in 10:39 2021/11/16 2021
 * @ Description ：This is to handle Excel file
 * @Version : 1.0$
 */

public class ExcelUtils {

    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;

    public static void setExcelFile(String Path) throws IOException {
        try{
            FileInputStream ExcelFile = new FileInputStream(Path);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
        }catch (Exception e){
            Log.error("Class Utils | Method setExcelFile | Exception desc : " + e.getMessage());
            DriverScript.bResult = false;
        }
    }

    // 读取Excel文件单元格数据
    // 新增sheetname参数，这样就可以去读取Test Steps和Test Cases两个工作表的单元格数据
    public static String getCellData(int RowNum, int ColNum, String SheetName) {
        try {
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            if("#".equalsIgnoreCase(CellData)){
                CellData = "";
            }
            return CellData;
        } catch (Exception e) {
            Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
            DriverScript.bResult = false;
            e.printStackTrace();
            return "";
        }
    }


    // 得到一共多少行数据
    public static int getRowCount(String SheetName) {
        int iNumber=0;
        try {
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
            iNumber = ExcelWSheet.getLastRowNum() + 1;
        }catch(Exception e){
            Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
            DriverScript.bResult = false;
        }
        return iNumber;
    }


    //得到测试用例的行号
    public static int getRowContains(String sTestCaseName, int colNum, String SheetName) {
        int iRowNum = 0;
        try{
            // ExcelWSheet = ExcelWBook.getSheet(SheetName);
            int rowCount = ExcelUtils.getRowCount(SheetName);
            for (iRowNum = 0; iRowNum < rowCount; iRowNum++) {
                if (ExcelUtils.getCellData(iRowNum, colNum, SheetName).equalsIgnoreCase(sTestCaseName)) {
                    break;
                }
            }
        }catch(Exception e){
            Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
            DriverScript.bResult = false;
        }

        return iRowNum;
    }


    //计算一个测试用例有多少个步骤
    public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart){
        try{
            for (int i = iTestCaseStart; i < ExcelUtils.getRowCount(SheetName); i++) {
                if (!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))) {
                    int number = i;
                    return number + 1;
                }
            }
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
            int number = ExcelWSheet.getLastRowNum() + 1;
            return number;
        }catch(Exception e){
            Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
            DriverScript.bResult = false;
            return 0;
        }
    }



    // 构造一个往单元格写数据的方法，主要是用来写结果pass还是fail
    public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) {
        try{

            ExcelWSheet = ExcelWBook.getSheet(SheetName);
            Row = ExcelWSheet.getRow(RowNum);
            Cell = Row.getCell(ColNum);
            if (Cell == null) {
                Cell = Row.createCell(ColNum);
            }
            Cell.setCellValue(Result);

            // Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
            ExcelWBook.write(fileOut);
            //fileOut.flush();
            fileOut.close();
            ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
        }catch(Exception e){
            DriverScript.bResult = false;
        }
    }

}