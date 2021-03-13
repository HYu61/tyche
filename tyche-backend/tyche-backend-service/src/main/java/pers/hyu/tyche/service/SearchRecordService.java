package pers.hyu.tyche.service;

import java.util.Set;

/**
 * The interface is used for get and store the hot search content from redis.
 *
 * @author Heng Yu
 * @version 1.0
 */
public interface SearchRecordService {
    /**
     * Add the search content to the redis.
     *
     * @param searchContent The content of search.
     */
    void addSearchContent(String searchContent);

    /**
     * Get the records from the redis.
     *
     * @param topNum The param is used to set how many records need to get.
     * @return The list of the search records.
     */
    Set<String> getHotSearch(Integer topNum);
}
