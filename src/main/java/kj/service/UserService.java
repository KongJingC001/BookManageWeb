package kj.service;

import jakarta.servlet.http.HttpSession;
import kj.entity.User;

import java.util.List;

public interface UserService {

    boolean authorize(String username, String password, HttpSession session);

    List<User> getUserList();

    int addUser(String nickname, String username, String password, String group);

    boolean authorizeCode(String code);

    int resetPassword(String username);

    boolean hasUser(String username);
}
