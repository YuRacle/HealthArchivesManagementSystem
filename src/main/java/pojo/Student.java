package pojo;

/**
 * 学生对象
 */
public class Student {
    private String sid;//学号
    private String name;//姓名
    private String sex;//性别
    private String dept;//专业系别

    public Student() {
    }

    public Student(String sid, String name, String sex, String dept) {
        this.sid = sid;
        this.name = name;
        this.sex = sex;
        this.dept = dept;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getDept() {
        return dept;
    }
}
