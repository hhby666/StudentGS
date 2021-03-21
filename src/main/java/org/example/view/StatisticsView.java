package org.example.view;


import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;

import javax.swing.*;
import java.awt.*;

/**
 * 统计面板
 *
 * @author by wlq on 2021/3/12
 * @version Java1.9 IDEA2020.2
 */
public class StatisticsView extends JDialog {
    StudentService studentService = new StudentServiceImpl();

    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
    JTextArea statisticsArea = new JTextArea(30, 80);
    JScrollPane jScrollPane = new JScrollPane(statisticsArea);

    public StatisticsView(MainView mainView) {
        super(mainView, "统计数据", true);
        Container contentPane = getContentPane();
        statisticsArea.setEditable(false);
        statisticsArea.setFont(new java.awt.Font("宋体", Font.PLAIN, 14));
        studentService.analyse(this);
        jPanel.add(jScrollPane);
        contentPane.add(jPanel);

        //设置窗体属性
        Dimension preferredSize = jPanel.getPreferredSize();
        setSize(preferredSize);
        setResizable(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //设置文本区文本
    public void setStatisticsAreaText(String text) {
        this.statisticsArea.setText(statisticsArea.getText() + text + "\n");
    }

    //设置文本框滚动块高度最高
    public void setScrollBarValue() {
        JScrollBar sBar = this.jScrollPane.getVerticalScrollBar();
        sBar.setValue(sBar.getMaximum());
    }
}
