package com.threewks.thundr.http.service;

import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.logger.Logger;

public class ServiceInjectionConfiguration implements com.threewks.thundr.injection.InjectionConfiguration {
	@Override
	public void configure(UpdatableInjectionContext injectionContext) {
		URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
		injectionContext.inject(URLFetchService.class).as(urlFetchService);
		injectionContext.inject(HttpService.class).as(HttpServiceImpl.class);
		Logger.info("Loaded HttpService module");
	}
}
