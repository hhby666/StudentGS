package org.example.service.impl;

import org.example.entity.AdminDO;
import org.example.service.AdminService;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 实现登录验证
 *
 * @author by wlq on 2021/3/8
 * @version Java1.9 IDEA2020.2
 */
public class AdminServiceImpl implements AdminService {
    @Override
    public boolean validate(AdminDO adminDO) {
        String pwdParam = adminDO.getPwd();
        String userName = adminDO.getUserName();
        String sql = "select pwd from user where user_name = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = DBUtil.getConn();
            if (conn == null) {
                return false;
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, adminDO.getUserName());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String pwd = resultSet.getString(1);
                if (pwdParam.equals(pwd)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeRs(resultSet);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return false;
    }

    @Override
    public boolean addAdmin(AdminDO adminDO) {
        String pwdParam = adminDO.getPwd();
        String userName = adminDO.getUserName();
        String insertSql = " insert into user(user_name, pwd) values(?, ?) ";
        String verifySql = " select count(*) from user where user_name=?";
        Connection conn = null;
        try {
            conn = DBUtil.getConn();
            if (conn == null) {
                return false;
            }
            if (verify(userName, verifySql, conn)) {
                return insert(pwdParam, userName, insertSql, conn);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
        }
        return false;
    }

    //检查用户是否已存在
    private boolean verify(String userName, String verifySql, Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(verifySql);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePs(ps);
            DBUtil.closeRs(rs);
        }
        return false;
    }

    //执行插入语句
    private boolean insert(String pwdParam, String userName, String insertSql, Connection conn) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insertSql);
            ps.setString(1, userName);
            ps.setString(2, pwdParam);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePs(ps);
        }
        return false;
    }
}
