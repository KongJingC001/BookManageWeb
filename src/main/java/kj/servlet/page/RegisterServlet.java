package kj.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kj.service.UserService;
import kj.service.impl.UserServiceImpl;
import kj.util.ThymeleafUtil;
import lombok.extern.java.Log;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Log
@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        HttpSession session = req.getSession();
        if(session.getAttribute("codeError") != null) {
            context.setVariable("codeError", true);
            session.removeAttribute("codeError");
        }
        if(session.getAttribute("hasUserError") != null) {
            context.setVariable("hasUserError", true);
            session.removeAttribute("hasUserError");
        }
        ThymeleafUtil.process("page/register.html", context, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("register")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("用户提交表单");
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String code = req.getParameter("code");
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");
        if(userService.hasUser(username)) {
            log.info(username + "用户已存在");
            session.setAttribute("hasUserError", new Object());
            this.doGet(req, resp);
            return;
        }
        if(userService.authorizeCode(code)) {
            log.info("验证码验证成功");
            int row = userService.addUser(nickname, username, password, "admin");
            // 用户刚注册，为用户填入username方便登录
            session.setAttribute("registerUsername", username);
            session.setAttribute("registerPassword", password);
            log.info(row + "行受影响");
            resp.sendRedirect("login");
        } else {
            log.info("验证码错误");
            session.setAttribute("codeError", new Object());
            this.doGet(req, resp);
        }
    }
}
