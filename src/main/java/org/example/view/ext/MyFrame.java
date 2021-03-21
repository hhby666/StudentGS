package org.example.view.ext;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 通用窗口类
 *
 * @author by wlq on 2021/2/28
 * @version Java1.9 IDEA2020.2
 */
public class MyFrame extends JFrame {
    public MyFrame() {
        super("学生成绩管理系统");

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //设置图标
        URL imageUrl = MyFrame.class.getClassLoader().getResource("1.png");
        Image image = null;
        if (imageUrl != null) {
            image = new ImageIcon(imageUrl).getImage();
        }
        setIconImage(image);
    }
}
