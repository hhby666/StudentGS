package org.example.res;

import java.util.Vector;

/**
 * 表格工具类,包含表格的数据,及记录条数.
 *
 * @author by wlq on 2021/3/8
 * @version Java1.9 IDEA2020.2
 */
public class TableDTO {
    //表格的数据
    private Vector<Vector<Object>> data;
    //记录的条数
    private int totalCount;

    public Vector<Vector<Object>> getData() {
        return data;
    }

    public void setData(Vector<Vector<Object>> data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
