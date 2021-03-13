package pers.hyu.tyche.pojo.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This class is a model for user register and login request.
 *
 * @author Heng Yu
 * @version 1.0
 */
@ApiModel(value = "user register and login model",
        description = "username and password are required for the user sign up and login; security answer is required for sign up")
public class UserRegisterAndLoginModel {
    @ApiModelProperty(position = 1, required = true, example = "username")
    private String username;
    @ApiModelProperty(position = 2, required = true, example = "password")
    private String plainPassword;
    @ApiModelProperty(position = 3, notes="Not used for login", required = false, example = "mother's name")
    private String securityAnswer;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
}
