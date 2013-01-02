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
package com.threewks.thundr.http.service.typeTransformer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.atomicleopard.expressive.ETransformer;
import com.threewks.thundr.exception.BaseException;

public class OutgoingStringTypeTransformer implements ETransformer<String, InputStream> {
	private static final String encoding = "UTF-8";

	@Override
	public InputStream from(String from) {
		return new ByteArrayInputStream(toBytes(from));
	}

	public static byte[] toBytes(String string) {
		try {
			return string.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new BaseException(e, "Failed to convert string to bytes - this platform doesnt support %s: %s", encoding, e.getMessage());
		}
	}

}
