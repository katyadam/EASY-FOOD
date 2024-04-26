package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.AccountManager;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;


public class LoginAction {
    private AccountManager accountManager;

    public LoginAction(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public boolean login(String username, String password) {
        // Hash the password
        String hashedPassword = hashPassword(password);

        // Load the user from the database
        RegisteredUser user = accountManager.loadUserByUsername(username);

        // Check if the user exists and compare passwords
        if (user != null && comparePasswords(hashedPassword, user.getPassword())) {
            return true; // Correct username and password
        }

        return false; // Incorrect username or password
    }

    private String hashPassword(String password) {
        // Implement password hashing logic here
        // You can use libraries like BCrypt or SHA-256 for secure password hashing
        // Example: return BCrypt.hashpw(password, BCrypt.gensalt());

        // For simplicity, let's assume no password hashing is done in this example
        return password;
    }

    private boolean comparePasswords(String hashedPassword, String storedPassword) {
        // Implement password comparison logic here
        // You should use a secure password comparison method like BCrypt's checkpw() method
        // Example: return BCrypt.checkpw(hashedPassword, storedPassword);

        // For simplicity, let's assume plain text comparison in this example
        return hashedPassword.equals(storedPassword);
    }
}