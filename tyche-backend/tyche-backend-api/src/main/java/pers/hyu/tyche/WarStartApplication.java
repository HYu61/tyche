package pers.hyu.tyche;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Run the application from the WAR deployment.
 *
 * @author Heng Yu
 * @version 1.0
 */
public class WarStartApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TycheApplication.class); // use the web.xml run the application and bind TycheApplication ,then start springboot.
    }
}
