package kj.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kj.entity.Book;
import kj.entity.User;
import kj.service.BookService;
import kj.service.impl.BookServiceImpl;
import kj.util.ThymeleafUtil;
import lombok.extern.java.Log;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Log
@WebServlet(name = "addBook", value = "/addBook")
public class AddBookServlet extends HttpServlet {

    BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("index")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("添加一条书籍信息");
        Context context = new Context();
        User user = (User) req.getSession().getAttribute("user");
        context.setVariable("nickname", user.getNickname());
        context.setVariable("group", user.getGroup());
        ThymeleafUtil.process("page/add-book.html", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("addBook")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        log.info("用户提交书籍信息");
        String bookName = req.getParameter("bookName");
        String bookDesc = req.getParameter("bookDesc");
        double bookPrice = Double.parseDouble(req.getParameter("bookPrice"));
        String bookStatus = req.getParameter("bookStatus");
        int row = bookService.addBook(bookName, bookDesc, bookPrice, bookStatus);
        log.info(row + "行受影响");
        resp.sendRedirect("index");
    }
}
