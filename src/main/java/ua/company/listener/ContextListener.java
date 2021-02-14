package ua.company.listener;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        initLog4j(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void initLog4j(ServletContext servletContext) {
        try {
            PropertyConfigurator.configure(servletContext.getRealPath("src/main/resources/log4j.properties"));
        } catch (Exception e) {
            System.out.println("[ContextListener] " + e.getMessage());
        }
    }
}
