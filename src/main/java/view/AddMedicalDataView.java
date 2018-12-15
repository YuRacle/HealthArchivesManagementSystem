package view;

import pojo.Student;
import pojo.StudentMedicalRecord;
import service.DataService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

public class AddMedicalDataView extends JFrame {

    public static void main(String[] args) {
        AddMedicalDataView.instance();
    }

    public static AddMedicalDataView addMedicalDataView;

    private JPanel panel = new JPanel();
    private JLabel label1 = new JLabel("学号:");
    private JEditorPane editorPane1 = new JEditorPane();
    private JLabel label2 = new JLabel("姓名:");
    private JEditorPane editorPane2 = new JEditorPane();
    private JLabel label3 = new JLabel("性别:");
    private JComboBox comboBox3 = new JComboBox();//性别
    private JLabel label4 = new JLabel("系别:");
    private JComboBox comboBox4 = new JComboBox();//系别
    private JLabel label5 = new JLabel("诊断:");
    private JEditorPane editorPane5 = new JEditorPane();
    private JLabel label6 = new JLabel("日期:");
    private JSpinner date6 = null;

    private JButton button1 = new JButton("添加");
    private JButton button2 = new JButton("取消");

    public static AddMedicalDataView instance() {
        if (addMedicalDataView == null) {
            addMedicalDataView = new AddMedicalDataView();
        }
        return addMedicalDataView;
    }

    private AddMedicalDataView() {

        this.setLayout(null);
        this.setSize(640,250);
        //只关闭当前小窗口
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.setTitle("添加病历数据");
        this.setResizable(false);

        comboBox3.addItem("男");
        comboBox3.addItem("女");

        comboBox4.addItem("信工学院");
        comboBox4.addItem("药学院");
        comboBox4.addItem("外语学院");

        //获得时间日期模型
        SpinnerDateModel model = new SpinnerDateModel();
        //获得JSPinner对象
        date6 = new JSpinner(model);
        date6.setValue(new java.util.Date());
        //设置时间格式
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date6,
                "yyyy-MM-dd");
        date6.setEditor(editor);

        button1.setBackground(Color.WHITE);
        button2.setBackground(Color.WHITE);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int isAdd = 0;
                try {
                    isAdd = addMedicalDate();
                    if (isAdd == 1) {
                        JOptionPane.showMessageDialog(addMedicalDataView, "添加成功", "提示",JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(addMedicalDataView, "添加失败：不能为空！", "提示",JOptionPane.WARNING_MESSAGE);
                }
                addMedicalDataView.dispose();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMedicalDataView.dispose();
            }
        });

        label1.setBounds(50,30,40,20);
        editorPane1.setBounds(90,30,120,20);
        label2.setBounds(250,30,40,20);
        editorPane2.setBounds(290,30,120,20);
        label3.setBounds(450,30,40,20);
        comboBox3.setBounds(490,30,120,20);
        label4.setBounds(50,70,40,20);
        comboBox4.setBounds(90,70,120,20);
        label5.setBounds(50,110,40,20);
        editorPane5.setBounds(90,110,350,40);
        label6.setBounds(250,70,40,20);
        date6.setBounds(290,70,140,20);
        button1.setBounds(160,170,100,30);
        button2.setBounds(350,170,100,30);

        panel.setLayout(null);
        panel.add(label1);
        panel.add(editorPane1);
        panel.add(label2);
        panel.add(editorPane2);
        panel.add(label3);
        panel.add(comboBox3);
        panel.add(label4);
        panel.add(comboBox4);
        panel.add(label5);
        panel.add(editorPane5);
        panel.add(label6);
        panel.add(date6);
        panel.add(button1);
        panel.add(button2);

        this.setContentPane(panel);
        this.setVisible(true);
    }
    //添加病历数据
    private int addMedicalDate() throws Exception{
        String sid = editorPane1.getText();
        String name = editorPane2.getText();
        String sex = (String) comboBox3.getSelectedItem();
        String dept = (String) comboBox4.getSelectedItem();

        java.util.Date date = (java.util.Date) date6.getValue();
        //日期 日+1
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        Date recordDate = new Date(date.getTime());
        //util.date转sql.date
//        java.util.Date date = (java.util.Date) date6.getValue();
//        Date recordDate = new Date(date.getTime());
        String diagnosis = editorPane5.getText();

        Student student = new Student(sid,name,sex,dept);
        StudentMedicalRecord smr = new StudentMedicalRecord(sid,diagnosis,recordDate);
        new DataService().addMedicalData(student,smr);

        return 1;
    }
}
