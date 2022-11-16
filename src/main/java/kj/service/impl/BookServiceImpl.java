package kj.service.impl;

import kj.dao.BookMapper;
import kj.entity.Book;
import kj.service.BookService;
import kj.util.SqlUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BookServiceImpl implements BookService {

    @Override
    public List<Book> getBookList() {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.getBookList();
        }
    }

    @Override
    public Book getBookByBid(String bid) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.getBookByBid(bid);
        }
    }

    @Override
    public String getBookTotalPrice() {
        List<Book> bookList = this.getBookList();
        double totalPrice = 0;
        for (Book book : bookList) {
            totalPrice += book.getPrice();
        }
        // 保留三位有效数字
        String t = String.valueOf(totalPrice);
        if(t.indexOf(".") + 2 < t.length() - 1) {
            t = t.substring(0, t.indexOf(".") + 3);
        }
        return t;
    }

    @Override
    public int removeBook(String bid) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);

            return bookMapper.removeBook(bid);
        }
    }

    @Override
    public int addBook(String title, String desc, double price, String status) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.addBook(title, desc, price, status);
        }
    }

    @Override
    public int isBookBorrowed(String bid) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.setStatus("借阅中", bid);
        }
    }

    @Override
    public int returnBorrowedBook(String bid) {
        try (SqlSession sqlSession = SqlUtil.getSqlSession()) {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            return bookMapper.setStatus("在馆可借阅", bid);
        }
    }


}
