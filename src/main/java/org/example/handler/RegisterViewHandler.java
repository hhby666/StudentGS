package org.example.handler;

import org.example.entity.AdminDO;
import org.example.service.AdminService;
import org.example.service.impl.AdminServiceImpl;
import org.example.view.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 注册界面按键处理
 *
 * @author by wlq on 2021/3/17
 * @version Java1.9 IDEA2020.2
 */
public class RegisterViewHandler extends KeyAdapter implements ActionListener {
    RegisterView registerView;

    //自定义构造函数引入registerView界面
    public RegisterViewHandler(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();
        if ("重置".equals(text)) {
            registerView.setRegisterText("", "");
        } else if ("注册".equals(text)) {
            register();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ENTER == e.getKeyCode()) {
            register();
        }
    }

    public void register() {
        String user = registerView.getUserName();
        char[] chars = registerView.getPassword();
        if (user == null || "".equals(user.trim()) ||
                chars == null) {
            JOptionPane.showMessageDialog(registerView, "用户名或密码必填!");
            return;
        }
        String password = new String(chars);

        //查询db
        AdminService adminService = new AdminServiceImpl();
        AdminDO adminDO = new AdminDO();
        adminDO.setUserName(user);
        adminDO.setPwd(password);
        boolean flag = false;
        flag = adminService.addAdmin(adminDO);
        if (flag) {
            //添加提示对话框
            JOptionPane.showMessageDialog(registerView, "注册成功,请重新登陆!");
            registerView.setLoginText(user, password);
            registerView.dispose();
        } else {
            //添加提示对话框
            JOptionPane.showMessageDialog(registerView, "注册失败,用户已存在!");
        }
    }
}


