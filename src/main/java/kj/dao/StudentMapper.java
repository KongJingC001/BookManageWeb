package kj.dao;

import kj.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {

    @Select("select * from student")
    List<Student> getStudentList();

    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(String sid);

    @Delete("delete from student where sid = #{sid}")
    int removeStudent(String sid);

    @Insert("insert into student(name, sex, grade) values(#{name}, #{sex}, #{grade})")
    int addStudent(@Param("name") String name, @Param("sex") String sex, @Param("grade") String grade);
}
