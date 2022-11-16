package kj.dao;

import kj.entity.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BorrowMapper {


    @Select("select * from borrow where id = #{id}")
    Borrow getBorrowById(String id);

    @Select("SELECT * FROM borrow")
    List<Borrow> getBorrowList();

    @Delete("delete from borrow where id = #{id}")
    int removeBorrow(String id);

    @Select("SELECT * from borrow WHERE sid = #{sid}")
    List<Borrow> getBorrowBySid(String sid);

    @Insert("insert into borrow(bid, sid, time) values (#{bid}, #{sid}, #{time})")
    int addBorrow(@Param("bid") String bid, @Param("sid") String sid, @Param("time") Date time);
}
