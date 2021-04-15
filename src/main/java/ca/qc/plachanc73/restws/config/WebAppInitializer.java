package ca.qc.plachanc73.restws.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext rootContext = createRootContext(servletContext);

		configureSpringMvc(servletContext, rootContext);
	}

	private static WebApplicationContext createRootContext(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebMvcConfigurer.class);
		servletContext.addListener(new ContextLoaderListener(context));
		return context;
	}

	private static void configureSpringMvc(ServletContext servletContext, WebApplicationContext rootContext) {
		DispatcherServlet dispatcher = new DispatcherServlet(rootContext);
		ServletRegistration.Dynamic servlet = servletContext
				.addServlet(AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME, dispatcher);
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
}