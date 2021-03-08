package com.sample.filter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sample.annotation.TaxYearAware;
import com.sample.config.DIYDatabase;
import com.sample.config.DIYDatabaseContextHolder;

@Component
public class TaxYearHeadInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {

		boolean proceed = true;

		if (o instanceof HandlerMethod) {

			HandlerMethod handlerMethod = (HandlerMethod) o;
			Method method = handlerMethod.getMethod();

			if (method.isAnnotationPresent(TaxYearAware.class)) {
				
				System.out.println("reached interceptor.....");				
				String year = httpServletRequest.getParameter("taxyear");
				if(!StringUtils.isEmpty(year)){
					if(year.equalsIgnoreCase("2021"))
					{
						DIYDatabaseContextHolder.clear();
						DIYDatabaseContextHolder.set(DIYDatabase.TAX_2021);
					}
					else if(year.equalsIgnoreCase("2022"))
					{
						DIYDatabaseContextHolder.clear();
						DIYDatabaseContextHolder.set(DIYDatabase.TAX_2022);
					}
				}
				else
					DIYDatabaseContextHolder.set(DIYDatabase.TAX_2021);
			}
		}
		return proceed;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) {
	}

}
