package kj.servlet.sign;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import kj.entity.User;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        log.info("用户" + ((User)session.getAttribute("user")).getNickname() + "退出登录");
        // 用户退出登录
        log.info("无效化session，删除cookie，并重定向到登录页面");
        session.invalidate();
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if("username".equals(cookie.getName()) || "password".equals(cookie.getName())) {
                log.info("捕获到用户信息cookie: " + cookie.getName() + " - " + cookie.getValue());
                // 设置一个新的同domain和同Path的cookie，并设置maxAge为0，即可删除
                Cookie c = new Cookie(cookie.getName(), "");
                c.setMaxAge(0);
                c.setPath("/");
                resp.addCookie(c);
            }
        }
        resp.sendRedirect("login");
    }

}
