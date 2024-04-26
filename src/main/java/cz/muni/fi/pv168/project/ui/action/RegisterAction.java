
package cz.muni.fi.pv168.project.ui.action;
import cz.muni.fi.pv168.project.database.AccountManager;



public class RegisterAction {
    private AccountManager accountManager;

    public RegisterAction(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void register(String username, String password) {
        if (accountManager.usernameExists(username)) {
            System.out.println("Username already exists in the database.");
            return;
        }

        if (!isStrongPassword(password)) {
            System.out.println("Password is not strong enough.");
            return;
        }

        // Create a new account in the database
        accountManager.createAccount(username, password);
        System.out.println("Account created successfully.");
    }
}
