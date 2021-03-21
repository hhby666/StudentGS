package org.example.view.ext;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * 主界面表格模式类
 *
 * @author by wlq on 2021/3/1
 * @version Java1.9 IDEA2020.2
 */
public class MainViewTableModel extends DefaultTableModel {
    static Vector<String> columns = new Vector<>();
    private static final MainViewTableModel tableModel = new MainViewTableModel();

    static {
        columns.addElement("序号");
        columns.addElement("姓名");
        columns.addElement("学号");
        columns.addElement("班级");
        columns.addElement("语文");
        columns.addElement("数学");
        columns.addElement("英语");
        columns.addElement("总分");
        columns.addElement("平均分");
    }

    //私有构造器,防止外部新建对象,保证单例
    private MainViewTableModel() {
        super(null, columns);
    }

    public static MainViewTableModel assembleModel(Vector<Vector<Object>> data) {
        tableModel.setDataVector(data, columns);
        return tableModel;
    }

    public static void updateModel(Vector<Vector<Object>> data) {
        tableModel.setDataVector(data, columns);
    }

    public static Vector<String> getColumns() {
        return columns;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}