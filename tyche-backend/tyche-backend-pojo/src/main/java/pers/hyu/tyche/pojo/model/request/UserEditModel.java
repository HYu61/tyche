package pers.hyu.tyche.pojo.model.request;

/**
 * The class is a request model for update user's info request.
 *
 * @author Heng Yu
 */
public class UserEditModel {
    private String nickname;
//    private Integer followerCounts;
//    private Integer followCounts;
//    private Integer receivedLikeCounts;
    private String vipPass; // the answer of the question for upgrade vip

    public String getVipPass() {
        return vipPass;
    }

    public void setVipPass(String vipPass) {
        this.vipPass = vipPass;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

//    public Integer getFollowerCounts() {
//        return followerCounts;
//    }
//
//    public void setFollowerCounts(Integer followerCounts) {
//        this.followerCounts = followerCounts;
//    }
//
//    public Integer getFollowCounts() {
//        return followCounts;
//    }
//
//    public void setFollowCounts(Integer followCounts) {
//        this.followCounts = followCounts;
//    }
//
//    public Integer getReceivedLikeCounts() {
//        return receivedLikeCounts;
//    }
//
//    public void setReceivedLikeCounts(Integer receivedLikeCounts) {
//        this.receivedLikeCounts = receivedLikeCounts;
//    }

}
