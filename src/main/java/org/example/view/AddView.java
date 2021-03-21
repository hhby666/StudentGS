package org.example.view;


import org.example.entity.StudentDO;
import org.example.handler.AddViewHandler;

import javax.swing.*;
import java.awt.*;

/**
 * 添加界面
 *
 * @author by wlq on 2021/3/12
 * @version Java1.9 IDEA2020.2
 */
public class AddView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
    JLabel nameLabel = new JLabel("姓名:", JLabel.RIGHT);
    JTextField nameText = new JTextField();
    JLabel idLabel = new JLabel("学号:", JLabel.RIGHT);
    JTextField idText = new JTextField();
    JLabel classLabel = new JLabel("班级:", JLabel.RIGHT);
    JTextField classText = new JTextField();
    JLabel chineseLabel = new JLabel("语文:", JLabel.RIGHT);
    JTextField chineseText = new JTextField();
    JLabel mathLabel = new JLabel("数学:", JLabel.RIGHT);
    JTextField mathText = new JTextField();
    JLabel englishLabel = new JLabel("英语:", JLabel.RIGHT);
    JTextField englishText = new JTextField();
    JButton addBtn = new JButton("添加");

    public AddView(MainView mainView) {
        super(mainView, "添加学生", true);
        AddViewHandler addViewHandler = new AddViewHandler(this, mainView);
        Container contentPane = getContentPane();

        //设置组件大小,添加组件
        Dimension labelDimension = new Dimension(80, 30);
        Dimension textDimension = new Dimension(200, 30);
        nameLabel.setPreferredSize(labelDimension);
        jPanel.add(nameLabel);
        nameText.setPreferredSize(textDimension);
        jPanel.add(nameText);
        idLabel.setPreferredSize(labelDimension);
        jPanel.add(idLabel);
        idText.setPreferredSize(textDimension);
        jPanel.add(idText);
        classLabel.setPreferredSize(labelDimension);
        jPanel.add(classLabel);
        classText.setPreferredSize(textDimension);
        jPanel.add(classText);
        chineseLabel.setPreferredSize(labelDimension);
        jPanel.add(chineseLabel);
        chineseText.setPreferredSize(textDimension);
        jPanel.add(chineseText);
        mathLabel.setPreferredSize(labelDimension);
        jPanel.add(mathLabel);
        mathText.setPreferredSize(textDimension);
        jPanel.add(mathText);
        englishLabel.setPreferredSize(labelDimension);
        jPanel.add(englishLabel);
        englishText.setPreferredSize(textDimension);
        jPanel.add(englishText);
        jPanel.add(addBtn);
        contentPane.add(jPanel);

        //添加事件监听
        addBtn.addActionListener(addViewHandler);

        //设置窗体属性
        setSize(350, 420);
        setResizable(false);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //获取要添加的学生属性
    public StudentDO buildStudentDo() {
        StudentDO studentDO = new StudentDO();
        studentDO.setName(nameText.getText());
        studentDO.setId(idText.getText());
        studentDO.setClassName(classText.getText());
        studentDO.setChinese(Integer.parseInt(chineseText.getText()));
        studentDO.setMath(Integer.parseInt(mathText.getText()));
        studentDO.setEnglish(Integer.parseInt(englishText.getText()));
        return studentDO;
    }
}
