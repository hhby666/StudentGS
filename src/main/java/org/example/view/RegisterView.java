package org.example.view;

import org.example.handler.RegisterViewHandler;

import javax.swing.*;
import java.awt.*;

/**
 * 注册界面
 *
 * @author by wlq on 2021/3/17
 * @version Java1.9 IDEA2020.2
 */
public class RegisterView extends JDialog {
    LoginView loginView;
    SpringLayout springLayout = new SpringLayout();
    JPanel centerPanel = new JPanel(springLayout);
    JLabel userNameLabel = new JLabel("用户名:");
    JTextField userText = new JTextField();
    JLabel pwdLabel = new JLabel("密码:");
    JPasswordField pwdField = new JPasswordField();
    JButton registerButton = new JButton("注册");
    JButton resetButton = new JButton("重置");

    RegisterViewHandler registerViewHandler;

    public RegisterView(LoginView loginView) {
        super(loginView, "注册", true);
        this.loginView = loginView;

        Container contentPane = getContentPane();
        registerViewHandler = new RegisterViewHandler(this);

        //设置字体,大小
        Font centerFont = new Font("楷体", Font.PLAIN, 20);
        userNameLabel.setFont(centerFont);
        pwdLabel.setFont(centerFont);
        registerButton.setFont(centerFont);
        registerButton.setPreferredSize(new Dimension(80, 35));
        resetButton.setFont(centerFont);
        resetButton.setPreferredSize(new Dimension(80, 35));
        userText.setPreferredSize(new Dimension(200, 30));
        pwdField.setPreferredSize(new Dimension(200, 30));

        //加入组件
        centerPanel.add(userNameLabel);
        centerPanel.add(userText);
        centerPanel.add(pwdLabel);
        centerPanel.add(pwdField);
        centerPanel.add(registerButton);
        centerPanel.add(resetButton);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        //布局
        layoutCenter();

        //添加事件监听
        registerButton.addKeyListener(registerViewHandler);
        registerButton.addActionListener(registerViewHandler);
        resetButton.addActionListener(registerViewHandler);

        //设置registerButton为默认按钮
        getRootPane().setDefaultButton(registerButton);

        //设置窗体属性
        setSize(400, 250);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void layoutCenter() {
        //组件弹簧布局,userNameLabel布局,水平居中
        Spring childWidth = Spring.sum(Spring.sum(Spring.width(userNameLabel),
                Spring.width(userText)), Spring.constant(20));
        int offsetX = childWidth.getValue() / 2;
        springLayout.putConstraint(SpringLayout.WEST, userNameLabel, -offsetX,
                SpringLayout.HORIZONTAL_CENTER, centerPanel);

        //垂直居中
        springLayout.putConstraint(SpringLayout.NORTH, userNameLabel, 20,
                SpringLayout.NORTH, centerPanel);

        //userText布局
        springLayout.putConstraint(SpringLayout.WEST, userText, 20,
                SpringLayout.EAST, userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH, userText, 0,
                SpringLayout.NORTH, userNameLabel);

        //pwdLabel布局
        springLayout.putConstraint(SpringLayout.EAST, pwdLabel, 0,
                SpringLayout.EAST, userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH, pwdLabel, 20,
                SpringLayout.SOUTH, userNameLabel);

        //pwdFiled布局
        springLayout.putConstraint(SpringLayout.WEST, pwdField, 20,
                SpringLayout.EAST, pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH, pwdField, 0,
                SpringLayout.NORTH, pwdLabel);

        //registerButton布局
        springLayout.putConstraint(SpringLayout.WEST, registerButton, 40,
                SpringLayout.WEST, pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH, registerButton, 20,
                SpringLayout.SOUTH, pwdLabel);

        //resetButton布局
        springLayout.putConstraint(SpringLayout.WEST, resetButton, 50,
                SpringLayout.EAST, registerButton);
        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 0,
                SpringLayout.NORTH, registerButton);
    }

    //获取用户名
    public String getUserName() {
        return userText.getText();
    }

    //获取密码
    public char[] getPassword() {
        return pwdField.getPassword();
    }

    //设置注册界面的密码和用户名
    public void setRegisterText(String user, String pwd) {
        userText.setText(user);
        pwdField.setText(pwd);
    }

    //设置登录界面的密码和用户名
    public void setLoginText(String user, String pwd) {
        loginView.setLoginText(user, pwd);
    }
}


