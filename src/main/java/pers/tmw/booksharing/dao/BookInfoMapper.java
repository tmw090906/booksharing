package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import pers.tmw.booksharing.pojo.BookInfo;

import java.util.List;

public interface BookInfoMapper {
    int deleteByPrimaryKey(Long bookId);

    int insert(BookInfo record);

    int insertSelective(BookInfo record);

    BookInfo selectByPrimaryKey(Long bookId);

    int updateByPrimaryKeySelective(BookInfo record);

    int updateByPrimaryKey(BookInfo record);

    List<BookInfo> selectByNameAndCategoryIds(@Param("textSearch") String textSearch, @Param("categoryIdList")List<Long> categoryIdList);

    List<BookInfo> selectList();

    String getBookNameByBookId(Long bookId);

    List<BookInfo> getBookListByBookIds(@Param("bookIdList")List<Long> bookIdList);

    int updateCategoryForCategoryDelete(@Param("oldCategoryId")Long oldCategoryId,@Param("newCategoryId")Long newCategoryId);
}