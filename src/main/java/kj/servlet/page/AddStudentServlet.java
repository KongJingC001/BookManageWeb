package kj.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kj.entity.User;
import kj.service.StudentService;
import kj.service.impl.StudentServiceImpl;
import kj.util.ThymeleafUtil;
import lombok.extern.java.Log;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Log
@WebServlet(name = "addStudent", value = "/addStudent")
public class AddStudentServlet extends HttpServlet {

    StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("student")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("访问add-student.html");
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("group", user.getGroup());
        context.setVariable("years", studentService.getAllGrade());
        ThymeleafUtil.process("page/add-student.html", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("addStudent")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("用户提交学生信息");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String grade = req.getParameter("grade");
        int row = studentService.addStudent(name, sex, grade);
        log.info(row + "行受影响");
        resp.sendRedirect("student");
    }

}
