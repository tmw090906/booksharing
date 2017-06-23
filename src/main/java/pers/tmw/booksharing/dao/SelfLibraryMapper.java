package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import pers.tmw.booksharing.pojo.SelfLibrary;

import java.util.List;
import java.util.Set;

public interface SelfLibraryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SelfLibrary record);

    int insertSelective(SelfLibrary record);

    SelfLibrary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SelfLibrary record);

    int updateByPrimaryKey(SelfLibrary record);

    List<SelfLibrary> selectSelfLibraryList(@Param("userId") Long userId, @Param("status")Short status);

    SelfLibrary selectSelfLibraryIdUserId(@Param("userId") Long userId, @Param("id")Long id);

    SelfLibrary selectLibraryByUserIdBookId(@Param("userId") Long userId, @Param("bookId")Long bookId);

    List<Long> getUserIdByBookId(Long bookId);

    List<Long> getBookIdByUserId(Long userId);

    Set<Long> getBookIdBySelfUserId(Long userId);

    List<Long> getUserIdByAllBookId(Long bookId);
}