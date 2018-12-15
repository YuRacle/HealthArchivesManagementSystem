package util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.Student;
import pojo.StudentHealthCheckup;
import pojo.StudentMedicalRecord;
import service.DataService;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 操作excel
 */
public class ExcelOperation {

    public static void main(String[] args) {
    }

    public String exportExcel(Object[][] objects, int type) {
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("数据");

        if (type == 1) {
            //在sheet里创建第一行（标题），参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row0 = sheet.createRow(0);
            //创建单元格并设置单元格内容（excel的单元格，参数为列索引，可以是0～255之间的任何一个)
            row0.createCell(0).setCellValue("日期");
            row0.createCell(1).setCellValue("学号");
            row0.createCell(2).setCellValue("姓名");
            row0.createCell(3).setCellValue("性别");
            row0.createCell(4).setCellValue("系别");
            row0.createCell(5).setCellValue("诊断");

            //在sheet里创建内容
            int rowCount = objects.length;
            for (int i = 1; i <= rowCount; i++) {
                HSSFRow rowi = sheet.createRow(i);
                rowi.createCell(0).setCellValue(objects[i-1][0].toString());
                rowi.createCell(1).setCellValue((String) objects[i-1][1]);
                rowi.createCell(2).setCellValue((String) objects[i-1][2]);
                rowi.createCell(3).setCellValue((String) objects[i-1][3]);
                rowi.createCell(4).setCellValue((String) objects[i-1][4]);
                rowi.createCell(5).setCellValue((String) objects[i-1][5]);
            }

        } else if (type == 2) {
            //在sheet里创建第一行（标题），参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row0 = sheet.createRow(0);
            //创建单元格并设置单元格内容（excel的单元格，参数为列索引，可以是0～255之间的任何一个)
            row0.createCell(0).setCellValue("日期");
            row0.createCell(1).setCellValue("学号");
            row0.createCell(2).setCellValue("姓名");
            row0.createCell(3).setCellValue("性别");
            row0.createCell(4).setCellValue("系别");
            row0.createCell(5).setCellValue("年龄");
            row0.createCell(6).setCellValue("身高");
            row0.createCell(7).setCellValue("体重");
            row0.createCell(8).setCellValue("胸围");

            //在sheet里创建内容
            int rowCount = objects.length;
            for (int i = 1; i <= rowCount; i++) {
                HSSFRow rowi = sheet.createRow(i);
                rowi.createCell(0).setCellValue(objects[i-1][0].toString());
                rowi.createCell(1).setCellValue((String) objects[i-1][1]);
                rowi.createCell(2).setCellValue((String) objects[i-1][2]);
                rowi.createCell(3).setCellValue((String) objects[i-1][3]);
                rowi.createCell(4).setCellValue((String) objects[i-1][4]);
                rowi.createCell(5).setCellValue(objects[i-1][5].toString());
                rowi.createCell(6).setCellValue(objects[i-1][6].toString());
                rowi.createCell(7).setCellValue(objects[i-1][7].toString());
                rowi.createCell(8).setCellValue(objects[i-1][8].toString());
            }
        }

        //输出Excel文件
        FileOutputStream outputStream = null;
        File file = null;
        try {
            file = new File("data.xls");
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        return file.getAbsolutePath();
    }

    public void importExcel(int type) throws Exception {

        FileChooser fileChooser = new FileChooser();
        String filePath = fileChooser.getFilePath();
        String fileName = fileChooser.getFileName();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("导入失败：文件不存在！");
        }

        InputStream is = new FileInputStream(file);
        Workbook hssfWorkbook = null;
        if (fileName.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (fileName.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003

        }
        if (type == 1) {
            // 循环工作表Sheet
            Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
            if (hssfSheet == null) {
                return;
            }

            // 循环行Row
            int rowCount = hssfSheet.getLastRowNum();
            //添加数据到数据库
            for (int i = 1; i <= rowCount; i++) {
                Row hssfRow = hssfSheet.getRow(i);
                if (hssfRow != null) {
                    String s = hssfRow.getCell(0).toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = sdf.parse(s);
                    //日期 日+1
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    date = c.getTime();
                    Date recordDate = new Date(date.getTime());
                    String sid = String.valueOf(hssfRow.getCell(1));
                    String name = String.valueOf(hssfRow.getCell(2));
                    String sex = String.valueOf(hssfRow.getCell(3));
                    String dept = String.valueOf(hssfRow.getCell(4));
                    String diagnosis = String.valueOf(hssfRow.getCell(5));

                    Student student = new Student(sid,name,sex,dept);
                    StudentMedicalRecord smr = new StudentMedicalRecord(sid,diagnosis,recordDate);
                    new DataService().addMedicalData(student,smr);
                }
            }

        } else if (type == 2) {
            // 循环工作表Sheet
            Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
            if (hssfSheet == null) {
                return;
            }
            // 循环行Row
            int rowCount = hssfSheet.getLastRowNum();
            //添加数据到数据库
            for (int i = 1; i <= rowCount; i++) {
                Row hssfRow = hssfSheet.getRow(i);
                if (hssfRow != null) {
                    //util.date转sql.date
                    String s = hssfRow.getCell(0).toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = sdf.parse(s);
                    //日期 日+1
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    date = c.getTime();
                    Date checkupDate = new Date(date.getTime());
                    String sid = String.valueOf(hssfRow.getCell(1));
                    String name = String.valueOf(hssfRow.getCell(2));
                    String sex = String.valueOf(hssfRow.getCell(3));
                    String dept = String.valueOf(hssfRow.getCell(4));
                    int age = Integer.parseInt(hssfRow.getCell(5).toString());
                    int height = Integer.parseInt(hssfRow.getCell(6).toString());
                    int weight = Integer.parseInt(hssfRow.getCell(7).toString());
                    int bust = Integer.parseInt(hssfRow.getCell(8).toString());

                    Student student = new Student(sid,name,sex,dept);
                    StudentHealthCheckup shc = new StudentHealthCheckup(sid,age,height,weight,bust,checkupDate);
                    new DataService().addCheckupData(student,shc);
                }
            }
        }
    }


}
