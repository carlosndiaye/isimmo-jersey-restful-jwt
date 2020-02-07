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
public class MissingRequiredFieldException extends RuntimeException{
    
    private static final long serialVersionUID = -5205793464355371646L;
    
    public MissingRequiredFieldException(String message) {
        super(message);
    }
    
}
