package pers.tmw.booksharing.dao;

import org.apache.ibatis.annotations.Param;
import pers.tmw.booksharing.pojo.User;

import java.math.BigDecimal;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    int checkUsername(String username);

    int checkEmail(String email);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username, @Param("question")String question,@Param("answer")String answer);

    int resetPasswordByForget(@Param("username") String username, @Param("passwordNew")String passwordNew);

    int checkPassword(@Param("passwordOld")String passwordOld,@Param("userId")Long userId);

    int checkEmailByUserId(@Param("email")String email,@Param("userId")Long userId);

    String getUserNameByUserId(Long userId);

    int subMoneyByIllegal(@Param("userId")Long userId, @Param("deposit")BigDecimal deposit);

    int addMoneyByIllegal(@Param("userId")Long userId, @Param("deposit")BigDecimal deposit);

    int subApproveDeposit(@Param("userId")Long userId, @Param("deposit")BigDecimal deposit);

    int addApproveDeposit(@Param("userId")Long userId, @Param("deposit")BigDecimal deposit);
}