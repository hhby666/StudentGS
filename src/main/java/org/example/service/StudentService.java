package org.example.service;

import org.example.entity.StudentDO;
import org.example.req.StudentRequest;
import org.example.res.TableDTO;
import org.example.view.StatisticsView;

/**
 * 用于学生的功能
 *
 * @author by wlq on 2021/3/8
 * @version Java1.9 IDEA2020.2
 */
public interface StudentService {

    /**
     * 学生成绩按不同索引排序
     *
     * @param request 查询工具类
     * @param key     排序索引
     * @return 表格的数据
     */
    TableDTO sortStudents(StudentRequest request, String key);

    /**
     * 学生成绩按不同索引搜索关键字
     *
     * @param request 查询工具类
     * @param key     排序索引
     * @return 表格的数据
     */
    TableDTO researchStudents(StudentRequest request, String key);

    /**
     * 添加学生到数据库
     *
     * @param studentDO 学生实体
     * @return 是否添加成功
     */
    boolean add(StudentDO studentDO);

    /**
     * 根据ID获得学生的成绩信息
     *
     * @param selectedStudentId 学生ID
     * @return 学生成绩信息
     */
    StudentDO getById(String selectedStudentId);

    /**
     * 修改学生成绩信息
     *
     * @param studentDO 包含要修改的学生ID,及修改的内容
     * @return 是否修改成功
     */
    boolean update(StudentDO studentDO);

    /**
     * 删除选中的学生
     *
     * @param selectedStudentIds 要删除的学生ID的集合
     * @return 是否删除成功
     */
    boolean delete(String[] selectedStudentIds);

    /**
     * 分析学生成绩的最高分和班级优秀率不及格率的情况.
     *
     * @param statisticsView 分析界面
     */
    void analyse(StatisticsView statisticsView);
}
