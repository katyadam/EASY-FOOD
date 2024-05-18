package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;

public class ChangePasswordDialog extends JDialog{
    
    private JTextField oldPasswordField;
    private JTextField newPasswordField;
    private JTextField newPasswordField2;
    private CommonDependencyProvider commonDependencyProvider;
    
    public ChangePasswordDialog(CommonDependencyProvider commonDependencyProvider) {
        this.commonDependencyProvider = commonDependencyProvider;
        
        JLabel oldPasswordLabel = new JLabel("Old password:");
        oldPasswordField = new JPasswordField();
        add(oldPasswordLabel);
        add(oldPasswordField);
        
        JLabel newPasswordLabel = new JLabel("New password:");
        newPasswordField = new JPasswordField();
        add(newPasswordLabel);
        add(newPasswordField);
        
        JLabel newPasswordLabel2 = new JLabel("New password again:");
        newPasswordField2 = new JPasswordField();
        add(newPasswordLabel2);
        add(newPasswordField2);
        
        JButton changePasswordButton = new JButton("Change password");
        changePasswordButton.addActionListener(e -> changePassword());

        add(changePasswordButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
        
        pack();
    }

    private void changePassword() {
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String newPassword2 = newPasswordField2.getText();
        
        if (!newPassword.equals(newPassword2)) {
            JOptionPane.showMessageDialog(null, "New passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        RegisteredUser user = commonDependencyProvider.getSession().getLoggedUser();
        if (!user.getPassword().equals(oldPassword)) {
            JOptionPane.showMessageDialog(null, "Old password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        user.setPassword(newPassword);
        commonDependencyProvider.getUserRepository().update(user);
        JOptionPane.showMessageDialog(null, "Password changed", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}
