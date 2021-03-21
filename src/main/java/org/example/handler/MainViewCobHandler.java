package org.example.handler;

import org.example.util.DBUtil;
import org.example.view.MainView;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 下拉列表框处理事件
 *
 * @author by wlq on 2021/3/10
 * @version Java1.9 IDEA2020.2
 */
public class MainViewCobHandler implements ItemListener {
    private final MainView mainView;

    public MainViewCobHandler(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String text = (String) e.getItem();
            if ("默认".equals(text)) {
                initSort();
                mainView.sortReloadTable(DBUtil.NAME);
            } else if ("学号".equals(text)) {
                initSort();
                mainView.sortReloadTable(DBUtil.ID);
            } else if ("总分".equals(text)) {
                initSort();
                mainView.sortReloadTable(DBUtil.SUM);
            } else if ("平均分".equals(text)) {
                initSort();
                mainView.sortReloadTable(DBUtil.AVERAGE);
            } else if ("语文".equals(text)) {
                initSort();
                mainView.sortReloadTable(DBUtil.CHINESE);
            } else if ("数学".equals(text)) {
                initSort();
                mainView.sortReloadTable(DBUtil.MATH);
            } else if ("英语".equals(text)) {
                initSort();
                mainView.sortReloadTable(DBUtil.ENGLISH);
            }
        }
    }

    //初始化主界面
    private void initSort() {
        mainView.setPageNow(1);
        mainView.isEnableNext(true);
        mainView.isEnablePre(false);
        mainView.reloadPageLabelText();
    }
}
