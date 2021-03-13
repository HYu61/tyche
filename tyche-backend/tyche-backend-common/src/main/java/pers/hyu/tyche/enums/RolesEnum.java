package pers.hyu.tyche.enums;

public enum  RolesEnum {
    REGULAR_USER("R_USER"),
    VIP_USER("V_USER");

    private final String ROLE;

    RolesEnum(String role) {
        this.ROLE = role;
    }

    public String getRole() {
        return ROLE;
    }
}
