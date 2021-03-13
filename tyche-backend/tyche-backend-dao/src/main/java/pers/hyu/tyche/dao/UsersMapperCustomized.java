package pers.hyu.tyche.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.hyu.tyche.pojo.entity.Users;
import pers.hyu.tyche.pojo.model.response.UserResponseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface UsersMapperCustomized {
   Users selectExistsUser(String usernameOrId);
   Users selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
   Users selectByUsernameAndSecurityAnswer(@Param("username") String username, @Param("answer") String answer);
   List<UserResponseModel> selectUserFollows(String userId);
   void addReceivedLikeCount(String userId);
   void reduceReceivedLikeCount(Map map);

   void addFollowerCount(String userId);    // fans
   void reduceFollowerCount(String userId); // fans

   void addFollowCount(String userId);
   void reduceFollowCount(String userId);


}
