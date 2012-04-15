package com.atomicleopard.webFramework.http.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.SocketConnection;

public class TestWebServer implements Container {
	public AtomicBoolean fatal = new AtomicBoolean(false);
	private SocketConnection connection;
	private int port = 9999;

	public final void start() throws IOException {
		connection = new SocketConnection(this);
		SocketAddress address = new InetSocketAddress(port);
		connection.connect(address);
	}

	public String getUri() {
		return "http://localhost:" + port;
	}

	public final void stop() throws IOException {
		connection.close();
	}

	@Override
	public void handle(Request req, Response resp) {
		try {
			if ("GET".equalsIgnoreCase(req.getMethod())) {
				get(req, resp);
			}
			if ("POST".equalsIgnoreCase(req.getMethod())) {
				post(req, resp);
			}
			if ("PUT".equalsIgnoreCase(req.getMethod())) {
				put(req, resp);
			}
			if ("DELETE".equalsIgnoreCase(req.getMethod())) {
				delete(req, resp);
			}
			if ("HEAD".equalsIgnoreCase(req.getMethod())) {
				head(req, resp);
			}
			if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
				options(req, resp);
			}
		} catch (Exception e) {
			resp.setCode(500);
			resp.setText(e.getMessage());
		}
		try {
			resp.commit();
			resp.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			fatal.set(true);
		}
	}

	public void get(Request req, Response resp) throws Exception {

	}

	public void post(Request req, Response resp) throws Exception {

	}

	public void put(Request req, Response resp) throws Exception {

	}

	public void delete(Request req, Response resp) throws Exception {

	}

	public void head(Request req, Response resp) throws Exception {

	}

	public void options(Request req, Response resp) throws Exception {

	}
}
