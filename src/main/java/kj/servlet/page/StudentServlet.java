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
@WebServlet(name = "student", value = "/student")
public class StudentServlet extends HttpServlet {

    StudentService studentService;


    @Override
    public void init() throws ServletException {
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("group", user.getGroup());
        context.setVariable("studentList", studentService.getStudentList());
        context.setVariable("studentBorrowCountMap", studentService.getStudentBorrowCountMap());
        ThymeleafUtil.process("page/student.html", context, resp);
    }

}

