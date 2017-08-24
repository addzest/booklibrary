package com.laba.booklibrary.web.listener;


import com.laba.booklibrary.service.connection.ConnectionPool;
import com.laba.booklibrary.service.connection.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * listener to initialize/dispose connection pool
 */
public class LibraryServletContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(LibraryServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            log.info("Initializing connection pool");
            ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            log.error("Error initializing servlet context",e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().dispose();
            log.info("Connection pool was destroyed");
        } catch (ConnectionPoolException e) {
            log.error("Error destroying servlet context",e);
        }

    }
}
