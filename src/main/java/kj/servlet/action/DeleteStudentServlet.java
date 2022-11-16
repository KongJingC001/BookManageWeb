package kj.servlet.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kj.service.StudentService;
import kj.service.impl.StudentServiceImpl;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
@WebServlet(name = "deleteStudent", value = "/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {

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
        String sid = req.getParameter("sid");
        log.info("学生注销信息");
        int row = studentService.removeStudent(sid);
        log.info(row + "行受影响");
        // 删除完成后，返回到borrow
        resp.sendRedirect("student");
    }
}
