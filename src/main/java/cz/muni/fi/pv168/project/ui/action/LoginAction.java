package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.service.crud.UserCrudService;
import java.security.*;

import java.util.List;


public class LoginAction {
    private UserCrudService userCrudService;

    public LoginAction(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    public boolean login(String username, String password) {
        if (!userCrudService.usernameExists(username)) {
            // TODO
            return false;
        }

        var res =  userCrudService.login(username, password);

        // do info to user here
        return res; // Incorrect username or password
    }


}