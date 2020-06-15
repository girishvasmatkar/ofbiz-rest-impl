package org.apache.ofbiz.ws.rs.spi.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.ofbiz.ws.rs.response.Error;
import org.apache.ofbiz.ws.rs.spi.AbstractExceptionMapper;

@Provider
public class GlobalExceptionMapper extends AbstractExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable throwable) {
		throwable.printStackTrace();
		Response.StatusType type = getStatusType(throwable);
		Error error = new Error(type.getStatusCode(), type.getReasonPhrase(), throwable.getMessage());
		return errorResponse(type.getStatusCode(), error);
	}

}
