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
public class UserNotCreatedException extends RuntimeException{

    private static final long serialVersionUID = -143337376185125834L;
    
    public UserNotCreatedException(String message) {
        super(message);
    }
}
    