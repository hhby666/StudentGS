package org.example.util;


import javax.swing.*;
import java.awt.*;

/**
 * 获取屏幕大小的工具类
 *
 * @author by wlq on 2021/3/1
 * @version Java1.9 IDEA2020.2
 */
public class DimensionUtil {
    public static Rectangle getBounds() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //保证主界面不会覆盖屏幕任务栏
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                new JFrame().getGraphicsConfiguration());

        return new Rectangle(screenInsets.left, screenInsets.top,
                screenSize.width - screenInsets.left - screenInsets.right,
                screenSize.height - screenInsets.top - screenInsets.bottom);
    }
}
