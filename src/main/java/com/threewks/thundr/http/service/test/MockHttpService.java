package com.threewks.thundr.http.service.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.atomicleopard.expressive.ETransformer;
import com.threewks.thundr.http.service.HttpResponse;
import com.threewks.thundr.http.service.HttpService;

public class MockHttpService implements HttpService {
	private HttpResponse expect = new MockHttpResponse();

	public MockHttpService expected(HttpResponse expect) {
		this.expect = expect;
		return this;
	}

	@Override
	public MockHttpRequest request(String url) {
		return new MockHttpRequest(url, expect);
	}

	@Override
	public <T> InputStream convertOutgoing(T t) {
		return new ByteArrayInputStream(new byte[0]);
	}

	@Override
	public <T> T convertIncoming(InputStream is, Class<T> type) {
		return null;
	}

	@Override
	public <T> void addOutgoingTypeConvertor(Class<T> type, ETransformer<T, InputStream> transformer) {
	}

	@Override
	public <T> void addIncomingTypeConvertor(Class<T> type, ETransformer<InputStream, T> transformer) {
	}

}
