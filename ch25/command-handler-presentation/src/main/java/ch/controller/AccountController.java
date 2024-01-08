package ch.controller;

import ch.controller.DomainNamespace.Membership;
import ch.controller.DomainNamespace.MembershipUser;
import ch.models.ChangePasswordModel;
import ch.models.LogInModel;
import ch.models.RegisterModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Controller
@ControllerAdvice
@RequestMapping("/account")
class AccountController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<String> handleValidationError(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }

    @GetMapping("/log-in")
    public String logIn() {
        return "account/log-in";
    }

    @PostMapping(path = "/log-in", consumes = {APPLICATION_FORM_URLENCODED_VALUE})
    public String logOn(@Valid LogInModel logOnModel, @RequestParam(name = "returnUrl", defaultValue = "", required = false) String returnUrl,
                        HttpServletResponse response, Model model) {

        if (Membership.validateUser(logOnModel.userName, logOnModel.password)) {
            setSessionCookie(response, logOnModel.userName, logOnModel.password);

            if (isURL(returnUrl) && returnUrl.length() > 1 && returnUrl.startsWith("/")
                    && !returnUrl.startsWith("//") && !returnUrl.startsWith("/\\")) {
                return "redirect:" + returnUrl;
            } else {
                return "redirect:/";
            }
        } else {
            model.addAttribute("message", "The username or password provided is incorrect.");
        }
        // If we got this far, something failed, redisplay form
        return "account/log-in";
    }

    @GetMapping("/log-off")
    public String logOff(HttpServletResponse response) {
        Cookie cookie = new Cookie("session", null);
        cookie.setPath("/account");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerModel", new RegisterModel());
        return "account/register";
    }

    @PostMapping(path = "/register", consumes = {APPLICATION_FORM_URLENCODED_VALUE})
    public String register(@Valid RegisterModel registerModel,
                           HttpServletResponse response, Model model) {
        // Attempt to register the user
        DomainNamespace.MembershipCreateStatus createStatus;
        createStatus = Membership.createUser(registerModel.userName, registerModel.password, registerModel.email);

        if (createStatus == DomainNamespace.MembershipCreateStatus.Success) {
            setSessionCookie(response, registerModel.userName, registerModel.password);
            return "redirect:/";
        } else {
            model.addAttribute("message", errorCodeToString(createStatus));
        }
        // If we got this far, something failed, redisplay form
        return "account/register";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordModel", new ChangePasswordModel());
        return "account/change-password";
    }

    @PostMapping(path = "/change-password", consumes = {APPLICATION_FORM_URLENCODED_VALUE})
    public String changePassword(@Valid ChangePasswordModel changePasswordModel,
                                 @CookieValue(required = false, name = "session") String cookie,
                                 HttpServletRequest request, Model model) {

        // ChangePassword will throw an exception rather
        // than return false in certain failure scenarios.
        boolean changePasswordSucceeded;
        try {
            var username = cookie == null ? null : cookie.split("=")[0];
            MembershipUser currentUser = Membership.getUser(username, true /* userIsOnline */);
            changePasswordSucceeded = currentUser.ChangePassword(changePasswordModel.oldPassword, changePasswordModel.newPassword);
        } catch (Exception e) {
            changePasswordSucceeded = false;
        }

        if (changePasswordSucceeded) {
            return "account/change-password-success";
        } else {
            model.addAttribute("message", "The current password is incorrect or the new password is invalid.");
        }
        // If we got this far, something failed, redisplay form
        return "account/change-password";
    }

    @GetMapping("/change-password-success")
    public String changePasswordSuccess() {
        return "account/change-password-success";
    }

    private void setSessionCookie(HttpServletResponse response, String userName, String password) {
        Cookie cookie = new Cookie("session", userName + "=" + password);
        cookie.setPath("/account");
        response.addCookie(cookie);
    }

    private static boolean isURL(String url) {
        // Url.IsLocalUrl(returnUrl) &&
        return true;
        // try {
        //     new URL(url);
        //     return true;
        // } catch (Exception e) {
        //     return false;
        // }
    }

    private static String errorCodeToString(DomainNamespace.MembershipCreateStatus createStatus) {
        // See http://go.microsoft.com/fwlink/?LinkID=177550 for
        // a full list of status codes.
        switch (createStatus) {
            case DuplicateUserName:
                return "User name already exists. Please enter a different user name.";

            case DuplicateEmail:
                return "A user name for that e-mail address already exists. Please enter a different e-mail address.";

            case InvalidPassword:
                return "The password provided is invalid. Please enter a valid password value.";

            case InvalidEmail:
                return "The e-mail address provided is invalid. Please check the value and try again.";

            case InvalidAnswer:
                return "The password retrieval answer provided is invalid. Please check the value and try again.";

            case InvalidQuestion:
                return "The password retrieval question provided is invalid. Please check the value and try again.";

            case InvalidUserName:
                return "The user name provided is invalid. Please check the value and try again.";

            case ProviderError:
                return "The authentication provider returned an error. Please verify your entry and try again. If the problem persists, please contact your system administrator.";

            case UserRejected:
                return "The user creation request has been canceled. Please verify your entry and try again. If the problem persists, please contact your system administrator.";

            default:
                return "An unknown error occurred. Please verify your entry and try again. If the problem persists, please contact your system administrator.";
        }
    }
}
