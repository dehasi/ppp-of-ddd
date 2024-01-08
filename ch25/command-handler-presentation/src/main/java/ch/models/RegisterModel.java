package ch.models;

import ch.models.validators.FieldsMatch;
import ch.models.validators.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@FieldsMatch(fields = {"password", "confirmPassword"})
public class RegisterModel {

    @NotBlank(message = "userName is required")
    public String userName;

    @NotBlank(message = "email is required")
    @Email
    public String email;

    // [StringLength(100, ErrorMessage = "The {0} must be at least {2} characters long.", MinimumLength = 6)]
    @Password
    @NotBlank(message = "password is required")
    @Length(min = 1, max = 100)
    public String password;

    // [System.Web.Mvc.Compare("Password", ErrorMessage = "The password and confirmation password do not match.")]
    @Password
    @NotBlank(message = "password is required")
    @Length(min = 1, max = 100)
    public String confirmPassword;

    public String getUserName() {
        return userName;
    }

    public RegisterModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
