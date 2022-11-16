package kj.dao;

import kj.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {


    @Select("select * from book")
    List<Book> getBookList();

    @Select("select * from book where bid = #{bid}")
    Book getBookByBid(String bid);

    @Delete("delete from book where bid = #{bid}")
    int removeBook(String bid);

    @Insert("insert into book(title, `desc`, price, status) values (#{title}, #{desc}, #{price}, #{status})")
    int addBook(@Param("title") String title, @Param("desc") String desc, @Param("price") double price, @Param("status") String status);

    @Update("update book set status = #{status} where bid = #{bid}")
    int setStatus(@Param("status") String status, @Param("bid") String bid);


}
