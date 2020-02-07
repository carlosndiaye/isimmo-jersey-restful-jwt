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
public class CouldNotDeleteRecordException extends RuntimeException {

    private static final long serialVersionUID = 1331636780006638442L;
    
    public CouldNotDeleteRecordException(String message) {
        super(message);
    }
}

