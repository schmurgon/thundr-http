package com.atomicleopard.webFramework.http.service;

import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.webFramework.http.Cookie;

class NingTransformers {
	protected static ETransformer<Cookie, com.ning.http.client.Cookie> toNingCookie = new ETransformer<Cookie, com.ning.http.client.Cookie>() {
		@Override
		public com.ning.http.client.Cookie to(Cookie from) {
			com.ning.http.client.Cookie cookie = new com.ning.http.client.Cookie(from.getDomain(), from.getName(), from.getValue(), from.getPath(), from.getMaxAge(), from.isSecure(),
					from.getVersion());
			cookie.setPorts(from.getPorts());
			return cookie;
		}
	};
	protected static ETransformer<com.ning.http.client.Cookie, Cookie> fromNingCookie = new ETransformer<com.ning.http.client.Cookie, Cookie>() {
		@Override
		public Cookie to(com.ning.http.client.Cookie from) {
			Cookie cookie = new Cookie(from.getDomain(), from.getName(), from.getValue(), from.getPath(), from.getMaxAge(), from.isSecure(), from.getVersion());
			cookie.setPorts(from.getPorts());
			return cookie;
		}
	};
}
