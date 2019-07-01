/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.exceptions;

import com.kraken.isimmo.ui.response.ErrorMessage;
import com.kraken.isimmo.ui.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author carlosndiaye
 */
@Provider
public class MissingRequiredFieldExceptionMapper implements ExceptionMapper<MissingRequiredFieldException> {
    public Response toResponse(MissingRequiredFieldException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.MISSING_REQUIRED_FIELD.name(), "http://kraken.com");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
