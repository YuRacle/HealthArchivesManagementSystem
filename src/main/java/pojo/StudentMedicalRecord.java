package pojo;

import java.sql.Date;

/**
 * 学生病历数据对象
 */
public class StudentMedicalRecord{

    private String sid;//学生学号
    private String diagnosis;//诊断结果
    private Date recordDate;//诊断日期

    public StudentMedicalRecord() {
    }

    public StudentMedicalRecord(String sid, String diagnosis, Date recordDate) {
        this.sid = sid;
        this.diagnosis = diagnosis;
        this.recordDate = recordDate;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getSid() {
        return sid;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Date getRecordDate() {
        return recordDate;
    }
}
