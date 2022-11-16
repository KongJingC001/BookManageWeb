package kj.service;

import kj.entity.Book;
import kj.entity.Borrow;
import kj.entity.Student;

import java.util.List;
import java.util.Map;

public interface BorrowService {


    Borrow getBorrowById(String id);

    List<Borrow> getBorrowList();

    int removeBorrow(String id);

    int addBorrow(String bid, String sid);

    Map<String, String> getBorrowStudentMap();

    Map<String, String> getBorrowBookMap();


}
