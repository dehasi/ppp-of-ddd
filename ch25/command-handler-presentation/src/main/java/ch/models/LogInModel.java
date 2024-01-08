package ch.models;

import ch.models.validators.Password;
import jakarta.validation.constraints.NotBlank;

public class LogInModel {

    @NotBlank(message = "userName is required")
    public String userName;

    @Password
    @NotBlank(message = "password is required")
    public String password;

    public boolean rememberMe;

    public String getUserName() {
        return userName;
    }

    public LogInModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LogInModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public LogInModel setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
        return this;
    }
}
