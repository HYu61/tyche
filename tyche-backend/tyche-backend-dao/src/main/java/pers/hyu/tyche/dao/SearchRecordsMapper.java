package pers.hyu.tyche.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.hyu.tyche.pojo.entity.SearchRecords;
import pers.hyu.tyche.pojo.entity.SearchRecordsExample;

@Repository
public interface SearchRecordsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    long countByExample(SearchRecordsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int deleteByExample(SearchRecordsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int insert(SearchRecords record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int insertSelective(SearchRecords record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    List<SearchRecords> selectByExample(SearchRecordsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    SearchRecords selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SearchRecords record, @Param("example") SearchRecordsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SearchRecords record, @Param("example") SearchRecordsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SearchRecords record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_records
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SearchRecords record);
}