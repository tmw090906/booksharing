package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import pers.tmw.booksharing.pojo.Complan;

import java.util.List;

public interface ComplanMapper {
    int deleteByPrimaryKey(Long complanId);

    int insert(Complan record);

    int insertSelective(Complan record);

    Complan selectByPrimaryKey(Long complanId);

    int updateByPrimaryKeySelective(Complan record);

    int updateByPrimaryKey(Complan record);

    List<Complan> getListByUserIdAndStatus(@Param("userId") Long userId,@Param("status")  Short status);

    List<Complan> getListByStatus(@Param("status")  Short status);
}