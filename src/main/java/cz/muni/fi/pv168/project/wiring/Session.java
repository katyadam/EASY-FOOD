package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;

public class Session {
    private final CommonDependencyProvider commonDependencyProvider;

    public Session(CommonDependencyProvider commonDependencyProvider) {
        this.commonDependencyProvider = commonDependencyProvider;
    }
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
