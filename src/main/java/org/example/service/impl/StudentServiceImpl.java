package org.example.service.impl;


import org.example.entity.StudentDO;
import org.example.req.StudentRequest;
import org.example.res.TableDTO;
import org.example.service.StudentService;
import org.example.util.DBUtil;
import org.example.view.StatisticsView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Vector;

/**
 * 实现StudentService接口
 *
 * @author by wlq on 2021/3/8
 * @version Java1.9 IDEA2020.2
 */
public class StudentServiceImpl implements StudentService {
    @Override
    public TableDTO sortStudents(StudentRequest request, String key) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from grade order by ").append(key);
        if (key.equals(DBUtil.ID) || key.equals(DBUtil.NAME)) {
            sql.append(" asc limit ");
        } else {
            sql.append(" desc limit ");
        }
        sql.append(request.getStart()).append(",").append(request.getPageSize());

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TableDTO returnDTO = new TableDTO();

        try {
            conn = DBUtil.getConn();
            if (conn == null) {
                System.out.println("数据库连接失败!");
                return null;
            }
            updateSumAndAverage(conn);
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            //查询结果
            returnDTO.setData(fillData(rs, request));
            //查询条数
            sql.setLength(0);
            sql.append("select count(*) from grade ");
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                int count = rs.getInt(1);
                returnDTO.setTotalCount(count);
            }
            return returnDTO;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return null;
    }

    @Override
    public TableDTO researchStudents(StudentRequest request, String key) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from grade where ").append(key).append(" like '%").
                append(request.getResearchKey().trim()).append("%' ").
                append(" order by sum desc");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TableDTO returnDTO = new TableDTO();
        try {
            conn = DBUtil.getConn();
            if (conn == null) {
                System.out.println("数据库连接失败!");
                return null;
            }
            updateSumAndAverage(conn);
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            //查询结果
            returnDTO.setData(fillData(rs, request));
            return returnDTO;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return null;
    }

    @Override
    public boolean add(StudentDO studentDO) {
        StringBuilder addSql = new StringBuilder();
        addSql.append(" insert into grade(stu_name,id,class,chinese,math,english) ").
                append(" values(?,?,?,?,?,?)");
        String verifySql = "select count(*) from grade where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConn();
            if (connection != null) {
                if (!verify(studentDO.getId(), verifySql, connection)) {
                    return false;
                } else {
                    preparedStatement = connection.prepareStatement(addSql.toString());
                    preparedStatement.setString(1, studentDO.getName());
                    preparedStatement.setString(2, studentDO.getId());
                    preparedStatement.setString(3, studentDO.getClassName());
                    preparedStatement.setInt(4, studentDO.getChinese());
                    preparedStatement.setInt(5, studentDO.getMath());
                    preparedStatement.setInt(6, studentDO.getEnglish());
                    return preparedStatement.executeUpdate() == 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePs(preparedStatement);
            DBUtil.closeConn(connection);
        }
        return false;
    }

    @Override
    public StudentDO getById(String selectedStudentId) {
        StringBuilder sql = new StringBuilder("select * from grade where id = ? ");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StudentDO studentDO = new StudentDO();
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, selectedStudentId);
            rs = ps.executeQuery();
            while (rs.next()) {
                //处理查出的每一条记录
                String name = rs.getString(DBUtil.NAME);
                String id = rs.getString(DBUtil.ID);
                String className = rs.getString(DBUtil.CLASS);
                int chinese = rs.getInt(DBUtil.CHINESE);
                int math = rs.getInt(DBUtil.MATH);
                int english = rs.getInt(DBUtil.ENGLISH);
                studentDO.setId(id);
                studentDO.setName(name);
                studentDO.setClassName(className);
                studentDO.setChinese(chinese);
                studentDO.setMath(math);
                studentDO.setEnglish(english);
            }
            return studentDO;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return null;
    }

    @Override
    public boolean update(StudentDO studentDO) {
        StringBuilder sql = new StringBuilder();
        String verifySql = "select count(*) from grade where id=?";
        sql.append("update grade set stu_name=?, id=?, class=?, chinese=?, ").
                append("math=?, english=? where id=?");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConn();
            if (connection != null) {
                if (!verify(studentDO.getId(), verifySql, connection) &&
                        !studentDO.getId().equals(studentDO.getSearchKeyId())) {
                    return false;
                } else {
                    preparedStatement = connection.prepareStatement(sql.toString());
                    preparedStatement.setString(1, studentDO.getName());
                    preparedStatement.setString(2, studentDO.getId());
                    preparedStatement.setString(3, studentDO.getClassName());
                    preparedStatement.setInt(4, studentDO.getChinese());
                    preparedStatement.setInt(5, studentDO.getMath());
                    preparedStatement.setInt(6, studentDO.getEnglish());
                    preparedStatement.setString(7, studentDO.getSearchKeyId());
                    return preparedStatement.executeUpdate() == 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePs(preparedStatement);
            DBUtil.closeConn(connection);
        }
        return false;
    }

    @Override
    public boolean delete(String[] selectedStudentIds) {
        int length = selectedStudentIds.length;
        StringBuilder sql = new StringBuilder();
        sql.append("delete from grade where id in ( ");
        for (int i = 0; i < length; i++) {
            if (i == (length - 1)) {
                sql.append(" ?) ");
            } else {
                sql.append(" ?, ");
            }
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConn();
            preparedStatement = connection.prepareStatement(sql.toString());
            //设置SQL语句的参数,从1开始
            for (int i = 0; i < length; i++) {
                preparedStatement.setString(i + 1, selectedStudentIds[i]);
            }
            return preparedStatement.executeUpdate() == length;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePs(preparedStatement);
            DBUtil.closeConn(connection);
        }
        return false;
    }

    @Override
    public void analyse(StatisticsView statisticsView) {
        setMaxScore(statisticsView);
        int size = getClassNum();
        int[][] personNum = new int[size][8];
        setPersonNumFirst(personNum);
        setPersonNumSecond(personNum);
        double[][] analysis = getClassAnalysis(personNum, size);
        setClassAnalysis(statisticsView, analysis);
    }

    //设置班级分析
    private void setClassAnalysis(StatisticsView statisticsView, double[][] analysis) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        statisticsView.setStatisticsAreaText("\n\n班级情况分析:");
        for (double[] d : analysis) {
            StringBuilder text = new StringBuilder();
            text.append((int) d[0]).append("班:\n").append("语文优秀率:").
                    append(decimalFormat.format(d[1])).append("\t  数学优秀率:").
                    append(decimalFormat.format(d[3])).append("\t  英语优秀率:").
                    append(decimalFormat.format(d[5])).append("\n语文不及格率:").
                    append(decimalFormat.format(d[2])).append("\t数学不及格率:").
                    append(decimalFormat.format(d[4])).append("\t英语不及格率:").
                    append(decimalFormat.format(d[6]));
            //System.out.println(text);
            statisticsView.setStatisticsAreaText(text.toString());
            statisticsView.setScrollBarValue();
        }
    }

    //获取班级分析
    private double[][] getClassAnalysis(int[][] personNum, int size) {
        double[][] analysis = new double[size][7];
        int i = 0;
        for (double[] n : analysis) {
            n[0] = personNum[i][0];
            n[1] = personNum[i][2] * 1.0 / personNum[i][1];
            n[2] = personNum[i][3] * 1.0 / personNum[i][1];
            n[3] = personNum[i][4] * 1.0 / personNum[i][1];
            n[4] = personNum[i][5] * 1.0 / personNum[i][1];
            n[5] = personNum[i][6] * 1.0 / personNum[i][1];
            n[6] = personNum[i][7] * 1.0 / personNum[i][1];
            i++;
        }
        System.out.println(Arrays.deepToString(analysis));
        return analysis;
    }

    //设置人数数组
    private void setPersonNumSecond(int[][] personNum) {
        String[] sql = {
                "select class,count(*) from grade where chinese>=90 group by class order by class"
                , "select class,count(*) from grade where chinese<60 group by class order by class"
                , "select class,count(*) from grade where math>=90 group by class order by class"
                , "select class,count(*) from grade where math<60 group by class order by class"
                , "select class,count(*) from grade where english>=90 group by class order by class"
                , "select class,count(*) from grade where english<60 group by class order by class"

        };

        Connection conn = null;
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            conn = DBUtil.getConn();
            for (int j = 0; j < sql.length; j++) {
                try {
                    ps = conn.prepareStatement(sql[j]);
                    rs = ps.executeQuery();
                    int i = 0;
                    while (rs.next()) {
                        if (personNum[i][0] == Integer.parseInt(rs.getString(DBUtil.CLASS))) {
                            personNum[i][2 + j] = rs.getInt(2);
                        } else {
                            personNum[i][2 + j] = 0;
                            rs.previous();
                        }
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    DBUtil.closeRs(rs);
                    DBUtil.closePs(ps);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
        }
    }

    //设置第一名数组
    private void setPersonNumFirst(int[][] personNum) {
        String sql = "select class,count(*) from grade group by class order by class";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                personNum[i][0] = Integer.parseInt(rs.getString(DBUtil.CLASS));
                personNum[i][1] = rs.getInt(2);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
    }

    //获取班级数量
    private int getClassNum() {
        String sql = "select count(distinct class) from grade";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeRs(rs);
            DBUtil.closePs(ps);
            DBUtil.closeConn(conn);
        }
        return 1;
    }

    //设置最高分
    private void setMaxScore(StatisticsView statisticsView) {
        String[][] sql = {
                {"语文最高分:", "select chinese, stu_name, id, class from grade where chinese in (select max(chinese) from grade)"}
                , {"数学最高分:", "select math, stu_name, id, class from grade where math in (select max(math) from grade)"}
                , {"英语最高分:", "select english, stu_name, id, class from grade where english in (select max(english) from grade)"}
                , {"总分最高分", "select sum, stu_name, id, class from grade where sum in (select max(sum) from grade)"}
        };
        Connection conn = null;
        statisticsView.setStatisticsAreaText("最高分情况分析:");
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            conn = DBUtil.getConn();
            for (String[] s : sql) {
                statisticsView.setStatisticsAreaText(s[0]);
                try {
                    ps = conn.prepareStatement(s[1]);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int maxScore = rs.getInt(1);
                        String name = rs.getString(DBUtil.NAME);
                        String className = rs.getString(DBUtil.CLASS);
                        String id = rs.getString(DBUtil.ID);
                        statisticsView.setStatisticsAreaText("分数:" + maxScore
                                + " 姓名:" + name + " 班级:" + className + " 学号:"
                                + id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    DBUtil.closeRs(rs);
                    DBUtil.closePs(ps);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn);
        }
    }

    //从结果集中提取表格数据
    private Vector<Vector<Object>> fillData(ResultSet rs, StudentRequest request) throws SQLException {
        Vector<Vector<Object>> data = new Vector<>();
        int count = 1;
        int no;
        while (rs.next()) {
            //处理查出的每一条记录
            Vector<Object> oneRecord = new Vector<>();
            String name = rs.getString(DBUtil.NAME);
            String id = rs.getString(DBUtil.ID);
            String className = rs.getString(DBUtil.CLASS);
            int chinese = rs.getInt(DBUtil.CHINESE);
            int math = rs.getInt(DBUtil.MATH);
            int english = rs.getInt(DBUtil.ENGLISH);
            int sum = rs.getInt(DBUtil.SUM);
            double average = rs.getDouble(DBUtil.AVERAGE);
            DecimalFormat df = new DecimalFormat("#0.0");
            no = (request.getPageNow() - 1) * request.getPageSize() + count;
            oneRecord.addElement(no);
            oneRecord.addElement(name);
            oneRecord.addElement(id);
            oneRecord.addElement(className);
            oneRecord.addElement(chinese);
            oneRecord.addElement(math);
            oneRecord.addElement(english);
            oneRecord.addElement(sum);
            oneRecord.addElement(df.format(average));
            data.addElement(oneRecord);
            count++;
        }
        return data;
    }

    //更新总分和平均分
    private void updateSumAndAverage(Connection connection) {
        PreparedStatement ps = null;
        String sql = "update grade set sum=english+grade.chinese+grade.math,average=sum/3.0";
        try {
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePs(ps);
        }
    }

    //检查学号是否已存在
    private boolean verify(String id, String verifySql, Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(verifySql);
            ps.setString(1, id);
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
}
