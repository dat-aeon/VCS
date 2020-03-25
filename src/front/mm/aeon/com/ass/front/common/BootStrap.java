package mm.aeon.com.ass.front.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class BootStrap implements ServletContextListener {
    private static Logger logger = Logger.getLogger(BootStrap.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initialization has been started...................");

        // Set Current project directory.
        ProjectPath.dirPath = sce.getServletContext().getRealPath("/");
        System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
        logger.info("Initilization has been fiished......................");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
