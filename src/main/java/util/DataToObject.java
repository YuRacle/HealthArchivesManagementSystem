package util;

import dao.HealthCheckupDao;
import dao.MedicalRecordDao;
import dao.StudentDao;
import pojo.Student;
import pojo.StudentHealthCheckup;
import pojo.StudentMedicalRecord;

import java.util.ArrayList;

public class DataToObject {

    private static StudentDao studentDao = new StudentDao();
    private static MedicalRecordDao medicalRecordDao = new MedicalRecordDao();
    private static HealthCheckupDao healthCheckupDao = new HealthCheckupDao();


    /**
     * 将病历数据集合转化成2维数组对象
     * @param medicalRecords
     * @return
     */
    public static Object[][] MedicalDataToObject(ArrayList<StudentMedicalRecord> medicalRecords) {
        Object[][] medicalDataObject = new Object[medicalRecords.size()][6];
        Student student = null;

        int rowCount = 0;//行
        int columnCount = 0;//列
        for (StudentMedicalRecord smr : medicalRecords) {
            student = studentDao.findStuBySid(smr.getSid());
            if (student != null) {
                columnCount = 0;
                //日期
                medicalDataObject[rowCount][columnCount++] = smr.getRecordDate();
                //学号
                medicalDataObject[rowCount][columnCount++] = student.getSid();
                //姓名
                medicalDataObject[rowCount][columnCount++] = student.getName();
                //性别
                medicalDataObject[rowCount][columnCount++] = student.getSex();
                //系别
                medicalDataObject[rowCount][columnCount++] = student.getDept();
                //诊断
                medicalDataObject[rowCount][columnCount++] = smr.getDiagnosis();

                rowCount++;
            }
        }
        return medicalDataObject;
    }

    /**
     * 将体检数据集合转化成2维数组对象
     * @param checkupRecords
     * @return
     */
    public static Object[][] CheckupDataToObject(ArrayList<StudentHealthCheckup> checkupRecords) {
        Object[][] checkupDataObject = new Object[checkupRecords.size()][9];
        Student student = null;

        int rowCount = 0;//行
        int columnCount = 0;//列
        for (StudentHealthCheckup shc : checkupRecords) {
            student = studentDao.findStuBySid(shc.getSid());
            if (student != null) {
                columnCount = 0;
                //日期
                checkupDataObject[rowCount][columnCount++] = shc.getCheckupDate();
                //学号
                checkupDataObject[rowCount][columnCount++] = student.getSid();
                //姓名
                checkupDataObject[rowCount][columnCount++] = student.getName();
                //性别
                checkupDataObject[rowCount][columnCount++] = student.getSex();
                //系别
                checkupDataObject[rowCount][columnCount++] = student.getDept();
                //年龄
                checkupDataObject[rowCount][columnCount++] = shc.getAge();
                //身高
                checkupDataObject[rowCount][columnCount++] = shc.getHeight();
                //体重
                checkupDataObject[rowCount][columnCount++] = shc.getWeight();
                //胸围
                checkupDataObject[rowCount][columnCount++] = shc.getBust();

                rowCount++;
            }
        }
        return checkupDataObject;
    }

    /**
     * 将学生数据集合转化成2维数组对象
     * @param students
     * @return
     */
    public static Object[][] studentsDataToObject(ArrayList<Student> students) {
        Object[][] studentsObject = new Object[students.size()][4];
        Student student = null;

        int rowCount = 0;//行
        int columnCount = 0;//列
        for (Student s : students) {
            student = studentDao.findStuBySid(s.getSid());
            if (student != null) {
                columnCount = 0;
                //学号
                studentsObject[rowCount][columnCount++] = student.getSid();
                //系别
                studentsObject[rowCount][columnCount++] = student.getDept();
                //姓名
                studentsObject[rowCount][columnCount++] = student.getName();
                //性别
                studentsObject[rowCount][columnCount++] = student.getSex();
                rowCount++;
            }
        }
        return studentsObject;
    }
}
