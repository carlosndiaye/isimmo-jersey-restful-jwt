/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.service.impl;

import com.kraken.isimmo.exceptions.CouldNotDeleteRecordException;
import com.kraken.isimmo.exceptions.CouldNotUpdateRecordException;
import com.kraken.isimmo.exceptions.NoRecordFoundException;
import com.kraken.isimmo.exceptions.UserNotCreatedException;
import com.kraken.isimmo.io.dao.DAO;
import com.kraken.isimmo.io.dao.impl.MySQLDAO;
import com.kraken.isimmo.service.UserService;
import com.kraken.isimmo.shared.dto.UserDTO;
import com.kraken.isimmo.ui.response.ErrorMessages;
import com.kraken.isimmo.utils.UserProfileUtils;
import java.util.List;

/**
 *
 * @author carlosndiaye
 */
public class UserServiceImpl implements UserService{
    
    DAO database;
    UserProfileUtils userProfileUtils = new UserProfileUtils();
    
    public UserServiceImpl() {
        this.database = new MySQLDAO();
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        UserDTO returnValue = null;
        
        // Validate the required fields
        userProfileUtils.validateRequiredFields(user);
        
        // Check if user already exists
        UserDTO existingUser = this.getUserByUsername(user.getEmail());
        if ( existingUser != null ) {
            throw new UserNotCreatedException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }
        
        // Generate secure public user id
        String userId = userProfileUtils.generateUserId(30);
        user.setUserId(userId);
                
        // Generate salt;
        String salt = userProfileUtils.getSalt(30);
              
        // Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);
        
        // Save data into database
        returnValue = this.saveUser(user);
        
        // Return back the user 
        return returnValue;
    }
    
    @Override
    public UserDTO getUserByUsername(String username) {
        UserDTO userDto = null;
        
        if ( username == null || username.isEmpty() ) {
            return userDto;
        }
        
        // Connect the database
        try {
            this.database.openConnection();
            userDto = this.database.getUserByUsername(username);
        } finally {
            this.database.closeConnection();
        }
        
        return userDto;
    }
    
    private UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        
        // Connect the database
        try {
            this.database.openConnection();
            returnValue = this.database.saveUser(user);
        } finally {
            this.database.closeConnection();
        }
        
        return returnValue;
    }

    @Override
    public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        List<UserDTO> users = null;
        
        // Get users from database
        try {
            this.database.openConnection();
            users = this.database.getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }
        
        return users;
    }

    @Override
    public void updateUserDetails(UserDTO userDetails) {
        try {
            // Connect to database
            this.database.openConnection();
            // Update user details
            this.database.updateUser(userDetails);
        } catch (Exception ex) {
            throw new CouldNotUpdateRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }
    }

    @Override
    public void deleteUser(UserDTO userDto) {
        try {
            this.database.openConnection();
            this.database.deleteUser(userDto);
        } catch (Exception ex) {
            throw new CouldNotDeleteRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }
        
        // Verify if user is deleted
        try {
            userDto = getUser(userDto.getUserId());
        } catch (NoRecordFoundException ex) {
            userDto = null;
        }
        
        if ( userDto != null ) {
            throw new CouldNotDeleteRecordException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessages());
        }
    }
    
}
