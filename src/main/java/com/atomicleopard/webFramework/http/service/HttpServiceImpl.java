package com.atomicleopard.webFramework.http.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.atomicleopard.expressive.Cast;
import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.webFramework.exception.BaseException;
import com.atomicleopard.webFramework.http.service.typeTransformer.IncomingByteArrayTypeTransformer;
import com.atomicleopard.webFramework.http.service.typeTransformer.IncomingInputStreamTypeTransformer;
import com.atomicleopard.webFramework.http.service.typeTransformer.IncomingStringTypeTransformer;
import com.atomicleopard.webFramework.http.service.typeTransformer.OutgoingDefaultTypeTransformer;
import com.atomicleopard.webFramework.http.service.typeTransformer.OutgoingStringTypeTransformer;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;

public class HttpServiceImpl implements HttpService {
	private Map<Class<?>, ETransformer<?, InputStream>> outgoingTypeConvertors = new LinkedHashMap<Class<?>, ETransformer<?, InputStream>>();
	private List<Class<?>> outgoingTypeConvertorOrder = new ArrayList<Class<?>>();
	private Map<Class<?>, ETransformer<InputStream, ?>> incomingTypeConvertors = new HashMap<Class<?>, ETransformer<InputStream, ?>>();

	private URLFetchService fetchService;
	
	public HttpServiceImpl(URLFetchService fetchService) {
		this.fetchService = fetchService;
		// default outgoing transformers
		addOutgoingTypeConvertor(Object.class, new OutgoingDefaultTypeTransformer());
		addOutgoingTypeConvertor(String.class, new OutgoingStringTypeTransformer());

		// default incoming transformers
		addIncomingTypeConvertor(String.class, new IncomingStringTypeTransformer());
		addIncomingTypeConvertor(InputStream.class, new IncomingInputStreamTypeTransformer());
		addIncomingTypeConvertor(byte[].class, new IncomingByteArrayTypeTransformer());
	}

	public <T> void addIncomingTypeConvertor(Class<T> type, ETransformer<InputStream, T> convertor) {
		incomingTypeConvertors.put(type, convertor);
	}

	public <T> void addOutgoingTypeConvertor(Class<T> type, ETransformer<T, InputStream> convertor) {
		outgoingTypeConvertors.put(type, convertor);
		outgoingTypeConvertorOrder.add(type);
	}

	public HttpRequest request(String url) {
		return new HttpRequestImpl(this, url);
	}

	@SuppressWarnings("unchecked")
	public <T> InputStream convertOutgoing(T t) {
		for (int i = outgoingTypeConvertorOrder.size() - 1; i >= 0; i--) {
			Class<?> type = outgoingTypeConvertorOrder.get(i);
			if (Cast.is(t, type)) {
				ETransformer<T, InputStream> transformer = (ETransformer<T, InputStream>) outgoingTypeConvertors.get(type);
				return transformer.to(t);
			}
		}
		throw new BaseException("Unable to convert the given object to an input stream, no convertor found. Object: %s", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convertIncoming(InputStream is, Class<T> type) {
		ETransformer<InputStream, ?> convertor = incomingTypeConvertors.get(type);
		if (convertor == null) {
			throw new BaseException("Unable to convert the response to the type %s, please make sure a convertor is registered", type.getName());
		}
		return (T) convertor.to(is);
	}

	public HttpResponseImpl fetch(HTTPRequest request) {
		Future<HTTPResponse> fetchAsync = fetchService.fetchAsync(request);
		return new HttpResponseImpl(fetchAsync, this);
	}
}
