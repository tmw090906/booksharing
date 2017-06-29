package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import pers.tmw.booksharing.pojo.Apply;

import java.util.List;

public interface ApplyMapper {
    int deleteByPrimaryKey(Long applyId);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Long applyId);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);

    List<Apply> getApplyListByAppliedUserIdStatus(@Param("appliedUserId") Long appliedUserId);

    List<Apply> getApplyListByApplyUserIdStatus(@Param("applyUserId") Long applyUserId);

    Apply selectByApplyIdAndAppliedUserId(@Param("applyId") Long applyId,@Param("appliedUserId") Long appliedUserId);

    int repeatApplyTest(@Param("appliedUserId") Long appliedUserId,@Param("appliedBookId") Long appliedBookId,
                        @Param("applyUserId") Long applyUserId,@Param("applyBookId") Long applyBookId);

}