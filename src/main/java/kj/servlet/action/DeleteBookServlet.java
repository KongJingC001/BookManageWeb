package kj.servlet.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kj.service.BookService;
import kj.service.impl.BookServiceImpl;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
@WebServlet(name = "deleteBook", value = "/deleteBook")
public class DeleteBookServlet extends HttpServlet {

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
        String bid = req.getParameter("bid");
        log.info("用户删除书籍 #" + bid);
        int row = bookService.removeBook(bid);
        log.info(row + "行受影响");
        // 删除完成后，应该继续回到index.html
        resp.sendRedirect("index");
    }
}
