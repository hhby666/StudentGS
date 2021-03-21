package org.example.view.ext;

import javax.swing.table.TableColumn;
import java.util.Vector;

/**
 * 主界面表格
 *
 * @author by wlq on 2021/3/1
 * @version Java1.9 IDEA2020.2
 */
public class MainViewTable extends MyTable {
    public MainViewTable(MainViewTableModel tableModel) {
        super(tableModel);
    }

    public void renderRule() {
        //设置表格,列的渲染方法
        Vector<String> columns = MainViewTableModel.getColumns();
        MainViewCellRender render = new MainViewCellRender();
        for (int i = 0; i < columns.size(); i++) {
            TableColumn column = getColumn(columns.get(i));
            column.setCellRenderer(render);
            if (i == 0) {
                column.setPreferredWidth(25);
            }
            column.setResizable(false);
        }
    }
}
