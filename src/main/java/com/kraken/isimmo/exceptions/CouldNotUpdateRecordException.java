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
public class CouldNotUpdateRecordException extends RuntimeException {

    private static final long serialVersionUID = 7180377298521891333L;
    
    public CouldNotUpdateRecordException(String message) {
        super(message);
    }
}
