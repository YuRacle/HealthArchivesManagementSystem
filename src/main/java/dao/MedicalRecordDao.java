package dao;

import com.sun.rowset.CachedRowSetImpl;
import pojo.StudentMedicalRecord;
import util.JDBCUtils;

import java.sql.Date;
import java.util.ArrayList;

public class MedicalRecordDao {

    private ArrayList<StudentMedicalRecord> medicalRecords = new ArrayList<StudentMedicalRecord>();


    /**
     * 添加学生病历信息
     *
     * @return 1:成功 0:失败
     */
    public int addMedicalData(String sid, StudentMedicalRecord smr) {
        String diagnosis = smr.getDiagnosis();
        Date recordDate = smr.getRecordDate();
        String sql = "insert into medical_record values(?,?,?,?)";
        try {
            JDBCUtils.execSQL(sql, null, sid, diagnosis, recordDate);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据sid修改病历信息
     *
     * @param oldSid 旧学生学号
     * @param smr    新病历信息
     * @return 1: 成功 0:失败
     */
    public int updateMsgBySid(String oldSid, Date date,StudentMedicalRecord smr) {

        String sid = smr.getSid();
        String diagnosis = smr.getDiagnosis();
        Date recordDate = smr.getRecordDate();

        String sql = "update medical_record set sid=?,diagnosis=?,record_date=? where sid=? and record_date = ?";
        try {
            JDBCUtils.execSQL(sql, sid, diagnosis, recordDate, oldSid,date);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据日期删除病历信息
     *
     * @param date 信息日期
     * @return 1:成功 0:失败
     */
    public int deleteMsg(String sid, Date date) {
        String sql = "delete from medical_record  where sid = ? and record_date = ?";
        try {
            JDBCUtils.execSQL(sql, sid, date);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据学生学号查找病历记录
     *
     * @param sid 学生学号
     * @return 病历数据对象集合
     */
    public ArrayList<StudentMedicalRecord> findRecordsBySid(String sid) {
        String sql = "select * from medical_record where sid = ?";

        return getMedicalRecords(sql, sid);
    }

    /**
     * 获取所有病历记录
     *
     * @return 所有病历数据对象集合
     */
    public ArrayList<StudentMedicalRecord> getAllRecords() {
        String sql = "select * from medical_record";

        return getMedicalRecords(sql);
    }

    /**
     * 获取多条数据结果集
     *
     * @param sql
     * @return
     */
    private ArrayList<StudentMedicalRecord> getMedicalRecords(String sql, String... args) {
        try {
            CachedRowSetImpl rs = JDBCUtils.execSQL(sql, args);
            if (rs != null) {
                while (rs.next()) {
                    StudentMedicalRecord smr = new StudentMedicalRecord();
                    smr.setSid(rs.getString("sid"));
                    smr.setDiagnosis(rs.getString("diagnosis"));
                    smr.setRecordDate(rs.getDate("record_date"));
                    medicalRecords.add(smr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalRecords;
    }
}
