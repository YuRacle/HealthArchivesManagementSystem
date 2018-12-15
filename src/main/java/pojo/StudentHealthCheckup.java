package pojo;

import java.sql.Date;

/**
 * 学生体检数据对象
 */
public class StudentHealthCheckup{

    private String sid;//学生学号
    private int age;//年龄
    private int height;//身高
    private int weight;//体重
    private int bust;//胸围
    private Date checkupDate;//体检日期

    public StudentHealthCheckup() {
    }

    public StudentHealthCheckup(String sid, int age, int height, int weight, int bust, Date checkupDate) {
        this.sid = sid;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bust = bust;
        this.checkupDate = checkupDate;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public void setCheckupDate(Date checkupDate) {
        this.checkupDate = checkupDate;
    }

    public String getSid() {
        return sid;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getBust() {
        return bust;
    }

    public Date getCheckupDate() {
        return checkupDate;
    }
}
