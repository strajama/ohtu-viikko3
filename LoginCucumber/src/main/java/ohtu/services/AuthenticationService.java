package ohtu.services;

import ohtu.domain.User;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;

    @Autowired
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

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {

        String valid = "[a-z]";
        if (username.length() < 3) {
            return true;
        }
        if (username.matches(valid)) {
            return true;
        }
        if (password.length() < 8) {
            return true;
        }
        for (int i = 0; i < password.length(); i++) {
            char check = password.charAt(i);
            if (java.lang.Character.isDigit(check)) {
                return false;
            } else if (!java.lang.Character.isLetter(check)) {
                return false;
            }
        }
        return true;
    }
}
