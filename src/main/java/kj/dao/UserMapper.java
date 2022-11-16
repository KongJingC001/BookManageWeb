package kj.dao;

import kj.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Select("select * from user where username=#{username} and `password`=#{password}")
    User getUser(@Param("username") String username, @Param("password") String password);

    @Select("select * from user")
    List<User> getUserList();

    @Select("select * from user where username = #{username}")
    User searchUser(String username);

    @Insert("insert into user(nickname, username, `password`, `group`) values (#{nickname}, #{username}, #{password}, #{group})")
    int addUser(@Param("nickname") String nickname, @Param("username") String username, @Param("password") String password, @Param("group") String group);

    @Update("update user set password = 123456 where username = #{username}")
    int resetPassword(String username);
}
