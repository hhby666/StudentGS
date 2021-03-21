package org.example.handler;

import org.example.entity.AdminDO;
import org.example.service.AdminService;
import org.example.service.impl.AdminServiceImpl;
import org.example.view.LoginView;
import org.example.view.MainView;
import org.example.view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

/**
 * 登录界面按钮功能
 *
 * @author by wlq on 2021/2/28
 * @version Java1.9 IDEA2020.2
 */
public class LoginViewHandler extends KeyAdapter implements ActionListener {
    private final LoginView loginView;

    //自定义构造函数,引入LoginView
    public LoginViewHandler(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JButton jButton = (JButton) event.getSource();
        String text = jButton.getText();
        if ("重置".equals(text)) {
            loginView.setLoginText("", "");
        } else if ("登录".equals(text)) {
            try {
                login();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if ("注册".equals(text)) {
            System.out.println("注册");
            new RegisterView(loginView);
        }
    }

    //登录
    public void login() throws SQLException {
        String user = loginView.getUserName();
        char[] chars = loginView.getPassword();
        if (user == null || "".equals(user.trim()) ||
                chars == null) {
            JOptionPane.showMessageDialog(loginView, "用户名或密码必填!");
            return;
        }
        String password = new String(chars);

        //查询db
        AdminService adminService = new AdminServiceImpl();
        AdminDO adminDO = new AdminDO();
        adminDO.setUserName(user);
        adminDO.setPwd(password);
        boolean flag = false;
        flag = adminService.validate(adminDO);
        if (flag) {
            // 跳转到主界面并销毁登录界面
            new MainView();
            loginView.dispose();
        } else {
            //添加提示对话框
            JOptionPane.showMessageDialog(loginView, "用户名或密码错误!");
        }
    }

    //enter键触发默认按钮
    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ENTER == e.getKeyCode()) {
            try {
                login();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
