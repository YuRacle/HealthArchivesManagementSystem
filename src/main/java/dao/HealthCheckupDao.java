package dao;

import com.sun.rowset.CachedRowSetImpl;
import pojo.StudentHealthCheckup;
import util.JDBCUtils;

import java.sql.Date;
import java.util.ArrayList;

public class HealthCheckupDao {

    private ArrayList<StudentHealthCheckup> healthCheckups = new ArrayList<StudentHealthCheckup>();

    /**
     * 添加学生体检信息
     *
     * @return 1:成功 0:失败
     */
    public int addCheckupData(String sid, StudentHealthCheckup shc) {
        int height = shc.getHeight();
        int weight = shc.getWeight();
        int bust = shc.getBust();
        int age = shc.getAge();
        Date checkupDate = shc.getCheckupDate();

        String sql = "insert into health_checkup values(null,?,?,?,?,?,?)";
        try {
            JDBCUtils.execSQL(sql, sid, height, weight, bust, age, checkupDate);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据sid修改体检信息
     *
     * @param oldSid 旧学生学号
     * @param shc    新体检信息
     * @return 1: 成功 0:失败
     */
    public int updateMsgBySid(String oldSid, Date date, StudentHealthCheckup shc) {

        String sid = shc.getSid();
        int height = shc.getHeight();//身高
        int weight = shc.getWeight();//体重
        int bust = shc.getBust();//胸围
        int age = shc.getAge();//年龄
        Date checkupDate = shc.getCheckupDate();//体检日期

        String sql = "update health_checkup set sid=?,height=?,weight=?,bust=?,age=?,checkup_date=? where sid=? and checkup_date=?";
        try {
            JDBCUtils.execSQL(sql, sid, height, weight, bust, age, checkupDate, oldSid, date);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据学生学号删除体检信息
     *
     * @param date 信息日期
     * @return 1:成功 0:失败
     */
    public int deleteMsg(String sid, Date date) {
        String sql = "delete from health_checkup where sid = ? and checkup_date = ?";
        try {
            JDBCUtils.execSQL(sql, sid, date);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据学生学号查找体检记录
     *
     * @param sid 学生学号
     * @return 体检数据对象集合
     */
    public ArrayList<StudentHealthCheckup> findCheckupsBySid(String sid) {
        String sql = "select * from health_checkup where sid = ?";

        return getHealthCheckups(sql, sid);
    }

    /**
     * 获取所有体检记录
     *
     * @return 所有体检数据对象集合
     */
    public ArrayList<StudentHealthCheckup> getAllCheckups() {
        String sql = "select * from health_checkup";

        return getHealthCheckups(sql);
    }

    /**
     * 获取多条数据结果集
     *
     * @param sql
     * @return
     */
    private ArrayList<StudentHealthCheckup> getHealthCheckups(String sql, String... args) {
        try {
            CachedRowSetImpl rs = JDBCUtils.execSQL(sql, args);
            if (rs != null) {
                while (rs.next()) {
                    StudentHealthCheckup shc = new StudentHealthCheckup();
                    shc.setSid(rs.getString("sid"));
                    shc.setAge(rs.getInt("age"));
                    shc.setHeight(rs.getInt("height"));
                    shc.setWeight(rs.getInt("weight"));
                    shc.setBust(rs.getInt("bust"));
                    shc.setCheckupDate(rs.getDate("checkup_date"));
                    healthCheckups.add(shc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return healthCheckups;
    }
}
