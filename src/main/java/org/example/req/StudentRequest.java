package org.example.req;

/**
 * 学生查询工具类,包含查询功能所需要的一些属性
 * (页数,每页容量,搜索关键字).
 *
 * @author by wlq on 2021/3/8
 * @version Java1.9 IDEA2020.2
 */
public class StudentRequest {

    private int pageNow;
    private int pageSize;
    //定义索引
    private String researchKey;

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getResearchKey() {
        return researchKey;
    }

    public void setResearchKey(String researchKey) {
        this.researchKey = researchKey;
    }

    //返回当前记录所在的条数范围
    public int getStart() {
        return (pageNow - 1) * pageSize;
    }
}
