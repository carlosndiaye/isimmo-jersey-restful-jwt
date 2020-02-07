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
public class NoRecordFoundException extends RuntimeException {

    private static final long serialVersionUID = -8050570112571459402L;
    
    public NoRecordFoundException(String message) {
        super(message);
    }
}
