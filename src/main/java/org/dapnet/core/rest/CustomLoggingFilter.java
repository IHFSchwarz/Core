/*
 * DAPNET CORE PROJECT
 * Copyright (C) 2016
 *
 * Daniel Sialkowski
 *
 * daniel.sialkowski@rwth-aachen.de
 *
 * Institute of High Frequency Technology
 * RWTH AACHEN UNIVERSITY
 * Melatener Str. 25
 * 52074 Aachen
 */

package org.dapnet.core.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.message.internal.ReaderWriter;

@Provider
public class CustomLoggingFilter extends LoggingFeature implements ContainerRequestFilter, ContainerResponseFilter {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		StringBuilder sb = new StringBuilder();

		// Append username is available
		String user = null;
		try {
			user = new LoginData(requestContext.getHeaders().get("Authorization").get(0)).getUsername();
		} catch (Exception e) {
		}

		sb.append("User: ").append(user);

		sb.append(" - Path: ").append(requestContext.getUriInfo().getPath());
		sb.append(" - Header: ").append(requestContext.getHeaders());

		// Append entity shortened to 10000 chars
		String entity = getEntityBody(requestContext);
		entity = entity.replace("\n", "").replace("\r", "");
		if (entity.length() > 10001) {
			entity.substring(0, 9999);
		}
		sb.append(" - Entity: ").append(entity);

		logger.info("REST " + requestContext.getMethod() + " Request : " + sb.toString());
	}

	private String getEntityBody(ContainerRequestContext requestContext) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = requestContext.getEntityStream();
		final StringBuilder b = new StringBuilder();
		try {
			ReaderWriter.writeTo(in, out);
			byte[] requestEntity = out.toByteArray();
			if (requestEntity.length == 0) {
				b.append("");
			} else {
				b.append(new String(requestEntity));
			}
			requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
		} catch (Exception ex) {
			return "";
		}

		return b.toString();
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		addHeader(responseContext);

		StringBuilder sb = new StringBuilder();
		sb.append("Header: ").append(responseContext.getHeaders());
		sb.append(" - Entity: ");
		if (responseContext != null && responseContext.getEntity() != null) {
			sb.append(responseContext.getEntity().toString().replace("\n", "").replace("\r", ""));
		} else {
			sb.append("null");
		}

		if (responseContext.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL
				|| responseContext.getStatusInfo().getFamily() == Response.Status.Family.CLIENT_ERROR) {
			logger.info("REST " + requestContext.getMethod() + " Response : " + sb.toString());
		} else {
			logger.error("REST " + requestContext.getMethod() + " Response : " + sb.toString());
		}
	}

	// Add Header to allow Web Module running on other server than DAPNET Core
	private void addHeader(ContainerResponseContext responseContext) {
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
	}
}