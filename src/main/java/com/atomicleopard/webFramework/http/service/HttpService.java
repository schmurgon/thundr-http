package com.atomicleopard.webFramework.http.service;

import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;

public interface HttpService {
	public HttpRequest request(String url);

	/**
	 * Converts the given object to an InputStream reqdy to be written as the body of the request.
	 * Additional transformers can be registered by passing them to {@link #addOutgoingTypeConvertor(Class, ETransformer)}
	 */
	public <T> InputStream convertOutgoing(T t);

	/**
	 * Converts the given {@link InputStream} into an instance of the specified type using a registered incoming transformer.
	 * Additional transformers can be registered by passing them to #addIncomingTypeConvertor
	 */
	public <T> T convertIncoming(InputStream is, Class<T> type);

	/**
	 * Adds a transformer for the specified type which is able to transform objects of that type to a representation suitable
	 * for placing within a request body.
	 * 
	 * @param type
	 * @param transformer
	 */
	public <T> void addOutgoingTypeConvertor(Class<T> type, ETransformer<T, InputStream> transformer);

	/**
	 * Adds a transformer for the specified type which is able to transform a response body to instances of that type
	 * 
	 * @param type
	 * @param transformer
	 */
	public <T> void addIncomingTypeConvertor(Class<T> type, ETransformer<InputStream, T> transformer);
}
