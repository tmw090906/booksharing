package pers.tmw.booksharing.service;

import org.springframework.web.multipart.MultipartFile;
import pers.tmw.booksharing.common.ServerResponse;
import pers.tmw.booksharing.pojo.Advice;
import pers.tmw.booksharing.pojo.BookInfo;
import pers.tmw.booksharing.pojo.User;

/**
 * Created by tmw090906 on 2017/6/8.
 */
public interface IBookInfoService {

    ServerResponse getBookList(String textSearch, Long categoryId, int pageNum, int pageSize, String orderBy);

    ServerResponse adviceAddBook(Advice advice);

    //后台
    ServerResponse saveOrUpdateBook(User user, BookInfo bookInfo);

    String upload(MultipartFile file, String path, String currentUser);

    ServerResponse manageBookDetail(Long bookId);

    ServerResponse getBookList(int pageNum, int pageSize);

    ServerResponse getCommitAdviceList(int pageNum,int pageSize);

    ServerResponse manageAdvice(Long userId,Long adviceId,Short status);

    ServerResponse getAdviceDetail(Long adviceId);

    ServerResponse manageGetAdviceList(Long userId,int pageNum,int pageSize);

    ServerResponse getAdviceList(Long userId,int pageNum,int pageSize);

    ServerResponse getExchangeInfoOne(Long userId,Long bookId);

    ServerResponse getExchangeInfo(Long userId,Long bookId);


}
