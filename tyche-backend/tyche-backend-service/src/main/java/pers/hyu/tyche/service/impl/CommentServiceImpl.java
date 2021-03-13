package pers.hyu.tyche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.hyu.tyche.dao.CommentsMapper;
import pers.hyu.tyche.dao.CommentsMapperCustomized;
import pers.hyu.tyche.pojo.entity.Comments;
import pers.hyu.tyche.pojo.model.dto.CommentDto;
import pers.hyu.tyche.pojo.model.request.CommentModel;
import pers.hyu.tyche.service.CommentService;
import pers.hyu.tyche.utils.EntityUtils;
import pers.hyu.tyche.utils.PagedResult;
import pers.hyu.tyche.utils.TimeAgoUtils;

import java.util.Date;
import java.util.List;

/**
 * @see pers.hyu.tyche.service.CommentService
 *
 * @author Heng Yu
 * @version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private EntityUtils entityUtils;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CommentsMapperCustomized commentsMapperCustomized;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addComment(CommentModel commentModel) {
        Comments comments = new Comments();
        BeanUtils.copyProperties(commentModel, comments);
        comments.setId(entityUtils.generateId());
        comments.setCreateTime(new Date());
        commentsMapper.insertSelective(comments);

    }

    @Transactional(readOnly = true)
    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CommentDto> list = commentsMapperCustomized.selectCommentsByVideoId(videoId);

        list.forEach(commentDto -> {
            commentDto.setTimeAgoStr(TimeAgoUtils.format(commentDto.getCreateTime()));
        });

        PageInfo<CommentDto> pageList = new PageInfo<>(list);
        PagedResult result = new PagedResult();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());
        return result;
    }
}
