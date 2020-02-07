/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.ui.entrypoints;

import com.kraken.isimmo.annotations.Secured;
import com.kraken.isimmo.service.UserService;
import com.kraken.isimmo.service.impl.UserServiceImpl;
import com.kraken.isimmo.shared.dto.UserDTO;
import com.kraken.isimmo.ui.request.CreateUserRequestModel;
import com.kraken.isimmo.ui.request.UpdateUserRequestModel;
import com.kraken.isimmo.ui.response.DeleteUserResponseModel;
import com.kraken.isimmo.ui.response.RequestOperation;
import com.kraken.isimmo.ui.response.ResponseStatus;
import com.kraken.isimmo.ui.response.UserProfile;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author carlosndiaye
 */

@Path("/users")
public class UsersEntryPoint {

    //@Secured
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile createUser(CreateUserRequestModel requestObject) {
        UserProfile returnValue = new UserProfile();

        // Prepare UserDTO
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDto);

        // Create new user
        UserService userService = new UserServiceImpl();
        UserDTO createdUser = userService.createUser(userDto);

        // Prepare response
        BeanUtils.copyProperties(createdUser, returnValue);
        return returnValue;
    }

    @Secured
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUserProfile(@PathParam("id") String id) {
        UserProfile returnValue = null;
        UserService userService = new UserServiceImpl();
        UserDTO userProfile = userService.getUser(id);

        returnValue = new UserProfile();
        BeanUtils.copyProperties(userProfile, returnValue);

        return returnValue;
    }

    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserProfile> getUsers(@DefaultValue("0") @QueryParam("start") int start,
            @DefaultValue("50") @QueryParam("limit") int limit) {

        UserService userService = new UserServiceImpl();
        List<UserDTO> users = userService.getUsers(start, limit);

        // Prepare return value
        List<UserProfile> returnValue = new ArrayList<UserProfile>();

        for (UserDTO userDto : users) {
            UserProfile userModel = new UserProfile();
            BeanUtils.copyProperties(userDto, userModel);
            userModel.setHref("/users/" + userDto.getUserId());
            returnValue.add(userModel);
        }

        return returnValue;
    }
    
    @Secured
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile updateUserDetails(@PathParam("id") String id, UpdateUserRequestModel userDetails) {
        UserService userService = new UserServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);
        
        // Set only those fields you would like to be updated with this request
        if ( userDetails.getFirstname() != null && !userDetails.getFirstname().isEmpty() ) {
            storedUserDetails.setFirstname(userDetails.getFirstname());
        }
        storedUserDetails.setLastname(userDetails.getLastname());
        
        // Update user details
        userService.updateUserDetails(storedUserDetails);
        
        // Prepare return value
        UserProfile returnValue = new UserProfile();
        BeanUtils.copyProperties(storedUserDetails, returnValue);
        
        return returnValue;
    }
    
    @Secured
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeleteUserResponseModel deleteUserProfile(@PathParam("id") String id) {
        DeleteUserResponseModel returnValue = new DeleteUserResponseModel();
        returnValue.setRequestOperation(RequestOperation.DELETE);
        
        UserService userService = new UserServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);
        
        userService.deleteUser(storedUserDetails);
        
        returnValue.setResponseStatus(ResponseStatus.SUCCESS);
        
        return returnValue;
    }

}
