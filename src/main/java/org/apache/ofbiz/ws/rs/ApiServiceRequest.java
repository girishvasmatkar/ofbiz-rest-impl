package org.apache.ofbiz.ws.rs;

import java.util.Map;

public class ApiServiceRequest {
	private Map<String, Object> inParams;

	public Map<String, Object> getInParams() {
		return inParams;
	}

	public ApiServiceRequest(Map<String, Object> inParams) {
		this.inParams = inParams;
	}

	@Override
	public String toString() {
		return inParams != null ? inParams.toString() : super.toString();
	}
}
