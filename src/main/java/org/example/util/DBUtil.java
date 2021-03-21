package org.example.util;

import java.sql.*;

/**
 * 连接数据库工具
 *
 * @author by wlq on 2021/3/3
 * @version Java1.9 IDEA2020.2
 */
public class DBUtil {
    //数据库中列名常数
    public static final String NAME = "stu_name";
    public static final String ID = "id";
    public static final String CLASS = "class";
    public static final String CHINESE = "chinese";
    public static final String MATH = "math";
    public static final String ENGLISH = "english";
    public static final String SUM = "sum";
    public static final String AVERAGE = "average";

    //数据库参数
    private static final String URL = "jdbc:mysql://localhost:3306/studentgs?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PWD = "1248";

    static {
        try {
            //加载驱动
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public static Connection getConn() {
        try {
            return DriverManager.getConnection(URL, USER_NAME, PWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭连接
    public static void closeConn(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭预编译资源
    public static void closePs(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭数据集资源
    public static void closeRs(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
