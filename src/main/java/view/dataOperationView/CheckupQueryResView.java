package view.dataOperationView;

import service.DataService;
import util.HealthCheckupTable;

import javax.swing.*;
import java.awt.*;

public class CheckupQueryResView extends JFrame {

    public static void main(String[] args) {
        new CheckupQueryResView("111");
    }


    //数据列表
    private JScrollPane dataScrollPane = null;


    public CheckupQueryResView(String sid) {
        this.setLayout(null);
        this.setSize(750,320);
        //只关闭当前小窗口
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.setTitle("添加体检数据");
        this.setResizable(false);

        HealthCheckupTable table = new HealthCheckupTable();
        Object[][] checkupDataBySid = new DataService().findCheckupDataBySid(sid);
        table.setP(checkupDataBySid);
        // 利用MyTable来建立JTable
        JTable t = new JTable(table);

//        t1.setPreferredScrollableViewportSize(new Dimension(550, 30));
        dataScrollPane = new JScrollPane(t);

        this.setContentPane(dataScrollPane);
        this.setVisible(true);
    }
}
