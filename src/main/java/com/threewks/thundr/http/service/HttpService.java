/*
 * This file is a component of thundr, a software library from 3wks.
 * Read more: http://www.3wks.com.au/thundr
 * Copyright (C) 2013 3wks, <thundr@3wks.com.au>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.threewks.thundr.http.service;

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
