package org.example.handler;

import org.example.entity.StudentDO;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;
import org.example.view.AddView;
import org.example.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 添加界面按钮功能
 *
 * @author by wlq on 2021/2/28
 * @version Java1.9 IDEA2020.2
 */
public class AddViewHandler implements ActionListener {
    private final AddView addView;
    private final MainView mainView;

    public AddViewHandler(AddView addView, MainView mainView) {
        this.addView = addView;
        this.mainView = mainView;
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        JButton jButton = (JButton) event.getSource();
        String text = jButton.getText();
        if ("添加".equals(text)) {
            StudentService studentService = new StudentServiceImpl();
            StudentDO studentDO = addView.buildStudentDo();
            boolean addResult = studentService.add(studentDO);
            if (addResult) {
                //重新加载表格
                mainView.setPageNow(1);
                mainView.reloadPageLabelText();
                mainView.sortReloadTable(mainView.getSortKey());
                addView.dispose();
            } else {
                JOptionPane.showMessageDialog(addView, "学号已存在,添加失败!");
            }

        }
    }
}
