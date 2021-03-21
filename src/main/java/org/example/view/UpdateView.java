package org.example.view;


import org.example.entity.StudentDO;
import org.example.handler.UpdateViewHandler;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;

import javax.swing.*;
import java.awt.*;

/**
 * 修改界面
 *
 * @author by wlq on 2021/3/12
 * @version Java1.9 IDEA2020.2
 */
public class UpdateView extends JDialog {
    UpdateViewHandler updateViewHandler;
    //定义中心流布局面板
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
    //定义组件
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
    JButton updateBtn = new JButton("修改");
    //定位用的学生ID
    private final String searchId;

    public UpdateView(MainView mainView, String selectedStudentId) {
        super(mainView, "修改学生", true);
        updateViewHandler = new UpdateViewHandler(this, mainView);
        searchId = selectedStudentId;
        Container contentPane = getContentPane();

        //查询selectedStudentId对应的记录并回显
        StudentService studentService = new StudentServiceImpl();
        StudentDO selectedStudent = studentService.getById(selectedStudentId);

        //设置组件大小,添加组件,在组件上显示数据
        Dimension labelDimension = new Dimension(80, 30);
        Dimension textDimension = new Dimension(200, 30);

        nameLabel.setPreferredSize(labelDimension);
        jPanel.add(nameLabel);

        nameText.setPreferredSize(textDimension);
        nameText.setText(selectedStudent.getName());
        jPanel.add(nameText);

        idLabel.setPreferredSize(labelDimension);
        jPanel.add(idLabel);

        idText.setPreferredSize(textDimension);
        idText.setText(selectedStudent.getId());
        jPanel.add(idText);

        classLabel.setPreferredSize(labelDimension);
        jPanel.add(classLabel);

        classText.setPreferredSize(textDimension);
        classText.setText(selectedStudent.getClassName());
        jPanel.add(classText);

        chineseLabel.setPreferredSize(labelDimension);
        jPanel.add(chineseLabel);

        chineseText.setPreferredSize(textDimension);
        chineseText.setText(String.valueOf(selectedStudent.getChinese()));
        jPanel.add(chineseText);

        mathLabel.setPreferredSize(labelDimension);
        jPanel.add(mathLabel);

        mathText.setPreferredSize(textDimension);
        mathText.setText(String.valueOf(selectedStudent.getMath()));
        jPanel.add(mathText);

        englishLabel.setPreferredSize(labelDimension);
        jPanel.add(englishLabel);

        englishText.setPreferredSize(textDimension);
        englishText.setText(String.valueOf(selectedStudent.getEnglish()));
        jPanel.add(englishText);

        updateBtn.addActionListener(updateViewHandler);
        jPanel.add(updateBtn);
        contentPane.add(jPanel);

        //设置窗体属性
        setSize(350, 420);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //获取修改后的学生信息
    public StudentDO buildStudentDo() {
        StudentDO studentDO = new StudentDO();
        studentDO.setName(nameText.getText());
        studentDO.setId(idText.getText());
        studentDO.setClassName(classText.getText());
        studentDO.setChinese(Integer.parseInt(chineseText.getText()));
        studentDO.setMath(Integer.parseInt(mathText.getText()));
        studentDO.setEnglish(Integer.parseInt(englishText.getText()));
        studentDO.setSearchKeyId(this.searchId);
        return studentDO;
    }
}
