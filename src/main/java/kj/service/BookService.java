package kj.service;

import kj.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getBookList();

    Book getBookByBid(String bid);

    String getBookTotalPrice();

    int removeBook(String bid);

    int addBook(String title, String desc, double price, String status);

    int isBookBorrowed(String sid);

    int returnBorrowedBook(String sid);

}
