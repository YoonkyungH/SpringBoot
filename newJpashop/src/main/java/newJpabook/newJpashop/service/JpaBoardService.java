package newJpabook.newJpashop.service;

import newJpabook.newJpashop.entity.BoardEntity;
import newJpabook.newJpashop.entity.BoardFileEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface JpaBoardService {

    List<BoardEntity> selectBoardList() throws Exception;

    void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception;

    BoardEntity selectBoardDetail(int boardIdx) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;

    BoardFileEntity selectBoardFileInformation(int idx, int boardIdx) throws Exception;

    void deleteBoardFile(int idx, int boardIdx) throws Exception;
}
