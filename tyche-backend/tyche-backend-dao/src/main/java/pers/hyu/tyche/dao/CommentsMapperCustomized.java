package pers.hyu.tyche.dao;

import org.springframework.stereotype.Repository;
import pers.hyu.tyche.pojo.model.dto.CommentDto;

import java.util.List;

@Repository
public interface CommentsMapperCustomized {
    List<CommentDto> selectCommentsByVideoId(String videoId);
}
