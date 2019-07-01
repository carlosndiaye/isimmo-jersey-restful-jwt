/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.io.dao;

import com.kraken.isimmo.shared.dto.UserDTO;
import java.util.List;

/**
 *
 * @author carlosndiaye
 */
public interface DAO {
    void openConnection();
    UserDTO getUserByUsername(String username);
    UserDTO saveUser(UserDTO user);
    UserDTO getUser(String id);
    List<UserDTO> getUsers(int start, int limit);
    void updateUser(UserDTO userProfile);
    void deleteUser(UserDTO userProfile);
    void closeConnection();
}
