package org.example.view;

import org.example.util.DBUtil;
import org.example.view.ext.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 查询界面
 *
 * @author by wlq on 2021/3/10
 * @version Java1.9 IDEA2020.2
 */
public class SearchView extends MyFrame {
    final String[] ways = new String[]{"姓名", "学号", "班级"};
    final JComboBox<String> searchCoB = new JComboBox<String>(ways);
    //定义组件及布局
    SpringLayout springLayout = new SpringLayout();
    JPanel centerPanel = new JPanel(springLayout);
    JLabel searchLabel = new JLabel("索引:");
    JTextField searchKeyText = new JTextField();
    JButton searchButton = new JButton("查询");
    JButton resetButton = new JButton("重置");
    MainView mainView;

    public SearchView(final MainView mainView) {
        this.mainView = mainView;
        Container contentPane = getContentPane();

        //设置字体,大小
        Font font = new Font("楷体", Font.PLAIN, 20);
        searchLabel.setFont(font);
        searchKeyText.setFont(font);
        searchCoB.setFont(font);
        searchButton.setFont(font);
        resetButton.setFont(font);
        searchKeyText.setPreferredSize(new Dimension(180, 30));

        //添加组件
        centerPanel.add(searchKeyText);
        centerPanel.add(searchCoB);
        centerPanel.add(searchLabel);
        centerPanel.add(searchButton);
        centerPanel.add(resetButton);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        //添加监听事件
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.researchReloadTable(getSearchText(), getSearchKey());
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSearchText("");
                setSearchKey("姓名");
                mainView.clearTable();
            }
        });

        //布局
        centerLayout();

        //设置窗口创建和销毁时事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                mainView.setEnableSearchBtn(false);
                mainView.setHideSouthPanel(false);
                mainView.clearTable();
                mainView.researchReloadTable("123", DBUtil.NAME);
                System.out.println(mainView.getSortKey());
            }

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainView.setEnableSearchBtn(true);
                mainView.setHideSouthPanel(true);
                mainView.setPageNow(1);
                mainView.reloadPageLabelText();
                mainView.sortReloadTable(mainView.getSortKey());
            }
        });

        //设置窗体属性
        setSize(400, 180);
        setTitle("查询");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void centerLayout() {
        //searchKeyText布局
        Spring childWidth = Spring.sum(Spring.sum(Spring.sum(Spring.
                width(searchKeyText), Spring.width(searchLabel)), Spring.
                width(searchCoB)), Spring.constant(30));
        int offsetX = childWidth.getValue() / 2;
        springLayout.putConstraint(SpringLayout.WEST, searchKeyText, -offsetX,
                SpringLayout.HORIZONTAL_CENTER, centerPanel);

        //垂直居中
        springLayout.putConstraint(SpringLayout.NORTH, searchKeyText, 20,
                SpringLayout.NORTH, centerPanel);

        //searchLabel布局
        springLayout.putConstraint(SpringLayout.WEST, searchLabel, 20,
                SpringLayout.EAST, searchKeyText);
        springLayout.putConstraint(SpringLayout.NORTH, searchLabel, 0,
                SpringLayout.NORTH, searchKeyText);

        //searchCoB布局
        springLayout.putConstraint(SpringLayout.WEST, searchCoB, 10,
                SpringLayout.EAST, searchLabel);
        springLayout.putConstraint(SpringLayout.NORTH, searchCoB, 0,
                SpringLayout.NORTH, searchLabel);

        //searchButton布局
        springLayout.putConstraint(SpringLayout.WEST, searchButton, 65,
                SpringLayout.WEST, searchKeyText);
        springLayout.putConstraint(SpringLayout.NORTH, searchButton, 20,
                SpringLayout.SOUTH, searchKeyText);

        //resetButton布局
        springLayout.putConstraint(SpringLayout.WEST, resetButton, 50,
                SpringLayout.EAST, searchButton);
        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 0,
                SpringLayout.NORTH, searchButton);
    }

    //得到索引
    public String getSearchKey() {
        String item = (String) this.searchCoB.getSelectedItem();
        if ("学号".equals(item)) {
            return DBUtil.ID;
        } else if ("班级".equals(item)) {
            return DBUtil.CLASS;
        } else {
            return DBUtil.NAME;
        }
    }

    //设置搜索索引
    public void setSearchKey(String state) {
        this.searchCoB.setSelectedItem(state);
    }

    //获取搜索框内容
    public String getSearchText() {
        return this.searchKeyText.getText();
    }

    //设置搜索框内容
    public void resetSearchText(String text) {
        this.searchKeyText.setText(text);
    }
}
