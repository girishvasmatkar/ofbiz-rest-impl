package org.apache.ofbiz.ws.rs.spi;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.ofbiz.ws.rs.response.Error;

/*
 * 
 */
public class AbstractExceptionMapper {
	protected Response errorResponse(int status, Error responseEntity) {
		return customizeResponse(status, responseEntity);
	}

	protected Response errorResponse(int status, Error responseEntity, Throwable t) {
		return customizeResponse(status, responseEntity);
	}

	protected Response.StatusType getStatusType(Throwable ex) {
		if (ex instanceof WebApplicationException) {
			return ((WebApplicationException) ex).getResponse().getStatusInfo();
		} else {
			return Response.Status.INTERNAL_SERVER_ERROR;
		}
	}

	private Response customizeResponse(int status, Error responseEntity) {
		return Response.status(status).entity(responseEntity).type(MediaType.APPLICATION_JSON).build();
	}

}
