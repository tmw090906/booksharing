package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import pers.tmw.booksharing.pojo.Replace;

import java.util.List;

public interface ReplaceMapper {
    int deleteByPrimaryKey(Long replaceId);

    int insert(Replace record);

    int insertSelective(Replace record);

    Replace selectByPrimaryKey(Long replaceId);

    int updateByPrimaryKeySelective(Replace record);

    int updateByPrimaryKey(Replace record);

    List<Replace> getListByUserIdStatus(@Param(value = "userId") Long userId,@Param(value = "status") Short status);
}