 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kraken.isimmo.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 *
 * @author carlosndiaye
 */
public class HibernateUtils {
    private static final SessionFactory sessionFactory;
    
    static {
        Configuration conf = new Configuration();
        conf.configure();
        
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            System.err.println("Initial session factory creation failed " + e);
            throw new ExceptionInInitializerError(e);
        }    
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
