package kj.service;

import kj.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {


    List<Student> getStudentList();

    Student getStudentBySid(String sid);

    Map<String, Long> getStudentBorrowCountMap();

    int addStudent(String name, String sex, String grade);

    String[] getAllGrade();

    int removeStudent(String sid);
}
