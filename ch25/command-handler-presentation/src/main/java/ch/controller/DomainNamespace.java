package ch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class DomainNamespace {

    static class Membership {
        private static final List<MembershipUser> users = new ArrayList<>() {{
            add(new MembershipUser("u1", "p1", "u1@gmail.com"));
            add(new MembershipUser("u2", "p2", "u2@gmail.com"));
        }};

        static boolean validateUser(String name, String password) {
            return users.stream()
                    .anyMatch(user -> user.name.equals(name)
                            && user.password.equals(password));
        }

        static MembershipUser getUser(String name, boolean userIsOnline) {
            return users.stream()
                    .filter(user -> user.name.equals(name))
                    .findFirst().orElse(null);
        }

        static MembershipCreateStatus createUser(String name, String password, String email) {
            if (getUser(name, false) != null)
                return MembershipCreateStatus.DuplicateUserName;

            Optional<String> duplicateEMail = users.stream()
                    .filter(user -> user.email.equals(email))
                    .findFirst()
                    .map(user -> user.email);
            if (duplicateEMail.isPresent())
                return MembershipCreateStatus.DuplicateEmail;

            users.add(new MembershipUser(name, password, email));
            return MembershipCreateStatus.Success;
        }
    }

    static class MembershipUser {
        String name, password, email;

        public MembershipUser(String name, String password, String email) {
            this.name = name;
            this.password = password;
            this.email = email;
        }

        public boolean ChangePassword(String oldPassword, String newPassword) {
            if (password.equals(oldPassword)) {
                password = newPassword;
                return true;
            }
            return false;
        }
    }

    enum MembershipCreateStatus {
        Success, DuplicateEmail, InvalidPassword, InvalidEmail, InvalidAnswer, InvalidQuestion, InvalidUserName, ProviderError, UserRejected, DuplicateUserName
    }
}
