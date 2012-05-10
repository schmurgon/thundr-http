package com.threewks.thundr.http.service;

import com.threewks.thundr.injection.UpdatableInjectionContext;
import com.threewks.thundr.logger.Logger;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class ServiceInjectionConfiguration implements com.threewks.thundr.injection.InjectionConfiguration {
	@Override
	public void configure(UpdatableInjectionContext injectionContext) {
		URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
		injectionContext.inject(HttpService.class).as(new HttpServiceImpl(urlFetchService));
		Logger.info("Loaded HttpService module");
	}
}
