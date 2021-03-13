package pers.hyu.tyche.enums;

public enum LikeStatusEnum {
    LIKE(1, "Like or Follow"),
    UNLIKE(2, "Unlike or UnFollow");

    private Integer code;
    private String desc;

    LikeStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
