package pers.tmw.booksharing.service;

import pers.tmw.booksharing.common.ServerResponse;

/**
 * Created by tmw090906 on 2017/6/11.
 */
public interface ISelfLibraryService {

    ServerResponse addBook(Long userId,Long bookId,Short status);

    ServerResponse getList(Long userId,Short status,int pageNum,int pageSize);

    ServerResponse updateStatus(Long userId,Long id,Short status);
}
