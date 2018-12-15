package service;

import dao.StudentDao;
import pojo.Student;
import util.DataToObject;

import java.util.ArrayList;

public class StudentsService {

    private StudentDao studentDao = new StudentDao();

    /**
     * 获取所有学生信息
     * @return
     */
    public Object[][] getAllStudents() {
        ArrayList<Student> students = studentDao.getStudents();
        Object[][] objects = DataToObject.studentsDataToObject(students);
        return objects;
    }

    /**
     * 可多选，删除学生信息
     * @param sidList
     */
    public void deleteStudents(ArrayList<String> sidList) {
        for (String sid : sidList) {
            studentDao.deleteStuBySid(sid);
        }
    }
}
