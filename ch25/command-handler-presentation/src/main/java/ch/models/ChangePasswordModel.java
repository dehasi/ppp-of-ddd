package ch.models;

import ch.models.validators.FieldsMatch;
import ch.models.validators.Password;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@FieldsMatch(fields = {"newPassword", "confirmPassword"})
public class ChangePasswordModel {

    @Password
    @NotBlank(message = "oldPassword is required")
    @Length(min = 1, max = 100)
    public String oldPassword;

    // [StringLength(100, ErrorMessage = "The {0} must be at least {2} characters long.", MinimumLength = 6)]
    @Password
    @NotBlank(message = "newPassword is required")
    @Length(min = 1, max = 100)
    public String newPassword;

    // [System.Web.Mvc.Compare("NewPassword", ErrorMessage = "The new password and confirmation password do not match.")]
    @Password
    @NotBlank(message = "confirmPassword is required")
    @Length(min = 1, max = 100)
    public String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public ChangePasswordModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public ChangePasswordModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
