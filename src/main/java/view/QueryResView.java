package view;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class QueryResView extends JFrame{

    //数据列表
    private JScrollPane dataScrollPane = null;

    public QueryResView(AbstractTableModel tableModel) {
        this.setLayout(null);
        this.setSize(750,320);
        //只关闭当前小窗口
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.setTitle("添加体检数据");
        this.setResizable(false);

        // 利用MyTable来建立JTable
        JTable t = new JTable(tableModel);

//        t1.setPreferredScrollableViewportSize(new Dimension(550, 30));
        dataScrollPane = new JScrollPane(t);

        this.setContentPane(dataScrollPane);
        this.setVisible(true);
    }

}
