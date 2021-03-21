package org.example.entity;

/**
 * @author by wlq on 2021/3/3
 * @version Java1.9 IDEA2020.2
 * @description 管理员实体类, 包括用户名和密码
 */
public class AdminDO {
    private String userName;
    private String pwd;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
