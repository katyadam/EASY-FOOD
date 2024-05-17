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
        changePasswordButton.addActionListener(e -> {
            if (!newPasswordField.getText().equals(newPasswordField2.getText())) {
            JOptionPane.showMessageDialog(null, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }
            if (!commonDependencyProvider.getUserRepository().existByLogin(commonDependencyProvider.getSession().getLoggedUser().getName(), oldPasswordField.getText()).isPresent()) {
            JOptionPane.showMessageDialog(null, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your password?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
            return;
            }
            
            // need hashing
            RegisteredUser user = commonDependencyProvider.getUserRepository().findById((commonDependencyProvider.getSession().getLoggedUser().getID())).get();
            user.setPassword(newPasswordField.getText());
            commonDependencyProvider.getUserRepository().update(user);
            JOptionPane.showMessageDialog(null, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
        add(changePasswordButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
        
        pack();
    }

}
