/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.filters;


import com.kraken.isimmo.annotations.Secured;
import com.kraken.isimmo.exceptions.AuthenticationException;
import com.kraken.isimmo.utils.JwTokenHelper;
import com.sun.jersey.core.util.Priority;
import com.sun.jersey.spi.container.ContainerRequest;
import javax.ws.rs.container.ContainerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author carlosndiaye
 */


@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String PRIVATE_KEY = "token";
    private JwTokenHelper jwTokenHelper = JwTokenHelper.getInstance();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        //String path = request.getPath();
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            throw new AuthenticationException("Authorization header must be provided");
        }

        String privateKeyHeaderValue = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String pKey = privateKeyHeaderValue.substring(6).trim();

        try {
            jwTokenHelper.claimKey(pKey);
        } catch (Exception e) {
            //e.printStackTrace();
            if (e instanceof ExpiredJwtException)
                throw new AuthenticationException(PRIVATE_KEY + " is expired");
            else if (e instanceof MalformedJwtException)
                throw new AuthenticationException(PRIVATE_KEY + " is not formed correct");
            else if (e instanceof SignatureException)
                throw new AuthenticationException(PRIVATE_KEY + " validity cannot be asserted and should not be trusted");
        }
      
    }
}