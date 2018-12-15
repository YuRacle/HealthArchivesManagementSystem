package dao;

import com.sun.rowset.CachedRowSetImpl;
import pojo.Student;
import util.JDBCUtils;

import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDao {

    private ArrayList<Student> students = new ArrayList<Student>();

    /**
     * 根据sid寻找学生
     * @param sid 学生学号
     * @return 学生对象
     */
    public Student findStuBySid(String sid) {
        String sql = "select * from students where sid = ?";

        Student student = new Student();
        try {
            ResultSet rs = JDBCUtils.execSQL(sql, sid);
            if (rs.next()) {
                student.setSid(rs.getString("sid"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setDept(rs.getString("dept"));
            } else {
                student = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    /**
     * 添加用户
     * @param s
     * @return 1:成功 0:失败
     */
    public int addStu(Student s) {
        String sid = s.getSid();
        String name = s.getName();
        String sex = s.getSex();
        String dept = s.getDept();

        String sql = "insert into students values(?,?,?,?)";
        if (findStuBySid(sid) == null) {
            try {
                JDBCUtils.execSQL(sql, sid, name, sex, dept);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 根据学生学号删除学生信息
     * @param sid 学生学号
     * @return 1:成功 0:失败
     */
    public int deleteStuBySid(String sid) {
        String sql = "delete from students where sid = ?";
        if (findStuBySid(sid) != null) {
            try {
                JDBCUtils.execSQL(sql, sid);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 根据sid修改学生信息
     * @param oldSid 旧学生学号
     * @param student
     * @return 1: 成功 0:失败
     */
    public int updateMsgBySid(String oldSid,Student student) {
        String sid = student.getSid();
        String name =student.getName();
        String sex =student.getSex();
        String dept = student.getDept();
        String sql = "update students set sid=?,name=?,sex=?,dept=? where sid=?";
        try {
            JDBCUtils.execSQL(sql,sid,name,sex,dept,oldSid);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取所有学生信息
     * @return 所有学生对象集合
     */
    public ArrayList<Student> getStudents() {
        String sql = "select * from students";
        try {
            CachedRowSetImpl rs = JDBCUtils.execSQL(sql);
            if (rs != null) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setSid(rs.getString("sid"));
                    student.setName(rs.getString("name"));
                    student.setSex(rs.getString("sex"));
                    student.setDept(rs.getString("dept"));
                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}
