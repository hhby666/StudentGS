package org.example.entity;

/**
 * 学生实体类,包含学生的各种成绩信息.
 *
 * @author by wlq on 2021/3/3
 * @version Java1.9 IDEA2020.2
 */
public class StudentDO {
    private String name;
    private String id;
    //搜素用的ID
    private String searchKeyId;
    private String className;
    private int chinese;
    private int math;
    private int english;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public String getSearchKeyId() {
        return searchKeyId;
    }

    public void setSearchKeyId(String searchKeyId) {
        this.searchKeyId = searchKeyId;
    }
}
