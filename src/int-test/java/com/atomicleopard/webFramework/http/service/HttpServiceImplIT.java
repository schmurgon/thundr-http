package com.atomicleopard.webFramework.http.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simpleframework.http.Cookie;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import com.atomicleopard.webFramework.http.service.HttpResponse;
import com.atomicleopard.webFramework.http.service.HttpServiceImpl;

public class HttpServiceImplIT {
	private static TestWebServer webServer;

	@BeforeClass
	public static void beforeClass() throws IOException {
		webServer = new BasicTestWebServer();
		webServer.start();
	}

	@AfterClass
	public static void afterClass() throws IOException {
		webServer.stop();
	}

	@Before
	public void before() {
		webServer.fatal.set(false);
	}

	@Test
	public void shouldPerformBasicHead() {
		HttpResponse response = new HttpServiceImpl().request(webServer.getUri()).header("Content-Type", "text/plain").parameter("q", "shoes").head();
		assertThat(response.getBody(), is(""));
		assertThat(response.getHeader("headHeader"), is("expected header"));
		assertThat(response.getHeader("q"), is("shoes"));
		assertThat(response.getCookie("headCookie").getValue(), is("expected cookie"));
		failIfWebserverErrors();
	}

	@Test
	public void shouldPerformBasicGet() {
		HttpResponse response = new HttpServiceImpl().request(webServer.getUri()).parameter("q", "shoes").get();
		assertThat(response.getBody(), is("Expected GET Result"));
		assertThat(response.getHeader("getHeader"), is("expected header"));
		assertThat(response.getHeader("q"), is("shoes"));
		assertThat(response.getCookie("getCookie").getValue(), is("expected cookie"));
		failIfWebserverErrors();
	}

	@Test
	public void shouldPerformBasicPost() {
		HttpResponse response = new HttpServiceImpl().request(webServer.getUri()).parameter("query", "queryParam").post();
		assertThat(response.getBody(), is("Expected POST Result"));
		assertThat(response.getHeader("postHeader"), is("expected header"));
		assertThat(response.getHeader("query"), is("queryParam"));
		assertThat(response.getCookie("postCookie").getValue(), is("expected cookie"));
		failIfWebserverErrors();
	}

	@Test
	public void shouldPerformBasicPut() {
		HttpResponse response = new HttpServiceImpl().request(webServer.getUri()).parameter("query", "queryParam").put();
		assertThat(response.getBody(), is("Expected PUT Result"));
		assertThat(response.getHeader("putHeader"), is("expected header"));
		assertThat(response.getHeader("query"), is("queryParam"));
		assertThat(response.getCookie("putCookie").getValue(), is("expected cookie"));
		failIfWebserverErrors();
	}

	@Test
	public void shouldPerformBasicDelete() {
		HttpResponse response = new HttpServiceImpl().request(webServer.getUri()).parameter("query", "queryParam").delete();
		assertThat(response.getBody(), is("Expected DELETE Result"));
		assertThat(response.getHeader("deleteHeader"), is("expected header"));
		assertThat(response.getHeader("query"), is("queryParam"));
		assertThat(response.getCookie("deleteCookie").getValue(), is("expected cookie"));
		failIfWebserverErrors();
	}

	@Test
	public void shouldPerformBasicOptions() {
		HttpResponse response = new HttpServiceImpl().request(webServer.getUri()).parameter("query", "queryParam").options();
		assertThat(response.getBody(), is("Expected OPTIONS Result"));
		assertThat(response.getHeader("optionsHeader"), is("expected header"));
		assertThat(response.getHeader("query"), is("queryParam"));
		assertThat(response.getCookie("optionsCookie").getValue(), is("expected cookie"));
		failIfWebserverErrors();
	}

	private void failIfWebserverErrors() {
		if (webServer.fatal.get()) {
			fail("Web server recorded a failure!");
		}
	}

	private static class BasicTestWebServer extends TestWebServer {
		@Override
		public void head(Request req, Response resp) throws Exception {
			resp.add("headHeader", "expected header");
			for (Map.Entry<String, String> queryParam : req.getQuery().entrySet()) {
				resp.add(queryParam.getKey(), queryParam.getValue());
			}
			resp.setCookie(new Cookie("headCookie", "expected cookie"));
			resp.setCode(200);
			resp.getPrintStream().print("Expected HEAD Result");
		}

		@Override
		public void get(Request req, Response resp) throws Exception {
			resp.add("getHeader", "expected header");
			for (Map.Entry<String, String> queryParam : req.getQuery().entrySet()) {
				resp.add(queryParam.getKey(), queryParam.getValue());
			}
			resp.setCookie(new Cookie("getCookie", "expected cookie"));
			resp.setCode(200);
			resp.getPrintStream().print("Expected GET Result");
		}

		@Override
		public void post(Request req, Response resp) throws Exception {
			resp.add("postHeader", "expected header");
			for (Map.Entry<String, String> param : req.getForm().entrySet()) {
				resp.add(param.getKey(), param.getValue());
			}
			resp.setCookie(new Cookie("postCookie", "expected cookie"));
			resp.setCode(200);
			resp.getPrintStream().print("Expected POST Result");
		}

		@Override
		public void put(Request req, Response resp) throws Exception {
			resp.add("putHeader", "expected header");
			for (Map.Entry<String, String> param : req.getForm().entrySet()) {
				resp.add(param.getKey(), param.getValue());
			}
			resp.setCookie(new Cookie("putCookie", "expected cookie"));
			resp.setCode(200);
			resp.getPrintStream().print("Expected PUT Result");
		}

		@Override
		public void delete(Request req, Response resp) throws Exception {
			resp.add("deleteHeader", "expected header");
			for (Map.Entry<String, String> queryParam : req.getQuery().entrySet()) {
				resp.add(queryParam.getKey(), queryParam.getValue());
			}
			resp.setCookie(new Cookie("deleteCookie", "expected cookie"));
			resp.setCode(200);
			resp.getPrintStream().print("Expected DELETE Result");
		}

		@Override
		public void options(Request req, Response resp) throws Exception {
			resp.add("optionsHeader", "expected header");
			for (Map.Entry<String, String> queryParam : req.getQuery().entrySet()) {
				resp.add(queryParam.getKey(), queryParam.getValue());
			}
			resp.setCookie(new Cookie("optionsCookie", "expected cookie"));
			resp.setCode(200);
			resp.getPrintStream().print("Expected OPTIONS Result");
		}
	}
}
