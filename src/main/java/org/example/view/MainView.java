package org.example.view;


import org.example.handler.MainViewBtnHandler;
import org.example.handler.MainViewCobHandler;
import org.example.req.StudentRequest;
import org.example.res.TableDTO;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;
import org.example.util.DBUtil;
import org.example.util.DimensionUtil;
import org.example.view.ext.MainViewTable;
import org.example.view.ext.MainViewTableModel;
import org.example.view.ext.MyFrame;

import javax.swing.*;
import java.awt.*;

/**
 * 主界面
 *
 * @author by wlq on 2021/3/1
 * @version Java1.9 IDEA2020.2
 */
public class MainView extends MyFrame {
    //排序下拉框
    final String[] ways = new String[]{"默认", "学号", "总分",
            "平均分", "语文", "数学", "英语"};
    final JComboBox<String> sortCoB = new JComboBox<String>(ways);
    //定义上部组件
    JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    JButton addButton = new JButton("增加");
    JButton updateButton = new JButton("修改");
    JButton deleteButton = new JButton("删除");
    JButton searchButton = new JButton("查询");
    JButton statButton = new JButton("统计");
    JLabel sortLabel = new JLabel("排序:");
    //定义下部组件
    JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel pageLabel = new JLabel();
    JButton preButton = new JButton("上一页");
    JButton nextButton = new JButton("下一页");
    //定义主界面表格
    MainViewTable mainViewTable;
    //定义事件监听器处理类
    MainViewBtnHandler mainViewBtnHandler;
    MainViewCobHandler mainViewCobHandler;
    //第几页
    private int pageNow = 1;
    //每页几条数据
    private int pageSize = 19;
    //总页数
    private int pageCount;

    public MainView() {
        mainViewBtnHandler = new MainViewBtnHandler(this);
        mainViewCobHandler = new MainViewCobHandler(this);
        Container contentPane = getContentPane();

        //根据屏幕大小计算显示列数
        Rectangle bounds = DimensionUtil.getBounds();
        pageSize = (int) Math.floor(bounds.height / 32.0);

        //放置北边组件
        northLayout(contentPane);

        //设置中间的表格
        centerLayout(contentPane);

        //放置南边组件
        southLayout(contentPane);

        //根据屏幕大小设置主界面大小
        setBounds(bounds);

        //设置窗体属性
        //设置窗体充满屏幕的可见大小
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setVisible(true);
    }

    private void centerLayout(Container contentPane) {
        StudentService studentService = new StudentServiceImpl();
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setPageNow(pageNow);
        studentRequest.setPageSize(pageSize);
        TableDTO tableDTO = studentService.sortStudents(studentRequest, DBUtil.NAME);
        pageCount = (int) Math.ceil(1.0 * tableDTO.getTotalCount() / pageSize);
        MainViewTableModel mainViewTableModel =
                MainViewTableModel.assembleModel(tableDTO.getData());
        mainViewTable = new MainViewTable(mainViewTableModel);
        mainViewTable.renderRule();
        JScrollPane jScrollPane = new JScrollPane(mainViewTable);
        contentPane.add(jScrollPane, BorderLayout.CENTER);
    }

    private void southLayout(Container contentPane) {
        preButton.addActionListener(mainViewBtnHandler);
        nextButton.addActionListener(mainViewBtnHandler);
        southPanel.add(preButton);
        southPanel.add(nextButton);
        reloadPageLabelText();
        southPanel.add(pageLabel);
        contentPane.add(southPanel, BorderLayout.SOUTH);
    }

    private void northLayout(Container contentPane) {
        addButton.addActionListener(mainViewBtnHandler);
        deleteButton.addActionListener(mainViewBtnHandler);
        updateButton.addActionListener(mainViewBtnHandler);
        searchButton.addActionListener(mainViewBtnHandler);
        statButton.addActionListener(mainViewBtnHandler);
        sortCoB.addItemListener(mainViewCobHandler);
        northPanel.add(addButton);
        northPanel.add(deleteButton);
        northPanel.add(updateButton);
        northPanel.add(searchButton);
        northPanel.add(statButton);
        northPanel.add(sortLabel);
        northPanel.add(sortCoB);
        contentPane.add(northPanel, BorderLayout.NORTH);
    }

    //排序时重新加载表格
    public void sortReloadTable(String key) {
        StudentService studentService = new StudentServiceImpl();
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setPageNow(pageNow);
        studentRequest.setPageSize(pageSize);
        TableDTO tableDTO = studentService.sortStudents(studentRequest, key);
        MainViewTableModel.updateModel(tableDTO.getData());
        mainViewTable.renderRule();
    }

    //查询时重新加载表格
    public void researchReloadTable(String researchKey, String key) {
        StudentService studentService = new StudentServiceImpl();
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setPageNow(pageNow);
        studentRequest.setPageSize(pageSize);
        studentRequest.setResearchKey(researchKey);
        TableDTO tableDTO = studentService.researchStudents(studentRequest, key);
        MainViewTableModel.updateModel(tableDTO.getData());
        mainViewTable.renderRule();
    }

    //获取被选中的行的id信息
    public String[] getSelectedStudentIds() {
        int[] selectedRows = mainViewTable.getSelectedRows();
        String[] ids = new String[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            int rowIndex = selectedRows[i];
            Object idObj = mainViewTable.getValueAt(rowIndex, 2);
            ids[i] = idObj.toString();
        }
        return ids;
    }

    //获取排序方式
    public String getSortKey() {
        String item = (String) sortCoB.getSelectedItem();
        if ("总分".equals(item)) {
            return DBUtil.SUM;
        } else if ("平均分".equals(item)) {
            return DBUtil.AVERAGE;
        } else if ("语文".equals(item)) {
            return DBUtil.CHINESE;
        } else if ("数学".equals(item)) {
            return DBUtil.MATH;
        } else if ("英语".equals(item)) {
            return DBUtil.ENGLISH;
        } else if ("学号".equals(item)) {
            return DBUtil.ID;
        } else {
            return DBUtil.NAME;
        }
    }

    //加载底部页数显示
    public void reloadPageLabelText() {
        this.pageLabel.setText(this.pageNow + "/" +
                this.pageCount);
    }

    //设置下一页按钮是否可用
    public void isEnableNext(boolean state) {
        this.nextButton.setEnabled(state);
    }

    //设置上一页按钮是否可用
    public void isEnablePre(boolean state) {
        this.preButton.setEnabled(state);
    }

    //设置下端组件是否可见
    public void setHideSouthPanel(boolean state) {
        this.southPanel.setVisible(state);
    }

    //设置查询键是否可用
    public void setEnableSearchBtn(boolean flag) {
        this.searchButton.setEnabled(flag);
    }
    //清空MainViewTable
    public void clearTable() {
        MainViewTableModel.updateModel(null);
        mainViewTable.renderRule();
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageCount() {
        return pageCount;
    }
}
