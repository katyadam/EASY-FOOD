package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;

public class Session {
    public RegisteredUser loggedUser = null;

    public boolean isLoggedIn() {
        return loggedUser != null;
    }

    public void Logout() {
        loggedUser = null;
    }

    public RegisteredUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(RegisteredUser loggedUser) {
        this.loggedUser = loggedUser;
    }
}
