package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import service.DataService;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DynamicChartView extends JPanel {

    private Thread thread;
    private Boolean isStarting = false;

    private JSplitPane splitPane = new JSplitPane();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    //数据类别单选按钮组
    private JPanel radioPanel = new JPanel();
    private ButtonGroup radioGroup = new ButtonGroup();
    private JRadioButton radioButton1 = new JRadioButton("健康指数", true);
    private JRadioButton radioButton2 = new JRadioButton("身高");
    private JRadioButton radioButton3 = new JRadioButton("体重");
    private JRadioButton radioButton4 = new JRadioButton("胸围");

    private JLabel label1 = new JLabel("学生学号:");
    private JEditorPane sid = new JEditorPane();
    private JLabel label2 = new JLabel("平均数:");
    private JLabel aver = new JLabel("0");
    private JLabel label3 = new JLabel("增长值:");
    private JLabel value = new JLabel("0");
    private JLabel label4 = new JLabel("增长率:");
    private JLabel rate = new JLabel("0 %");

    private JButton button1 = new JButton("分析");
    private JButton button2 = new JButton("停止");

    private JFreeChart jFreeChart;
    private TimeSeries series;

    /**
     * 构造
     */
    public DynamicChartView() {

        this.createUI();
        this.setLayout(new BorderLayout());

        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(2);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setEnabled(false);//设置分隔条禁止拖动

        radioGroup.add(radioButton1);
        radioGroup.add(radioButton2);
        radioGroup.add(radioButton3);
        radioGroup.add(radioButton4);
        radioPanel.add(radioButton1);
        radioPanel.add(radioButton2);
        radioPanel.add(radioButton3);
        radioPanel.add(radioButton4);
        radioPanel.setLayout(null);
        radioButton1.setBounds(12, 25, 80, 25);
        radioButton2.setBounds(12, 55, 80, 25);
        radioButton3.setBounds(110, 25, 80, 25);
        radioButton4.setBounds(110, 55, 80, 25);
        radioPanel.setBorder(BorderFactory.createTitledBorder("数据类型"));
        radioPanel.setBounds(10, 20, 200, 90);

        label1.setBounds(20, 120, 100, 25);
        label1.setFont(new Font("微软雅黑", 1, 16));
        sid.setBounds(20, 150, 150, 25);
        sid.setBorder(BorderFactory.createTitledBorder(""));

        label2.setBounds(20, 220, 100, 25);
        label2.setFont(new Font("微软雅黑", 1, 16));
        aver.setBounds(120, 220, 100, 25);
        aver.setBorder(BorderFactory.createTitledBorder(""));
        label3.setBounds(20, 260, 100, 25);
        label3.setFont(new Font("微软雅黑", 1, 16));
        value.setBounds(120, 260, 100, 25);
        value.setBorder(BorderFactory.createTitledBorder(""));
        label4.setBounds(20, 300, 100, 25);
        label4.setFont(new Font("微软雅黑", 1, 16));
        rate.setBounds(120, 300, 100, 25);
        rate.setBorder(BorderFactory.createTitledBorder(""));

        button1.setBackground(Color.WHITE);
        button1.setFont(new Font("微软雅黑", 1, 20));
        button1.setBounds(20, 400, 100, 30);
        button2.setBackground(Color.WHITE);
        button2.setFont(new Font("微软雅黑", 1, 20));
        button2.setBounds(130, 400, 100, 30);

        leftPanel.setBorder(BorderFactory.createTitledBorder("Dynamic"));
        leftPanel.setLayout(null);

        leftPanel.add(radioPanel);
        leftPanel.add(label1);
        leftPanel.add(label2);
        leftPanel.add(label3);
        leftPanel.add(label4);
        leftPanel.add(sid);
        leftPanel.add(aver);
        leftPanel.add(value);
        leftPanel.add(rate);
        leftPanel.add(button1);
        leftPanel.add(button2);

        this.add(splitPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * 创建应用程序界面
     */
    private void createUI() {
        this.series = new TimeSeries("健康指数", Day.class);
        TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        ChartPanel chartPanel = new ChartPanel(createChart(dataset));

        //底部留空
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(chartPanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(rightPanel);

        button1.addActionListener(e -> {
            isStarting = true;
            thread = new Thread(() -> dynamicRun());
            thread.start();
        });

        button2.addActionListener(e -> {
            isStarting = false;
            thread = null;
        });
    }

    /**
     * 根据结果集构造JFreechart报表对象
     */
    private JFreeChart createChart(XYDataset dataset) {
        jFreeChart = ChartFactory.createTimeSeriesChart("动态折线图", "时间             ",
                "动态数值变化", dataset, true, true, false);
        XYPlot plot = (XYPlot) jFreeChart.getPlot();
        DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
//        dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
//        axis.setFixedAutoRange(60000.0);
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 200.0);

        return jFreeChart;
    }

    private void updateDate() {

        int type = 0;
        if (radioButton1.isSelected()) {
            type = 1;
        } else if (radioButton2.isSelected()) {
            type = 2;
        } else if (radioButton3.isSelected()) {
            type = 3;
        } else if (radioButton4.isSelected()) {
            type = 4;
        }
        String id = sid.getText();
        //获取jFreeChart y轴对象
        XYPlot plot = (XYPlot) jFreeChart.getPlot();
        ValueAxis axis = plot.getRangeAxis();
        int valueSum , increase, firstValue, index;

        switch (type) {
            case 1:
                series.clear();
                //设置y值
                axis.setRange(0.0, 100.0);

                Object[][] objects1 = new DataService().getAllMedicalData();
                valueSum = 0;increase=0;firstValue=0;index = 0;
                for (int i = 0; i < objects1.length; i++) {
                    if (id.equals(objects1[i][1])) {
                        index++;
                        Date date = (Date) objects1[i][0];
                        //获取年、月、日
                        int year, month, day;
                        Calendar now = Calendar.getInstance();
                        now.setTime(date);
                        year = now.get(Calendar.YEAR);
                        month = now.get(Calendar.MONTH) + 1;
                        day = now.get(Calendar.DATE);

//                        System.out.println(year + " " + month + " " + day);
                        int health = Integer.parseInt(objects1[i][5].toString());
                        if (index == 1) {
                            firstValue = health;
                        }

                        valueSum += health;
                        increase = health - firstValue;

                        aver.setText(String.valueOf(valueSum/(i+1)));
                        value.setText(String.valueOf(increase));
                        if (firstValue != 0) {
                            rate.setText(String.valueOf(increase*100/firstValue)+" %");
                        }else {
                            rate.setText("0 %");
                        }

                        if (isStarting) {
                            this.series.add(new Day(day, month, year), health);
                        }else {
                            return;
                        }
                        try {
                            thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第"+series.getItemCount()+"个数据节点");
                    }
                }
                break;
            case 2:
                series.clear();
                //设置y值
                axis.setRange(140.0, 200.0);

                Object[][] objects2 = new DataService().getAllCheckupData();
                valueSum = 0;increase=0;firstValue=0;index = 0;
                for (int i = 0; i < objects2.length; i++) {
                    if (id.equals(objects2[i][1])) {
                        index++;
                        Date date = (Date) objects2[i][0];
                        //获取年、月、日
                        int year, month, day;
                        Calendar now = Calendar.getInstance();
                        now.setTime(date);
                        year = now.get(Calendar.YEAR);
                        month = now.get(Calendar.MONTH) + 1;
                        day = now.get(Calendar.DATE);

//                        System.out.println(year + " " + month + " " + day);
                        int height = Integer.parseInt(objects2[i][6].toString());
                        if (index == 1) {
                            firstValue = height;
                        }

                        valueSum += height;
                        increase = height - firstValue;

                        aver.setText(String.valueOf(valueSum/(i+1)));
                        value.setText(String.valueOf(increase));
                        if (firstValue != 0) {
                            rate.setText(String.valueOf(increase*100/firstValue)+" %");
                        }else {
                            rate.setText("0 %");
                        }

                        if (isStarting) {
                            this.series.add(new Day(day, month, year), height);
                        }else {
                            return;
                        }
                        try {
                            thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第"+series.getItemCount()+"个数据节点");
                    }
                }
                break;
            case 3:
                series.clear();
                //设置y值
                axis.setRange(70.0, 160.0);

                Object[][] objects3 = new DataService().getAllCheckupData();
                valueSum = 0;increase=0;firstValue=0;index = 0;
                for (int i = 0; i < objects3.length; i++) {
                    if (id.equals(objects3[i][1])) {
                        index++;
                        Date date = (Date) objects3[i][0];
                        //获取年、月、日
                        int year, month, day;
                        Calendar now = Calendar.getInstance();
                        now.setTime(date);
                        year = now.get(Calendar.YEAR);
                        month = now.get(Calendar.MONTH) + 1;
                        day = now.get(Calendar.DATE);

//                        System.out.println(year + " " + month + " " + day);
                        int weight = Integer.parseInt(objects3[i][7].toString());
                        if (index == 1) {
                            firstValue = weight;
                        }

                        valueSum += weight;
                        increase = weight - firstValue;

                        aver.setText(String.valueOf(valueSum/(i+1)));
                        value.setText(String.valueOf(increase));
                        if (firstValue != 0) {
                            rate.setText(String.valueOf(increase*100/firstValue)+" %");
                        }else {
                            rate.setText("0 %");
                        }

                        if (isStarting) {
                            this.series.add(new Day(day, month, year), weight);
                        }else {
                            return;
                        }
                        try {
                            thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第"+series.getItemCount()+"个数据节点");
                    }
                }
                break;
            case 4:
                series.clear();
                //设置y值
                axis.setRange(40.0, 120.0);

                Object[][] objects4 = new DataService().getAllCheckupData();
                valueSum = 0;increase=0;firstValue=0;index = 0;
                for (int i = 0; i < objects4.length; i++) {
                    if (id.equals(objects4[i][1])) {
                        index++;
                        Date date = (Date) objects4[i][0];
                        //获取年、月、日
                        int year, month, day;
                        Calendar now = Calendar.getInstance();
                        now.setTime(date);
                        year = now.get(Calendar.YEAR);
                        month = now.get(Calendar.MONTH) + 1;
                        day = now.get(Calendar.DATE);

//                        System.out.println(year + " " + month + " " + day);
                        int bust = Integer.parseInt(objects4[i][8].toString());
                        if (index == 1) {
                            firstValue = bust;
                        }

                        valueSum += bust;
                        increase = bust - firstValue;

                        aver.setText(String.valueOf(valueSum/(i+1)));
                        value.setText(String.valueOf(increase));
                        if (firstValue != 0) {
                            rate.setText(String.valueOf(increase*100/firstValue)+" %");
                        }else {
                            rate.setText("0 %");
                        }

                        if (isStarting) {
                            this.series.add(new Day(day, month, year), bust);
                        }else {
                            return;
                        }
                        try {
                            thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第"+series.getItemCount()+"个数据节点");
                    }
                }
                break;
        }

    }

    /**
     * 动态运行
     */
    private void dynamicRun() {
        try {
            updateDate();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}


