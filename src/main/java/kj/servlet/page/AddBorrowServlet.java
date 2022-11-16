package kj.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kj.entity.User;
import kj.service.BookService;
import kj.service.BorrowService;
import kj.service.StudentService;
import kj.service.impl.BookServiceImpl;
import kj.service.impl.BorrowServiceImpl;
import kj.service.impl.StudentServiceImpl;
import kj.util.ThymeleafUtil;
import lombok.extern.java.Log;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Log
@WebServlet(name = "addBorrow", value = "/addBorrow")
public class AddBorrowServlet extends HttpServlet {

    BorrowService borrowService;

    BookService bookService;

    StudentService studentService;

    @Override
    public void init() throws ServletException {
        borrowService = new BorrowServiceImpl();
        bookService = new BookServiceImpl();
        studentService = new StudentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("borrow")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("访问add-borrow.html");
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("group", user.getGroup());
        context.setVariable("bookList", bookService.getBookList());
        context.setVariable("studentList", studentService.getStudentList());
        ThymeleafUtil.process("page/add-borrow.html", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("addBorrow")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("用户提交借阅信息");
        String bid = req.getParameter("book");
        String sid = req.getParameter("student");
        int row = borrowService.addBorrow(bid, sid);
        log.info(row + "行受影响");
        resp.sendRedirect("borrow");
    }
}
