/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.service;

import com.kraken.isimmo.exceptions.AuthenticationException;
import com.kraken.isimmo.shared.dto.UserDTO;

/**
 *
 * @author carlosndiaye
 */
public interface AuthenticationService {
    UserDTO authenticate(String username, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
    public void resetSecurityCredentials(String password, UserDTO userProfile);
}
