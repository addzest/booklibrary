package com.laba.booklibrary.service.connection;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory;

    private static Session currentSession;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();

        } catch (Exception ex) {
            log.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private HibernateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        if (currentSession == null) {
            currentSession = sessionFactory.openSession();
        }
        return currentSession;
    }

    public static void closeSession() {
        if (currentSession != null) {
            currentSession.close();
            currentSession = null;
        }
    }

}
