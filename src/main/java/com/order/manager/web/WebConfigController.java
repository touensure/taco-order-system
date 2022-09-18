package com.order.manager.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfigController implements WebMvcConfigurer
{
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login");
		/* this path is for testing SecurityConfig.java, firstly visit "/design" or "/abc", user will be redirected to "/login",
		 * after log in succeed, user will be redirected to the firstly visited path.*/
		registry.addViewController("/abc").setViewName("tempTestPage");
	}
}
