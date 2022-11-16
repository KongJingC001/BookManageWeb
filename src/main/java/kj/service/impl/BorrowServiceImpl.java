package kj.service.impl;

import kj.dao.BorrowMapper;
import kj.entity.Book;
import kj.entity.Borrow;
import kj.entity.Student;
import kj.service.BookService;
import kj.service.BorrowService;
import kj.service.StudentService;
import kj.util.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
public class BorrowServiceImpl implements BorrowService {

    @Override
    public Borrow getBorrowById(String id) {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
            return borrowMapper.getBorrowById(id);
        }
    }

    @Override
    public List<Borrow> getBorrowList() {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
            return borrowMapper.getBorrowList();
        }
    }

    @Override
    public int removeBorrow(String id) {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
            BookService bookService = new BookServiceImpl();
            bookService.returnBorrowedBook(this.getBorrowById(id).getBid());
            return borrowMapper.removeBorrow(id);
        }
    }

    @Override
    public int addBorrow(String bid, String sid) {
        try(SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BorrowMapper borrowMapper = sqlSession.getMapper(BorrowMapper.class);
            BookService bookService = new BookServiceImpl();
            bookService.isBookBorrowed(bid);
            return borrowMapper.addBorrow(bid, sid, new Date());
        }
    }


    @Override
    public Map<String, String> getBorrowStudentMap() {
        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        List<Borrow> borrowList = this.getBorrowList();
        StudentService studentService = new StudentServiceImpl();
        for (Borrow borrow : borrowList) {
            String name = studentService.getStudentBySid(borrow.getSid()).getName();
            map.put(borrow.getId(), name);
        }
        long end = System.currentTimeMillis();
        log.info("获取map时长 " + (end - start) + "ms");
        return map;
    }

    @Override
    public Map<String, String> getBorrowBookMap() {
        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        List<Borrow> borrowList = this.getBorrowList();
        BookService bookService = new BookServiceImpl();
        for (Borrow borrow : borrowList) {
            String title = bookService.getBookByBid(borrow.getBid()).getTitle();
            map.put(borrow.getId(), title);
        }
        long end = System.currentTimeMillis();
        log.info("获取map时长 " + (end - start) + "ms");
        return map;
    }

}
