package org.example.handler;


import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;
import org.example.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主界面按钮事件
 *
 * @author by wlq on 2021/3/10
 * @version Java1.9 IDEA2020.2
 */


public class MainViewBtnHandler implements ActionListener {
    private final MainView mainView;

    //自定义构造函数引入mainView
    public MainViewBtnHandler(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = ((JButton) e.getSource()).getText();
        if ("增加".equals(text)) {
            new AddView(mainView);
        } else if ("修改".equals(text)) {
            String[] selectedStudentIds = mainView.getSelectedStudentIds();
            if (selectedStudentIds.length != 1) {
                JOptionPane.showMessageDialog(mainView, "一次只能修改一行!");
            } else {
                new UpdateView(mainView, selectedStudentIds[0]);
            }
        } else if ("删除".equals(text)) {
            String[] selectedStudentIds = mainView.getSelectedStudentIds();
            if (selectedStudentIds.length == 0) {
                JOptionPane.showMessageDialog(mainView, "请选择要删除的行!");
            } else {
                int option = JOptionPane.showConfirmDialog(mainView,
                        "你确认要删除选中的" + selectedStudentIds.length +
                                "行吗?", "确认删除", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    //确认就执行删除
                    StudentService studentService = new StudentServiceImpl();
                    boolean delResult = studentService.delete(selectedStudentIds);
                    if (delResult) {
                        //重新加载表格
                        mainView.setPageNow(1);
                        mainView.reloadPageLabelText();
                        mainView.sortReloadTable(mainView.getSortKey());
                    } else {
                        JOptionPane.showMessageDialog(mainView, "删除失败!");
                    }
                }
            }
        } else if ("查询".equals(text)) {
            new SearchView(mainView);
            mainView.setPageNow(1);
        } else if ("统计".equals(text)) {
            new StatisticsView(mainView);
        } else if ("上一页".equals(text)) {
            if (mainView.getPageNow() == 1) {
                mainView.isEnablePre(false);
            } else {
                mainView.isEnablePre(true);
                mainView.setPageNow(mainView.getPageNow() - 1);
                mainView.reloadPageLabelText();
                mainView.sortReloadTable(mainView.getSortKey());
            }
            mainView.isEnableNext(true);
        } else if ("下一页".equals(text)) {
            if (mainView.getPageNow() == mainView.getPageCount()) {
                mainView.isEnableNext(false);
            } else {
                mainView.isEnableNext(true);
                mainView.setPageNow(mainView.getPageNow() + 1);
                mainView.reloadPageLabelText();
                mainView.sortReloadTable(mainView.getSortKey());
            }
            mainView.isEnablePre(true);
        }
    }

}
