package view;

import service.DataService;
import service.StudentsService;
import util.ExcelOperation;
import util.HealthCheckupTable;
import util.MedicalRecordTable;
import util.StudentTable;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static view.IndexView.indexView;


public class DataView extends JPanel {

    public static void main(String[] args) {
    }

    private JSplitPane textSplitPane = new JSplitPane();
    private JSplitPane dataSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JSplitPane splitPane = new JSplitPane();
    //table列表
    public static JTable medicalRecordTable;
    public static JTable healthCheckupTable;
    public static JTable studentTable;

    //查询组件
    private JCheckBox checkBox1 = new JCheckBox("学号");
    private JEditorPane editorPane1 = new JEditorPane();
    private JCheckBox checkBox2 = new JCheckBox("系别");
    private JComboBox comboBox2 = new JComboBox();//系别
    private JCheckBox checkBox3 = new JCheckBox("姓名");
    private JEditorPane editorPane3 = new JEditorPane();
    private JCheckBox checkBox4 = new JCheckBox("性别");
    private JComboBox comboBox4 = new JComboBox();//性别
    private JCheckBox checkBox5 = new JCheckBox("日期");
    private JSpinner date5 = null;

    //数据类别单选按钮组
    private JPanel radioPanel = new JPanel();
    private ButtonGroup radioGroup = new ButtonGroup();
    private JRadioButton radioButton1 = new JRadioButton("病历数据", true);
    private JRadioButton radioButton2 = new JRadioButton("体检数据");
    //操作选项按钮
    private JPanel operationPanel = new JPanel();
    private JButton operationbutton1 = new JButton("查询");
    private JButton operationbutton2 = new JButton("添加");
    private JButton operationbutton3 = new JButton("刷新");
    private JButton operationbutton4 = new JButton("导入");
    private JButton operationbutton5 = new JButton("导出");
    private JButton operationbutton6 = new JButton("修改");
    private JButton operationbutton7 = new JButton("删除");
    private JButton operationbutton8 = new JButton("删除学生");
    //数据列表
    private JScrollPane dataScrollPane1 = null;
    private JScrollPane dataScrollPane2 = null;
    //学生列表
    private JScrollPane stuPanel = null;
    public static Boolean isMedicalData = true;
    public static Boolean isCheckupData = false;


    public DataView() {

        /**
         * 由于MyTable类继承了AbstractTableModel
         * 并且实现了getColumnCount()，getRowCount()、getValueAt
         * ()方法，因此可以通过MyTable来禅城TableModel的实体
         * 利用JTable所提供的getTableColumnModel()方法取得TableColumnModel对象
         * 再由TableColumnModel类所提供的getColumn（）方法取得TableColumn对象
         * TableColumn类可针对表格中的每一行做具体的设置 例如设置字段的宽度，某行的标头，设置输入较复杂的数据类型等。
         * 在这里利用TableColumn类所提供的setCellEditor()方法，将JComboBox作为第二行的默认编辑组件
         */
        MedicalRecordTable t1 = new MedicalRecordTable();
        // 利用MyTable来建立JTable
        medicalRecordTable = new JTable(t1);
        HealthCheckupTable t2 = new HealthCheckupTable();
        healthCheckupTable = new JTable(t2);
        StudentTable t3 = new StudentTable();
        studentTable = new JTable(t3);
        dataScrollPane1 = new JScrollPane(medicalRecordTable);
        dataScrollPane2 = new JScrollPane(healthCheckupTable);
        stuPanel = new JScrollPane(studentTable);


        comboBox4.addItem("男");
        comboBox4.addItem("女");

        comboBox2.addItem("信工学院");
        comboBox2.addItem("药学院");
        comboBox2.addItem("外语学院");

        //获得时间日期模型
        SpinnerDateModel model = new SpinnerDateModel();
        //获得JSPinner对象
        date5 = new JSpinner(model);
        date5.setValue(new java.util.Date());
        //设置时间格式
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date5,
                "yyyy-MM-dd");
        date5.setEditor(editor);

        radioGroup.add(radioButton1);
        radioGroup.add(radioButton2);
        radioPanel.add(radioButton1);
        radioPanel.add(radioButton2);
        radioPanel.setLayout(null);
        radioButton1.setBounds(7, 40, 80, 20);
        radioButton2.setBounds(7, 90, 80, 20);

