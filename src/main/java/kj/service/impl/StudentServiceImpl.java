package kj.service.impl;

import kj.dao.BorrowMapper;
import kj.dao.StudentMapper;
import kj.entity.Student;
import kj.service.StudentService;
import kj.util.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

@Log
public class StudentServiceImpl implements StudentService {

    @Override
    public List<Student> getStudentList() {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.getStudentList();
        }
    }

    @Override
    public Student getStudentBySid(String sid) {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.getStudentBySid(sid);
        }
    }

    @Override
    public Map<String, Long> getStudentBorrowCountMap() {
        long start = System.currentTimeMillis();
        Map<String, Long> map = new HashMap<>();
        List<Student> studentList = this.getStudentList();
        try(SqlSession sqlSession =  SqlUtil.getSqlSession()) {
            BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
            studentList.forEach(student -> map.put(student.getSid(), (long) borrowMapper.getBorrowBySid(student.getSid()).size()));
            long end = System.currentTimeMillis();
            log.info("获取map时长 " + (end - start) + "ms");
            return map;
        }
    }

    @Override
    public int addStudent(String name, String sex, String grade) {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.addStudent(name, sex, grade);
        }
    }

    @Override
    public String[] getAllGrade() {
        final int range = 15;
        String[] years = new String[range];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < years.length; i++) {
            years[i] = String.valueOf(currentYear - range + i + 1);
        }
        return years;
    }

    @Override
    public int removeStudent(String sid) {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            return studentMapper.removeStudent(sid);
        }
    }
}
