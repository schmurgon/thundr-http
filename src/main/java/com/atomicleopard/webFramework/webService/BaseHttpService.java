package com.atomicleopard.webFramework.webService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.atomicleopard.expressive.Cast;
import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.webFramework.exception.BaseException;
import com.atomicleopard.webFramework.webService.typeConvertor.DefaultTypeConvertor;
import com.atomicleopard.webFramework.webService.typeConvertor.StringTypeConvertor;

/**
 * The whole web service module is unfinished and untested.
 */
@Deprecated
public class BaseHttpService implements HttpService {
	private Map<Class<?>, ETransformer<?, InputStream>> typeConvertors = new LinkedHashMap<Class<?>, ETransformer<?, InputStream>>();
	private List<Class<?>> typeConvertorOrder = new ArrayList<Class<?>>();

	public BaseHttpService() {
		addTypeConvertor(Object.class, new DefaultTypeConvertor());
		addTypeConvertor(String.class, new StringTypeConvertor());
	}

	public <T> void addTypeConvertor(Class<T> type, ETransformer<T, InputStream> convertor) {
		typeConvertors.put(type, convertor);
		typeConvertorOrder.add(type);
	}

	public HttpRequest request(String url) {
		return new BasicHttpRequest(this, url);
	}

	public <T> InputStream convert(T t) {
		for (int i = typeConvertorOrder.size(); i >= 0; i--) {
			Class<?> type = typeConvertorOrder.get(i);
			if (Cast.is(t, type)) {
				ETransformer<T, InputStream> transformer = (ETransformer<T, InputStream>) typeConvertors.get(type);
				return transformer.to(t);
			}
		}
		throw new BaseException("Unable to convert the given object to an input stream, no convertor found. Object: %s", t);
	}
}
