/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.service.impl;

import com.kraken.isimmo.exceptions.AuthenticationException;
import com.kraken.isimmo.io.dao.DAO;
import com.kraken.isimmo.io.dao.impl.MySQLDAO;
import com.kraken.isimmo.service.AuthenticationService;
import com.kraken.isimmo.service.UserService;
import com.kraken.isimmo.shared.dto.UserDTO;
import com.kraken.isimmo.ui.response.ErrorMessages;
import com.kraken.isimmo.utils.JwTokenHelper;
import com.kraken.isimmo.utils.UserProfileUtils;


/**
 *
 * @author carlosndiaye
 */
public class AuthenticationServiceImpl implements AuthenticationService {
    
    DAO database;
   

    @Override
    public UserDTO authenticate(String username, String password) throws AuthenticationException {
        UserService userService = new UserServiceImpl();
        UserDTO storedUser = userService.getUserByUsername(username); // username is unique
        
        if ( storedUser == null ) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessages());
        }
        
        String encryptedPassword = null;
        
        encryptedPassword = new UserProfileUtils().
                generateSecurePassword(password, storedUser.getSalt());
        
        boolean authenticated = false;
        if ( encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword()) ) {
            if ( username != null && username.equalsIgnoreCase(storedUser.getEmail()) ) {
                authenticated = true; 
            }
        }
        
        if ( !authenticated ) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessages());
        }
        
        return storedUser;
    }

    @Override
    public String issueAccessToken(UserDTO userProfile) {
        String privateKey = JwTokenHelper.getInstance().generatePrivateKey(userProfile.getEmail(), userProfile.getEncryptedPassword());
        return privateKey;
    }
    
    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MySQLDAO();
        try {
            database.openConnection();
            database.updateUser(userProfile);
        } finally {
            database.closeConnection();
        }
    }

    @Override
    public void resetSecurityCredentials(String password, UserDTO userProfile) {
        // Generate a new salt
        UserProfileUtils userUtils = new UserProfileUtils();
        String salt = userUtils.getSalt(30);
        
        // Generate a new password
        String securePassword = userUtils.generateSecurePassword(password, salt);
        userProfile.setSalt(salt);
        userProfile.setEncryptedPassword(securePassword);
        
        // Update user profile
        updateUserProfile(userProfile);
        
    }
    
}
