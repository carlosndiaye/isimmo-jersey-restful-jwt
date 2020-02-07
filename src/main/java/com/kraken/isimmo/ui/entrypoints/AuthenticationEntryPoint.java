/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.ui.entrypoints;

import com.kraken.isimmo.service.AuthenticationService;
import com.kraken.isimmo.service.impl.AuthenticationServiceImpl;
import com.kraken.isimmo.shared.dto.UserDTO;
import com.kraken.isimmo.ui.request.LoginCredentials;
import com.kraken.isimmo.ui.response.AuthenticationDetails;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author carlosndiaye
 */
@Path("/authentication")
public class AuthenticationEntryPoint {
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AuthenticationDetails userLogin(LoginCredentials loginCredentials) {
        AuthenticationDetails returnValue = new AuthenticationDetails();
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        
        UserDTO authenticatedUser = authenticationService.authenticate(loginCredentials.getUsername(), loginCredentials.getPassword());
       
        // Issue Access Token
        
        String accessToken = authenticationService.issueAccessToken(authenticatedUser);
        
        returnValue.setId(authenticatedUser.getUserId());
        returnValue.setToken(accessToken);
        
        return returnValue;
    }
        
}