        operationPanel.setLayout(null);
        operationPanel.add(checkBox1);
        operationPanel.add(editorPane1);
        operationPanel.add(checkBox2);
        operationPanel.add(comboBox2);
        operationPanel.add(checkBox3);
        operationPanel.add(editorPane3);
        operationPanel.add(checkBox4);
        operationPanel.add(comboBox4);
        operationPanel.add(checkBox5);
        operationPanel.add(date5);

        operationPanel.add(operationbutton1);
        operationPanel.add(operationbutton2);
        operationPanel.add(operationbutton3);
        operationPanel.add(operationbutton4);
        operationPanel.add(operationbutton5);
        operationPanel.add(operationbutton6);
        operationPanel.add(operationbutton7);
        operationPanel.add(operationbutton8);

        checkBox1.setBounds(35, 35, 60, 20);
        editorPane1.setBounds(95, 35, 100, 20);
        checkBox2.setBounds(235, 85, 60, 20);
        comboBox2.setBounds(295, 85, 100, 20);
        checkBox3.setBounds(35, 85, 60, 20);
        editorPane3.setBounds(95, 85, 100, 20);
        checkBox4.setBounds(235, 35, 60, 20);
        comboBox4.setBounds(295, 35, 100, 20);
        checkBox5.setBounds(35, 135, 60, 20);
        date5.setBounds(95, 135, 150, 20);

        operationbutton1.setBounds(495, 30, 100, 30);
        operationbutton2.setBounds(635, 30, 100, 30);
        operationbutton3.setBounds(495, 130, 100, 30);
        operationbutton4.setBounds(635, 130, 100, 30);
        operationbutton5.setBounds(775, 130, 100, 30);
        operationbutton6.setBounds(495, 80, 100, 30);
        operationbutton7.setBounds(635, 80, 100, 30);
        operationbutton8.setBounds(775, 80, 100, 30);
        operationbutton1.setBackground(Color.WHITE);
        operationbutton2.setBackground(Color.WHITE);
        operationbutton3.setBackground(Color.WHITE);
        operationbutton4.setBackground(Color.WHITE);
        operationbutton5.setBackground(Color.WHITE);
        operationbutton6.setBackground(Color.WHITE);
        operationbutton7.setBackground(Color.WHITE);
        operationbutton8.setBackground(Color.WHITE);

        radioPanel.setBorder(BorderFactory.createTitledBorder("数据类别:"));
        operationPanel.setBorder(BorderFactory.createTitledBorder("查询:"));
        stuPanel.setBorder(BorderFactory.createTitledBorder("学生列表:"));


        this.setLayout(new BorderLayout());

        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(2);
        splitPane.setLeftComponent(stuPanel);
        splitPane.setRightComponent(dataSplitPane);
        splitPane.setEnabled(false);//设置分隔条禁止拖动

        textSplitPane.setDividerLocation(120);
        textSplitPane.setDividerSize(2);
        textSplitPane.setLeftComponent(radioPanel);
        textSplitPane.setRightComponent(operationPanel);
        textSplitPane.setEnabled(false);//设置分隔条禁止拖动

        dataSplitPane.setDividerLocation(480);
        dataSplitPane.setDividerSize(2);
        dataSplitPane.setLeftComponent(dataScrollPane1);
        dataSplitPane.setRightComponent(textSplitPane);
        dataSplitPane.setEnabled(false);//设置分隔条禁止拖动
        this.add(splitPane, BorderLayout.CENTER);

        radioButton1.addActionListener(event -> {
            isMedicalData = true;
            isCheckupData = false;
            changeDataPane();
        });
        radioButton2.addActionListener(event -> {
            isCheckupData = true;
            isMedicalData = false;
            changeDataPane();
        });

        operationbutton1.addActionListener(e -> {
            queryData();
        });

        operationbutton2.addActionListener(event -> {
            if (isMedicalData) {
                AddMedicalDataView.instance().setVisible(true);
            } else if (isCheckupData) {
                AddCheckupDataView.instance().setVisible(true);
            }
        });

        operationbutton3.addActionListener(e -> {
            refresh();
        });
        //导入
        operationbutton4.addActionListener(e -> {
            importDate();
            refresh();
        });
        //导出
        operationbutton5.addActionListener(e -> {
            exportDate();
        });

        operationbutton6.addActionListener(e -> {
            updateDate();
            refresh();
        });

        operationbutton7.addActionListener(e -> {
            deleteDate();
            refresh();
        });

        operationbutton8.addActionListener(e -> {
            deleteStuDate();
            refresh();
        });

