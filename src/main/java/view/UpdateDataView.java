package view;

import pojo.StudentHealthCheckup;
import pojo.StudentMedicalRecord;
import service.DataService;
import util.HealthCheckupTable;
import util.MedicalRecordTable;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class UpdateDataView extends JFrame {

    private JPanel panel = new JPanel();
    private JScrollPane jScrollPane;
    private JTable table;
    private JButton button1 = new JButton("修改");
    private JButton button2 = new JButton("取消");
    private ArrayList<String> oldsids = new ArrayList<String>();
    ArrayList<Date> oldDates = new ArrayList<>();


    /**
     * @param objects
     * @param type 数据类型 1：病历数据 2：体检数据
     */
    public UpdateDataView(Object[][] objects, int type) {
        this.setLayout(null);
        this.setSize(640,350);
        //只关闭当前小窗口
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.setTitle("修改数据");
        this.setResizable(false);

        //获取旧sid
        for (int i=0;i<objects.length;i++) {
            String oldsid = (String) objects[i][1];
            oldsids.add(oldsid);
        }

        //获取旧date
        for (int i=0;i<objects.length;i++) {
            Date oldDay = (Date) objects[i][0];
            oldDates.add(oldDay);
        }

        if (type == 1) {
            MedicalRecordTable t = new MedicalRecordTable();
            t.setP(objects);
            table = new JTable(t);
            jScrollPane = new JScrollPane(table);
        }else if (type == 2) {
            HealthCheckupTable t = new HealthCheckupTable();
            t.setP(objects);
            table = new JTable(t);
            jScrollPane = new JScrollPane(table);
        }

        button1.addActionListener(e -> {
            try {
                updateDate(type);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });

        button2.addActionListener(e -> {
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.add(jScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.setLayout(null);
        buttonPanel.setPreferredSize(new Dimension(640,50));
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        button1.setBounds(160,10,100,30);
        button2.setBounds(380,10,100,30);
        button1.setBackground(Color.WHITE);
        button2.setBackground(Color.WHITE);

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void updateDate(int type) throws ParseException {
        if (type == 1) {
            ArrayList<StudentMedicalRecord> medicalRecords = new ArrayList<>();
            int rowCount = table.getRowCount();

            for (int i=0;i<rowCount;i++) {
                StudentMedicalRecord smr = new StudentMedicalRecord();

                String sid = (String) table.getValueAt(i,1);
                String diagnosis = (String) table.getValueAt(i,5);
//                java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse((String) table.getValueAt(i, 0));
//                Date recordDate = new Date(d.getTime());
                Date recordDate = (Date) table.getValueAt(i, 0);
                smr.setSid(sid);
                smr.setDiagnosis(diagnosis);
                smr.setRecordDate(recordDate);

                medicalRecords.add(smr);
            }
            new DataService().updateMedicalData(oldsids, oldDates, medicalRecords);

            JOptionPane.showMessageDialog(this, "修改成功", "提示",JOptionPane.WARNING_MESSAGE);
            this.dispose();
        }else if (type == 2) {
            ArrayList<StudentHealthCheckup> healthCheckups = new ArrayList<>();
            int rowCount = table.getRowCount();

            for (int i=0;i<rowCount;i++) {
                StudentHealthCheckup shc = new StudentHealthCheckup();
                String sid = (String) table.getValueAt(i,1);
                int age = Integer.parseInt(table.getValueAt(i,5).toString());
                int height = Integer.parseInt(table.getValueAt(i,6).toString());
                int weight = Integer.parseInt(table.getValueAt(i,7).toString());
                int bust = Integer.parseInt(table.getValueAt(i,8).toString());
                Date checkupDate = (Date) table.getValueAt(i,0);
                shc.setSid(sid);
                shc.setAge(age);
                shc.setHeight(height);
                shc.setWeight(weight);
                shc.setBust(bust);
                shc.setCheckupDate(checkupDate);

                healthCheckups.add(shc);
            }
            new DataService().updateCheckupData(oldsids,oldDates,healthCheckups);

            JOptionPane.showMessageDialog(this, "修改成功", "提示",JOptionPane.WARNING_MESSAGE);
            this.dispose();
        }else {
            JOptionPane.showMessageDialog(this, "修改失败", "提示",JOptionPane.WARNING_MESSAGE);
        }
    }

}
