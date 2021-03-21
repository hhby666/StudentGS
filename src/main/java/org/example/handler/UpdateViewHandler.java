package org.example.handler;

import org.example.entity.StudentDO;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;
import org.example.view.MainView;
import org.example.view.UpdateView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 修改界面按钮功能
 *
 * @author by wlq on 2021/2/28
 * @version Java1.9 IDEA2020.2
 */
public class UpdateViewHandler implements ActionListener {
    private final UpdateView updateView;
    private final MainView mainView;

    public UpdateViewHandler(UpdateView updateView, MainView mainView) {
        this.updateView = updateView;
        this.mainView = mainView;
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        JButton jButton = (JButton) event.getSource();
        String text = jButton.getText();
        if ("修改".equals(text)) {
            StudentService studentService = new StudentServiceImpl();
            StudentDO studentDO = updateView.buildStudentDo();
            boolean updateResult = studentService.update(studentDO);
            if (updateResult) {
                //重新加载表格
                mainView.setPageNow(1);
                mainView.reloadPageLabelText();
                mainView.sortReloadTable(mainView.getSortKey());
                updateView.dispose();
            } else {
                JOptionPane.showMessageDialog(updateView, "要修改的学号已存在,修改失败!");
            }
        }
    }
}
