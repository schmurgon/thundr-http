package com.atomicleopard.webFramework.webService;

import com.atomicleopard.webFramework.injection.UpdatableInjectionContext;
import com.atomicleopard.webFramework.logger.Logger;

public class WebServiceInjectionConfiguration implements com.atomicleopard.webFramework.injection.InjectionConfiguration {
	@Override
	public void configure(UpdatableInjectionContext injectionContext) {
		Logger.info("Loaded WebService module");
	}
}
