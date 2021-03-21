package org.example.view.ext;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 主界面表格渲染类
 *
 * @author by wlq on 2021/3/1
 * @version Java1.9 IDEA2020.2
 */
public class MainViewCellRender extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //隔行变色
        if (row % 2 == 1) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.LIGHT_GRAY);
        }

        //文本水平居中
        setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}