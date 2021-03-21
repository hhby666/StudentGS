package org.example.service;

import org.example.entity.AdminDO;

import java.sql.SQLException;

/**
 * 用于管理员的功能
 *
 * @author by wlq on 2021/3/8
 * @version Java1.9 IDEA2020.2
 */
public interface AdminService {
    /**
     * 验证用户名和密码是否正确
     *
     * @param adminDO 管理员实体
     * @return 用户名和密码的正确性
     * @throws SQLException 数据库查询异常
     */
    boolean validate(AdminDO adminDO) throws SQLException;

    /**
     * 添加新注册的管理员信息到数据库中
     *
     * @param adminDO 管理员实体
     * @return 用户名和密码是否添加成功
     */
    boolean addAdmin(AdminDO adminDO);
}
