/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.exceptions;

/**
 *
 * @author carlosndiaye
 */
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -6588749761790312183L;
    
    public AuthenticationException (String message) {
        super(message);
    }
}
