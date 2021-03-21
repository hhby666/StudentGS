package org.example.view;

import org.example.handler.LoginViewHandler;
import org.example.view.ext.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

/**
 * 登录界面
 *
 * @author by wlq on 2021/2/28
 * @version Java1.9 IDEA2020.2
 */
public class LoginView extends MyFrame {

    //中心面板弹簧布局
    SpringLayout springLayout = new SpringLayout();
    JPanel centerPanel = new JPanel(springLayout);

    JLabel nameLabel = new JLabel("学生成绩管理系统登录", JLabel.CENTER);

    JLabel userNameLabel = new JLabel("用户名:");
    JTextField userText = new JTextField();

    JLabel pwdLabel = new JLabel("密码:");
    JPasswordField pwdField = new JPasswordField();

    JButton loginButton = new JButton("登录");
    JButton resetButton = new JButton("重置");
    JButton registerButton = new JButton("注册");

    //系统托盘
    SystemTray systemTray;
    TrayIcon trayIcon;

    //定义事件处理类
    LoginViewHandler loginHandler;

    //自定义构造函数
    public LoginView() {
        Container contentPane = getContentPane();
        loginHandler = new LoginViewHandler(this);

        //设置字体,大小
        Font centerFont = new Font("楷体", Font.PLAIN, 20);
        nameLabel.setFont(new Font("华文行楷", Font.PLAIN, 40));
        nameLabel.setPreferredSize(new Dimension(0, 80));
        userNameLabel.setFont(centerFont);
        pwdLabel.setFont(centerFont);
        loginButton.setFont(centerFont);
        loginButton.setPreferredSize(new Dimension(80, 35));
        resetButton.setFont(centerFont);
        resetButton.setPreferredSize(new Dimension(80, 35));
        registerButton.setFont(centerFont);
        registerButton.setPreferredSize(new Dimension(210, 35));
        userText.setPreferredSize(new Dimension(200, 30));
        pwdField.setPreferredSize(new Dimension(200, 30));

        //加入组件
        centerPanel.add(userNameLabel);
        centerPanel.add(userText);
        centerPanel.add(pwdLabel);
        centerPanel.add(pwdField);
        centerPanel.add(loginButton);
        centerPanel.add(resetButton);
        centerPanel.add(registerButton);
        contentPane.add(nameLabel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        //中心面板布局
        layoutCenter();

        //给按钮添加监听事件
        loginButton.addKeyListener(loginHandler);
        loginButton.addActionListener(loginHandler);
        resetButton.addActionListener(loginHandler);
        registerButton.addActionListener(loginHandler);

        //设置loginButton为默认按钮
        getRootPane().setDefaultButton(loginButton);

        //增加系统托盘
        if (SystemTray.isSupported()) {
            systemTray = SystemTray.getSystemTray();
            trayIcon = new TrayIcon(new ImageIcon(
                    Objects.requireNonNull(LoginView.class.getClassLoader().
                            getResource("1.png"))).getImage());

            trayIcon.setImageAutoSize(true);
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            //增加最小化时销毁资源
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowIconified(WindowEvent e) {
                    LoginView.this.dispose();
                }
            });
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int clickCount = e.getClickCount();
                    if (clickCount == 1) {
                        LoginView.this.setExtendedState(JFrame.NORMAL);
                    }
                    LoginView.this.setVisible(true);
                }
            });
        }

        //设置窗体属性
        setSize(600, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void layoutCenter() {
        //userNameLabel布局,水平居中
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

        //loginButton布局
        springLayout.putConstraint(SpringLayout.WEST, loginButton, 40,
                SpringLayout.WEST, pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH, loginButton, 20,
                SpringLayout.SOUTH, pwdLabel);

        //resetButton布局
        springLayout.putConstraint(SpringLayout.WEST, resetButton, 50,
                SpringLayout.EAST, loginButton);
        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 0,
                SpringLayout.NORTH, loginButton);

        //registerButton布局
        springLayout.putConstraint(SpringLayout.WEST, registerButton, 0,
                SpringLayout.WEST, loginButton);
        springLayout.putConstraint(SpringLayout.NORTH, registerButton, 12,
                SpringLayout.SOUTH, loginButton);

    }

    //获取用户名
    public String getUserName() {
        return userText.getText();
    }

    //获取密码
    public char[] getPassword() {
        return pwdField.getPassword();
    }

    //设置文本框内容
    public void setLoginText(String user, String pwd) {
        this.userText.setText(user);
        this.pwdField.setText(pwd);
    }
}
