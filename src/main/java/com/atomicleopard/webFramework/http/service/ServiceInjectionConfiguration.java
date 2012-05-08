package com.atomicleopard.webFramework.http.service;

import com.atomicleopard.webFramework.injection.UpdatableInjectionContext;
import com.atomicleopard.webFramework.logger.Logger;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class ServiceInjectionConfiguration implements com.atomicleopard.webFramework.injection.InjectionConfiguration {
	@Override
	public void configure(UpdatableInjectionContext injectionContext) {
		URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
		injectionContext.inject(HttpService.class).as(new HttpServiceImpl(urlFetchService));
		Logger.info("Loaded HttpService module");
	}
}
