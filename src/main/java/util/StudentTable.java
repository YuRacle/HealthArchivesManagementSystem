package util;

import service.StudentsService;

import javax.swing.table.AbstractTableModel;

public class StudentTable  extends AbstractTableModel{

    private static final long serialVersionUID = 1L;

    private Object[][] p = {};

   /* Object[][] p = {
            { "王鹏", "江西省九江市", new Integer(66), new Integer(32), new Integer(98), 1,1,1,1},
            { "宋兵", "浙江省杭州市", new Integer(85), new Integer(69),
                    new Integer(154), 1,1,1,1}, };*/

    String[] n = { "学号", "系别", "姓名", "性别" };

    public StudentTable() {
        Object[][] students = new StudentsService().getAllStudents();
        if (students != null) {
            p = students;
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
        return false;
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
