package pers.hyu.tyche.pojo.model.request;

public class UserRelationModel {
    private String loginUserId;
    private String videoPublishUserId;
    private boolean likeVideoOrFollowPublisher;

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getVideoPublishUserId() {
        return videoPublishUserId;
    }

    public void setVideoPublishUserId(String videoPublishUserId) {
        this.videoPublishUserId = videoPublishUserId;
    }

    public boolean isLikeVideoOrFollowPublisher() {
        return likeVideoOrFollowPublisher;
    }

    public void setLikeVideoOrFollowPublisher(boolean likeVideoOrFollowPublisher) {
        this.likeVideoOrFollowPublisher = likeVideoOrFollowPublisher;
    }
}
