package org.example.view.ext;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * 通用表格类
 *
 * @author by wlq on 2021/3/1
 * @version Java1.9 IDEA2020.2
 */
public class MyTable extends JTable {
    public MyTable(TableModel tableModel) {
        super(tableModel);

        //设置表头属性
        JTableHeader tableHeader = getTableHeader();
        tableHeader.setFont(new Font(null, Font.BOLD, 16));
        tableHeader.setForeground(new Color(0, 0, 0));
        tableHeader.setBackground(new Color(0, 255, 255));
        tableHeader.setPreferredSize(new Dimension(0, 34));

        //字体
        setFont(new Font(null, Font.PLAIN, 14));
        setForeground(Color.black);

        //表框
        setGridColor(Color.BLACK);

        //行高
        setRowHeight(27);

        //设置多行选择
        getSelectionModel().setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
}
