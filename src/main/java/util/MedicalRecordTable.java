package util;

import service.DataService;

import javax.swing.table.AbstractTableModel;

public class MedicalRecordTable extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private Object[][] p = {};

    /*Object[][] p = {
            { "王鹏", "江西省九江市", new Integer(66), new Integer(32), new Integer(98), 1},
            { "宋兵", "浙江省杭州市", new Integer(85), new Integer(69),
                    new Integer(154), 1}, };*/

    String[] n = { "日期", "学号", "姓名", "性别", "系别", "诊断" };

    public MedicalRecordTable() {
        Object[][] allMedicalData = new DataService().getAllMedicalData();
        if (allMedicalData != null) {
            p = allMedicalData;
        }
    }

    @Override
    public int getRowCount() {
        return p.length;
    }

    @Override
    public int getColumnCount() {
        return n.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return p[row][col];
    }

    @Override
    public String getColumnName(int column) {
        return n[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 5) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        p[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setP(Object[][] p) {
        this.p = p;
    }

    public Object[][] getP() {
        return p;
    }
}
