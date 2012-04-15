package com.atomicleopard.webFramework.http.service;

import com.atomicleopard.webFramework.injection.UpdatableInjectionContext;
import com.atomicleopard.webFramework.logger.Logger;

public class ServiceInjectionConfiguration implements com.atomicleopard.webFramework.injection.InjectionConfiguration {
	@Override
	public void configure(UpdatableInjectionContext injectionContext) {
		Logger.info("Loaded WebService module");
	}
}
