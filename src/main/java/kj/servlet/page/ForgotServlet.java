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
@WebServlet(name = "forgot", value = "/forgot")
public class ForgotServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        HttpSession session = req.getSession();
        if(session.getAttribute("codeErrorMsg") != null) {
            context.setVariable("failure", true);
            session.removeAttribute("codeErrorMsg");
        }
        ThymeleafUtil.process("page/forgot.html", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("forgot")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String code = req.getParameter("code");
        log.info(username + "使用CODE" + code + "重置密码");
        if(userService.authorizeCode(code)) {
            log.info("验证码验证成功");
            int row = userService.resetPassword(username);
            log.info(row + "行受影响");
            resp.sendRedirect("login");
        } else {
            log.info("验证码错误");
            session.setAttribute("codeErrorMsg", new Object());
            this.doGet(req, resp);
        }
    }
}
