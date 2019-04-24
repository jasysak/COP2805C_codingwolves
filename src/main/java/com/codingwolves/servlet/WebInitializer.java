package com.codingwolves.servlet;

import com.codingwolves.configuration.SpringWebConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This class is required to register a DispatcherServlet which is then used to begin a Java-based
 * Spring web application. The WebInitializer class is needed to every Spring app; alternatively
 * it is possible to define the application in an xml.
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * This method is used to specify all the the classes for the Servlet application context
     * @return {@link com.codingwolves.configuration.SpringWebConfiguration} which is our
     * current configuration for the Servlet application.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringWebConfiguration.class };
    }

    /**
     * This method is used to specify the servlet mapping for the DispatcherServlet.
     * In essence, you specify the name of our root directory.
     * @return String which specifies our root.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * This method set the configuration for the root app.
     * @return Null in our case because the creation and registration of the root is
     * not desired.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }
}
