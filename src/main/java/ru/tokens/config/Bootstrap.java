package ru.tokens.config;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.tokens.site.controller.AuthenticationFilter;
import ru.tokens.site.controller.CharsetFilter;
import ru.tokens.site.controller.LoggingFilter;

/**
 *
 * @author solon4ak
 */
public class Bootstrap implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        container.getServletRegistration("default").addMapping("/resource/*");

        AnnotationConfigWebApplicationContext rootContext = 
                new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext servletContext = 
                new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfiguration.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet(
                "springDispatcher",
                new DispatcherServlet(servletContext)
        );
        dispatcher.setLoadOnStartup(1);
        // file size up to 5 mb / request size up to 40 mb
        dispatcher.setMultipartConfig(
                new MultipartConfigElement(null, 5_242_880L, 41_943_040L, 512_000)
        );
        dispatcher.addMapping("/");

        FilterRegistration.Dynamic registration = container.addFilter(
                "loggingFilter", new LoggingFilter()
        );
        registration.addMappingForUrlPatterns(null, false, "/*");
        
        registration = container.addFilter(
                "charsetFilter", new CharsetFilter()
        );
        registration.addMappingForUrlPatterns(null, false, "/*");

        registration = container.addFilter(
                "authenticationFilter", new AuthenticationFilter()
        );
        registration.addMappingForUrlPatterns(
                null, false, 
                "/token/user", "/token/user/*",
                "/token/register", "/token/register/*",
                "/token/add/user", "/token/add/user/*",
                "/session", "/session/*",
                "/user/view", "/user/view/*",
                "/user/edit", "/user/edit/*",
                "/admin", "/admin/*"
        );
        
        
        /*, "/token/user/edit/*",
                "/token/user/add", "/token/user/add/*",
                "/token/user/view", "/token/user/view/*",
                "/token/user/address", "/token/user/address/*",
                "/token/user/contact", "/token/user/contact/*",
                "/token/user/med", "/token/user/med/*"*/
    }

}
