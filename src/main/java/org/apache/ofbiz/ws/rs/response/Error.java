package org.apache.ofbiz.ws.rs.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "error")
public class Error {

	private int statusCode;
	private String statusDescription;
	private String errorMessage;
	private List<String> additionalErrors;

	public Error(int statusCode, String statusDescription, String errorMessage) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.errorMessage = errorMessage;
	}

	public Error(int statusCode, String statusDescription, String errorMessage, List<String> additionalErrors) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.errorMessage = errorMessage;
		this.additionalErrors = additionalErrors;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public List<String> getAdditionalErrors() {
		return additionalErrors;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