        this.setVisible(true);
    }

    /**
     * 根据数据类型变换数据列表
     */
    private void changeDataPane() {
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(2);
        splitPane.setLeftComponent(stuPanel);

        if (isMedicalData) {
            dataSplitPane.setDividerLocation(480);
            dataSplitPane.setDividerSize(2);
            dataSplitPane.setLeftComponent(dataScrollPane1);
        } else if (isCheckupData) {
            dataSplitPane.setDividerLocation(480);
            dataSplitPane.setDividerSize(2);
            dataSplitPane.setLeftComponent(dataScrollPane2);
        }
    }

    /**
     * 刷新信息
     */
    private void refresh() {
        StudentTable st = new StudentTable();
        studentTable = new JTable(st);
        stuPanel = new JScrollPane(studentTable);

        if (isMedicalData) {
            MedicalRecordTable table = new MedicalRecordTable();
            // 利用MyTable来建立JTable
            medicalRecordTable = new JTable(table);
            dataScrollPane1 = new JScrollPane(medicalRecordTable);
            changeDataPane();
//            JOptionPane.showMessageDialog(indexView, "刷新成功", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (isCheckupData) {
            HealthCheckupTable table = new HealthCheckupTable();
            healthCheckupTable = new JTable(table);
            dataScrollPane2 = new JScrollPane(healthCheckupTable);
            changeDataPane();
//            JOptionPane.showMessageDialog(indexView, "刷新成功", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * 多选删除多条信息
     */
    private void deleteDate() {
        if (isMedicalData) {
            int[] selectedRows = medicalRecordTable.getSelectedRows();
            ArrayList<String> sids = new ArrayList<String>();
            ArrayList<java.sql.Date> dates = new ArrayList<java.sql.Date>();
            for (int i : selectedRows) {
                String s = (String) medicalRecordTable.getValueAt(i, 1);
                sids.add(s);
                java.sql.Date d = (java.sql.Date) medicalRecordTable.getValueAt(i, 0);
                dates.add(d);
            }
            new DataService().deleteMedicalData(sids, dates);
            JOptionPane.showMessageDialog(indexView, "删除成功", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (isCheckupData) {
            int[] selectedRows = healthCheckupTable.getSelectedRows();
            ArrayList<String> sids = new ArrayList<String>();
            ArrayList<java.sql.Date> dates = new ArrayList<java.sql.Date>();
            for (int i : selectedRows) {
                String s = (String) healthCheckupTable.getValueAt(i, 1);
                sids.add(s);
                java.sql.Date d = (java.sql.Date) healthCheckupTable.getValueAt(i, 0);
                dates.add(d);
            }
            new DataService().deleteCheckupData(sids, dates);
            JOptionPane.showMessageDialog(indexView, "删除成功", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(indexView, "删除失败：没有数据可删除", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * 修改数据
     */
    private void updateDate() {
        if (isMedicalData) {
            int[] selectedRows = medicalRecordTable.getSelectedRows();
            Object[][] objects = new Object[selectedRows.length][6];
            int row = 0;
            for (int i : selectedRows) {
                for (int j = 0; j < 6; j++) {
                    objects[row][j] = medicalRecordTable.getValueAt(i, j);
                }
                row++;
            }

            new UpdateDataView(objects, 1);
        } else if (isCheckupData) {
            int[] selectedRows = healthCheckupTable.getSelectedRows();
            Object[][] objects = new Object[selectedRows.length][9];
            int row = 0;
            for (int i : selectedRows) {
                for (int j = 0; j < 9; j++) {
                    objects[row][j] = healthCheckupTable.getValueAt(i, j);
                }
                row++;
            }

            new UpdateDataView(objects, 2);
        }
    }
    //查询数据
    private void queryData() {

        boolean isSelectedSid = checkBox1.isSelected();
        boolean isSelectedDept = checkBox2.isSelected();
        boolean isSelectedName = checkBox3.isSelected();
        boolean isSelectedSex = checkBox4.isSelected();
        boolean isSelectedDate = checkBox5.isSelected();

        String sid = editorPane1.getText();
        String dept = comboBox2.getSelectedItem().toString();
        String name = editorPane3.getText();
        String sex = comboBox4.getSelectedItem().toString();
        Date date = (Date) date5.getValue();
        //没有勾选不进行查询
        if (isSelectedDate && isSelectedDept && isSelectedName && isSelectedSex && isSelectedSid) {
            JOptionPane.showMessageDialog(indexView, "查询失败：没有勾选条件", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (isMedicalData) {
            int row = 0;
            int rowCount = medicalRecordTable.getRowCount();
            Object[][] objects = new Object[rowCount][6];
            for (int i = 0; i < rowCount; i++) {
                if (isSelectedSid) {
                    if (!sid.equals(medicalRecordTable.getValueAt(i, 1)))
                        continue;
                    System.out.println("sid--------");
                }
                if (isSelectedName) {
                    if (!name.equals(medicalRecordTable.getValueAt(i, 2)))
                        continue;
                    System.out.println("name--------");
                }
                if (isSelectedSex) {
                    if (!sex.equals(medicalRecordTable.getValueAt(i, 3)))
                        continue;
                    System.out.println("sex--------");
                }
                if (isSelectedDept) {
                    if (!dept.equals(medicalRecordTable.getValueAt(i, 4)))
                        continue;
                    System.out.println("dept--------");
                }
                if (isSelectedDate) {
                    Date d = (Date) medicalRecordTable.getValueAt(i, 0);
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
                    if (!(fmt.format(date)).equals(fmt.format(medicalRecordTable.getValueAt(i, 0))))
                        continue;
                    System.out.println("date--------");
                }

                for (int j = 0; j < 6; j++) {
                    objects[row][j] = medicalRecordTable.getValueAt(i, j);
                }
                row++;
            }
            MedicalRecordTable table = new MedicalRecordTable();
            table.setP(objects);

            new QueryResView(table).setVisible(true);
            System.out.println("ok--------");
        } else if (isCheckupData) {
            int row = 0;
            int rowCount = healthCheckupTable.getRowCount();
            Object[][] objects = new Object[rowCount][9];
            for (int i = 0; i < rowCount; i++) {
                if (isSelectedSid) {
                    if (!sid.equals(healthCheckupTable.getValueAt(i, 1)))
                        continue;
                    System.out.println("sid--------");
                }
                if (isSelectedName) {
                    if (!name.equals(healthCheckupTable.getValueAt(i, 2)))
                        continue;
                    System.out.println("name--------");
                }
                if (isSelectedSex) {
                    if (!sex.equals(healthCheckupTable.getValueAt(i, 3)))
                        continue;
                    System.out.println("sex--------");
                }
                if (isSelectedDept) {
                    if (!dept.equals(healthCheckupTable.getValueAt(i, 4)))
                        continue;
                    System.out.println("dept--------");
                }
                if (isSelectedDate) {
                    Date d = (Date) healthCheckupTable.getValueAt(i, 0);
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
                    if (!(fmt.format(date)).equals(fmt.format(healthCheckupTable.getValueAt(i, 0))))
                        continue;
                    System.out.println("date--------");
                }

                for (int j = 0; j < 9; j++) {
                    objects[row][j] = healthCheckupTable.getValueAt(i, j);
                }
                row++;
            }
            HealthCheckupTable table = new HealthCheckupTable();
            table.setP(objects);

            new QueryResView(table).setVisible(true);
            System.out.println("ok--------");
        }
    }
    //删除学生
    private void deleteStuDate() {
        int[] selectedRows = studentTable.getSelectedRows();
        ArrayList<String> sids = new ArrayList<String>();
        for (int i : selectedRows) {
            String sid = (String) studentTable.getValueAt(i, 0);
            sids.add(sid);
        }
        new StudentsService().deleteStudents(sids);
        JOptionPane.showMessageDialog(indexView, "删除成功", "提示", JOptionPane.WARNING_MESSAGE);
    }

    //导出Excel
    private void exportDate() {
        if (isMedicalData) {
            MedicalRecordTable model = (MedicalRecordTable) medicalRecordTable.getModel();
            Object[][] p = model.getP();
            String path = new ExcelOperation().exportExcel(p, 1);

            JOptionPane.showMessageDialog(indexView, "导出成功,文件已保存:"+path, "提示", JOptionPane.WARNING_MESSAGE);
        }else if (isCheckupData) {
            HealthCheckupTable model = (HealthCheckupTable) healthCheckupTable.getModel();
            Object[][] p = model.getP();
            String path = new ExcelOperation().exportExcel(p, 2);

            JOptionPane.showMessageDialog(indexView, "导出成功,文件已保存:"+path, "提示", JOptionPane.WARNING_MESSAGE);
        }
    }
    //导入Excel
    private void importDate() {
        if (isMedicalData) {
            try {
                new ExcelOperation().importExcel(1);
                refresh();

                JOptionPane.showMessageDialog(indexView, "导入成功", "提示", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (isCheckupData) {

            try {
                new ExcelOperation().importExcel(2);
                refresh();

                JOptionPane.showMessageDialog(indexView, "导入成功", "提示", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
