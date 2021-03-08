package com.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sample.filter.TaxYearHeadInterceptor;

@SpringBootApplication
public class MultidbApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(MultidbApplication.class, args);
	}

	@Configuration
	public class WebMvcConfig extends WebMvcConfigurerAdapter {

	  @Autowired 
	  TaxYearHeadInterceptor taxYearHeadInterceptor;

	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {

	    registry.addInterceptor(taxYearHeadInterceptor); 

	  }
	}
}
