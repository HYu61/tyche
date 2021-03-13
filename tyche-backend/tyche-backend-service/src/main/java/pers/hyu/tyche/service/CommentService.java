package pers.hyu.tyche.service;

import pers.hyu.tyche.pojo.model.request.CommentModel;
import pers.hyu.tyche.utils.PagedResult;

/**
 * The interface is userd for add and get comments.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface CommentService {
    /**
     * Add the comment to the db.
     *
     * @param commentModel The model include the info of the comment.
     */
    void addComment(CommentModel commentModel);

    /**
     * Get all the comments to the video.
     *
     * @param videoId The id of the video.
     * @param page The num of from which page to start select the data.
     * @param pageSize The num of how many records will display in one page.
     * @return The pagination of the list of the video's cover.
     */
    PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
}
