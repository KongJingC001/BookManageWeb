package kj.service.impl;

import jakarta.servlet.http.HttpSession;
import kj.dao.UserMapper;
import kj.entity.User;
import kj.service.UserService;
import kj.util.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSession;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Log
public class UserServiceImpl implements UserService {


    @Override
    public boolean authorize(String username, String password, HttpSession session) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUser(username, password);
            if(user != null) {
                session.setAttribute("user", user);
                return true;
            }
            return false;
        }
    }

    @Override
    public List<User> getUserList() {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserList();
        }
    }

    @Override
    public int addUser(String nickname, String username, String password, String group) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.addUser(nickname, username, password, group);
        }
    }

    @Override
    public boolean authorizeCode(String code) {
        Calendar calendar = Calendar.getInstance();
        String realCode = calendar.get(Calendar.YEAR) + "" +
                (calendar.get(Calendar.MONTH) + 1)+ "" +
                calendar.get(Calendar.DATE);
        log.info("CODE: " + realCode);
        log.info("INPUT CODE" +code);
        return code.equals(realCode);
    }

    @Override
    public int resetPassword(String username) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.resetPassword(username);
        }
    }

    @Override
    public boolean hasUser(String username) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.searchUser(username) != null;
        }
    }
}
