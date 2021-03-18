package pers.hyu.tyche.pojo.model.request;

/**
 * The class is a request model for update user's info request.
 *
 * @author Heng Yu
 */
public class UserEditModel {
    private String nickname;
    private String vipPass; // the answer of the question for upgrade vip
    private String vipVideoAccessQuestion;
    private String vipVideoAccessAnswer;
    private boolean deleteVipVideoAccess;

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

    public String getVipVideoAccessQuestion() {
        return vipVideoAccessQuestion;
    }

    public void setVipVideoAccessQuestion(String vipVideoAccessQuestion) {
        this.vipVideoAccessQuestion = vipVideoAccessQuestion;
    }

    public String getVipVideoAccessAnswer() {
        return vipVideoAccessAnswer;
    }

    public void setVipVideoAccessAnswer(String vipVideoAccessAnswer) {
        this.vipVideoAccessAnswer = vipVideoAccessAnswer;
    }

    public boolean isDeleteVipVideoAccess() {
        return deleteVipVideoAccess;
    }

    public void setDeleteVipVideoAccess(boolean deleteVipVideoAccess) {
        this.deleteVipVideoAccess = deleteVipVideoAccess;
    }
}
