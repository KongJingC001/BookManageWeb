package kj.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import kj.entity.User;
import kj.service.UserService;
import kj.service.impl.UserServiceImpl;
import kj.util.ThymeleafUtil;
import lombok.extern.java.Log;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Log
@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("试图访问login.html");
        HttpSession session = req.getSession();
        Context context = new Context();
        if(session.getAttribute("login-failure") != null) {
            log.info("用户登录失败，回到登录页面");
            context.setVariable("failure", true);
            // 清除session中的失败信息
            session.removeAttribute("login-failure");
        }
        // 在浏览器中获取cookie，用于remember-me选项
        Cookie[] cookies = req.getCookies();
        boolean f1 = false;
        boolean f2 = false;
        Cookie uCookie = null, pCookie = null;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if("username".equals(cookie.getName())) {
                    log.info("cookie中存在用户名信息");
                    f1 = true;
                    uCookie = cookie;
                } else if ("password".equals(cookie.getName())) {
                    log.info("cookie中存在密码信息");
                    f2 = true;
                    pCookie = cookie;
                }
            }
        }
        // 自动登录实现
        if(f1 && f2) {
            log.info("cookie中存在用户信息");
            if(userService.authorize(uCookie.getValue(), pCookie.getValue(), session)) {
                log.info("cookie中的用户验证成功");
                // 更新cookie生命周期
                uCookie.setMaxAge(3600 * 7);
                pCookie.setMaxAge(3600 * 7);
                resp.sendRedirect("index");
            }
        }
        // 为刚注册的用户自动填入username, password 方便登录
        String registerUsername = (String) session.getAttribute("registerUsername");
        String registerPassword = (String) session.getAttribute("registerPassword");
        if(registerUsername != null && registerPassword != null) {
            context.setVariable("registered", true);
            context.setVariable("registerUsername", registerUsername);
            context.setVariable("registerPassword", registerPassword);
            // 移除session中的这两个属性
            session.removeAttribute("registerUsername");
            session.removeAttribute("registerPassword");
        } else {
            context.setVariable("registered", false);
        }
        ThymeleafUtil.process("page/login.html", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("login")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("用户发起登录请求，验证登录信息");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember-me");
        HttpSession session = req.getSession();
        if(userService.authorize(username, password, session)) {
            log.info("用户验证成功");
            if("on".equals(remember)) {
                Cookie c1 = new Cookie("username", username);
                c1.setMaxAge(3600 * 7);
                c1.setPath("/");
                Cookie c2 = new Cookie("password", password);
                c2.setPath("/");
                c2.setMaxAge(3600 * 7);
                resp.addCookie(c1);
                resp.addCookie(c2);
                log.info("用户勾选remember-me，添加cookie信息");
            }
            resp.sendRedirect("index");
        } else {
            log.info("用户验证失败");
            session.setAttribute("login-failure", new Object());
            this.doGet(req, resp);
        }
    }

}
