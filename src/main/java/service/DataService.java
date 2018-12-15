package service;

import dao.HealthCheckupDao;
import dao.MedicalRecordDao;
import dao.StudentDao;
import pojo.Student;
import pojo.StudentHealthCheckup;
import pojo.StudentMedicalRecord;
import util.DataToObject;

import java.sql.Date;
import java.util.ArrayList;

public class DataService {

    private StudentDao studentDao = new StudentDao();
    private MedicalRecordDao medicalRecordDao = new MedicalRecordDao();
    private HealthCheckupDao healthCheckupDao = new HealthCheckupDao();

    /**
     * 返回所有病历数据
     *
     * @return
     */
    public Object[][] getAllMedicalData() {
        ArrayList<StudentMedicalRecord> allRecords = medicalRecordDao.getAllRecords();
        Object[][] medicalDataObject = DataToObject.MedicalDataToObject(allRecords);
        return medicalDataObject;
    }

    /**
     * 返回所有体检数据
     *
     * @return
     */
    public Object[][] getAllCheckupData() {
        ArrayList<StudentHealthCheckup> allCheckups = healthCheckupDao.getAllCheckups();
        Object[][] checkupDataObject = DataToObject.CheckupDataToObject(allCheckups);
        return checkupDataObject;
    }

    /**
     * 根据学号查询病历信息
     *
     * @param sid 学生学号
     * @return 包含该学生所有病历信息的二维数组对象
     */
    public Object[][] findMedicalDataBySid(String sid) {

        ArrayList<StudentMedicalRecord> medicalRecords = medicalRecordDao.findRecordsBySid(sid);

        Object[][] medicalDataObject = DataToObject.MedicalDataToObject(medicalRecords);

        return medicalDataObject;
    }

    /**
     * 根据学号查询体检信息
     *
     * @param sid 学生学号
     * @return 包含该学生所有体检信息的二维数组对象
     */
    public Object[][] findCheckupDataBySid(String sid) {

        ArrayList<StudentHealthCheckup> checkupRecords = healthCheckupDao.findCheckupsBySid(sid);

        Object[][] checkupDataObject = DataToObject.CheckupDataToObject(checkupRecords);

        return checkupDataObject;
    }

    /**
     * 添加病历信息
     *
     * @param student 学生
     * @param smr     病历数据
     */
    public void addMedicalData(Student student, StudentMedicalRecord smr) {
        studentDao.addStu(student);
        medicalRecordDao.addMedicalData(smr.getSid(), smr);
        System.out.println("添加成功");
    }

    /**
     * 添加体检信息
     *
     * @param student 学生
     * @param shc     体检数据
     */
    public void addCheckupData(Student student, StudentHealthCheckup shc) {
        studentDao.addStu(student);
        healthCheckupDao.addCheckupData(shc.getSid(), shc);
        System.out.println("添加成功");
    }

    /**
     * 修改多条，根据oldsid更改（学生信息 + 病历信息）
     *
     * @param oldsidList 旧学号列表
     * @param smrList    新病历数据列表
     */
    public void updateMedicalData(ArrayList<String> oldsidList, ArrayList<Date> dates, ArrayList<StudentMedicalRecord> smrList) {
        for (int i = 0; i < smrList.size(); i++) {
            medicalRecordDao.updateMsgBySid(oldsidList.get(i), dates.get(i),smrList.get(i));
        }
    }

    /**
     * 修改多条，根据oldsid更改（学生信息 + 体检信息）
     *
     * @param oldsidList 旧学号列表
     * @param shcList    新体检数据列表
     */
    public void updateCheckupData(ArrayList<String> oldsidList, ArrayList<Date> dates, ArrayList<StudentHealthCheckup> shcList) {
        for (int i = 0; i < shcList.size(); i++) {
            healthCheckupDao.updateMsgBySid(oldsidList.get(i), dates.get(i), shcList.get(i));
        }
    }

    /**
     * 删除多条病历信息(学号+日期)索引
     * @param dateList
     */
    public void deleteMedicalData(ArrayList<String> sidList, ArrayList<Date> dateList) {
        for (int i = 0; i < sidList.size(); i++) {
            medicalRecordDao.deleteMsg(sidList.get(i), dateList.get(i));
        }
    }

    /**
     * 删除多条体检信息(学号+日期)索引
     * @param dateList
     */
    public void deleteCheckupData(ArrayList<String> sidList, ArrayList<Date> dateList) {
        for (int i = 0; i < sidList.size(); i++) {
            healthCheckupDao.deleteMsg(sidList.get(i), dateList.get(i));
        }
    }


}
