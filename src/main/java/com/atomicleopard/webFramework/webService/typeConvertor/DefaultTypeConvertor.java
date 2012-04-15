package com.atomicleopard.webFramework.webService.typeConvertor;

import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;

public class DefaultTypeConvertor implements ETransformer<Object, InputStream> {
	private static final StringTypeConvertor stringTypeConvertor = new StringTypeConvertor();

	@Override
	public InputStream to(Object from) {
		return stringTypeConvertor.to(from.toString());
	}

}
