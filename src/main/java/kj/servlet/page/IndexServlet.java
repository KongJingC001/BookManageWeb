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
import kj.service.UserService;
import kj.service.impl.BookServiceImpl;
import kj.service.impl.BorrowServiceImpl;
import kj.service.impl.StudentServiceImpl;
import kj.service.impl.UserServiceImpl;
import kj.util.ThymeleafUtil;
import lombok.extern.java.Log;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Log
@WebServlet(name = "index", value = "/index")
public class IndexServlet extends HttpServlet {

    BookService bookService;

    StudentService studentService;

    BorrowService borrowService;

    @Override
    public void init() throws ServletException {
        bookService = new BookServiceImpl();
        studentService = new StudentServiceImpl();
        borrowService = new BorrowServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("用户访问index.html");
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("group", user.getGroup());
        // 设置书籍列表数据
        context.setVariable("bookList", bookService.getBookList());
        context.setVariable("bookCount", bookService.getBookList().size());
        context.setVariable("bookTotalPrice", bookService.getBookTotalPrice());
        // 涉及学生信息
        context.setVariable("studentCount", studentService.getStudentList().size());
        // 获取借阅信息
        context.setVariable("borrowCount", borrowService.getBorrowList().size());
        ThymeleafUtil.process("page/index.html", context, resp);
    }

}
