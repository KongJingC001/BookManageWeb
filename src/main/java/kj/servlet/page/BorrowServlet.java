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
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "borrow", value = "/borrow")
public class BorrowServlet extends HttpServlet {

    BorrowService borrowService;

    StudentService studentService;

    BookService bookService;

    @Override
    public void init() throws ServletException {
        borrowService = new BorrowServiceImpl();
        studentService = new StudentServiceImpl();
        bookService = new BookServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("group", user.getGroup());
        context.setVariable("borrowList", borrowService.getBorrowList());
        context.setVariable("borrowStudentMap", borrowService.getBorrowStudentMap());
        context.setVariable("borrowBookMap", borrowService.getBorrowBookMap());
        context.setVariable("hasStudent", !studentService.getStudentList().isEmpty());
        context.setVariable("hasBook", !bookService.getBookList().stream().filter(book -> "在馆可借阅".equals(book.getStatus())).collect(Collectors.toList()).isEmpty());
        ThymeleafUtil.process("page/borrow.html", context, resp);
    }
}
