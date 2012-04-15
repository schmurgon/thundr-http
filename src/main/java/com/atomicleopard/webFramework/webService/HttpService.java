package com.atomicleopard.webFramework.webService;

import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;

public interface HttpService {
	public HttpRequest request(String url);
	public <T> InputStream convert(T t);
	public <T> void addTypeConvertor(Class<T> type, ETransformer<T, InputStream> convertor);
}
