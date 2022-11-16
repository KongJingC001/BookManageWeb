package kj.servlet.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kj.entity.Borrow;
import kj.service.BorrowService;
import kj.service.impl.BorrowServiceImpl;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
@WebServlet(name = "deleteBorrow", value = "/deleteBorrow")
public class DeleteBorrowServlet extends HttpServlet {

    BorrowService borrowService;

    @Override
    public void init() throws ServletException {
        borrowService = new BorrowServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getHeader("Referer").contains("borrow")) {
            log.warning("收到一个未知的请求，拒绝");
            resp.sendRedirect("404");
            return;
        }
        String id = req.getParameter("id");
        log.info("学生归还书籍");
        int row = borrowService.removeBorrow(id);
        log.info(row + "行受影响");
        // 删除完成后，返回到borrow
        resp.sendRedirect("borrow");
    }

}
