package ohtu.authentication;

import ohtu.data_access.UserDao;
import ohtu.domain.User;
import ohtu.util.CreationStatus;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
        CreationStatus status = new CreationStatus();
        String valid = "[a-z]";

        if (userDao.findByName(username) != null) {
            status.addError("username is already taken");
        }

        if (username.length() < 3) {
            status.addError("username should have at least 3 characters");
        }

        if (!password.equals(passwordConfirmation)) {
            status.addError("password and password confirmation do not match");
        }

        if (password.length() < 8) {
            status.addError("password should have at least 8 characters");
        }

        if (username.matches(valid)) {
            status.addError("username is not valid");
        }
        for (int i = 0; i < password.length(); i++) {
            char check = password.charAt(i);
            if (java.lang.Character.isDigit(check)) {
                break;
            } else if (!java.lang.Character.isLetter(check)) {
                break;
            }
            if (i==password.length()-1) {
                status.addError("password is not valid");
            }
        }

        if (status.isOk()) {
            userDao.add(new User(username, password));
        }

        return status;
    }

}
