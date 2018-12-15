package view.dataOperationView;

import util.HealthCheckupTable;
import view.DataView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueryBySidView extends JFrame {

    public static void main(String[] args) {
        QueryBySidView.instance();
    }

    public static QueryBySidView queryBySidView;

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("学生学号");
    private JTextPane textPane = new JTextPane();
    private JButton button = new JButton("查询");

    public static QueryBySidView instance() {
        if (queryBySidView == null) {
            queryBySidView = new QueryBySidView();
        }
        return queryBySidView;
    }

    public QueryBySidView() {

        this.setLayout(null);
        this.setSize(640,290);
        //只关闭当前小窗口
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.setTitle("查询");
        this.setResizable(false);

        panel.setLayout(null);
        panel.add(label);
        panel.add(textPane);
        panel.add(button);

        label.setBounds(270,30,100,50);
        label.setFont(new Font("微软雅黑",1, 20));
        textPane.setBounds(160,100,320,30);
        textPane.setFont(new Font("微软雅黑",1, 16));
        button.setBounds(270,165,100,40);
        button.setBackground(Color.WHITE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryBySidView.dispose();
                if (DataView.isMedicalData) {
                    new MedicalQueryResView(queryBySidView.getSid()).setVisible(true);
                }else if (DataView.isCheckupData) {
                    new CheckupQueryResView(queryBySidView.getSid()).setVisible(true);
                }
            }
        });

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private String getSid() {
        String sid = textPane.getText();
        textPane.setText("");
        return sid;
    }
}
