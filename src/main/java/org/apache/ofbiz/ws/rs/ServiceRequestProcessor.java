package org.apache.ofbiz.ws.rs;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ModelParam;
import org.apache.ofbiz.service.ModelService;
import org.apache.ofbiz.service.ServiceUtil;
import org.apache.ofbiz.ws.rs.util.RestApiUtil;

public class ServiceRequestProcessor {

	public Response process(Map<String, Object> requestContext) throws GenericServiceException {
		String serviceName = (String) requestContext.get("serviceName");
		String httpVerb = (String) requestContext.get("httpVerb");
		Map<String, Object> requestMap = (Map<String, Object>) requestContext.get("requestMap");
		LocalDispatcher dispatcher = (LocalDispatcher) requestContext.get("dispatcher");
		HttpServletRequest request = (HttpServletRequest) requestContext.get("request");
		GenericValue userLogin = (GenericValue)request.getAttribute("userLogin");
		DispatchContext dispatchContext = dispatcher.getDispatchContext();
		ModelService service = null;
		try {
			service = dispatchContext.getModelService(serviceName);
		} catch (GenericServiceException gse) {
			throw new NotFoundException(gse.getMessage());
		}
		if (UtilValidate.isNotEmpty(service.verb) && !service.verb.equalsIgnoreCase(httpVerb)) {
			throw new MethodNotAllowedException("HTTP "+httpVerb+" is not allowed on this service.");
		}
		Map<String, Object> serviceContext = dispatchContext.makeValidContext(serviceName, ModelService.IN_PARAM, requestMap);
		serviceContext.put("userLogin", userLogin);
		Map<String, Object> result = dispatcher.runSync(serviceName, serviceContext);
		Map<String, Object> responseData = new LinkedHashMap<>();
		if (ServiceUtil.isSuccess(result)) {
			Set<String> outParams = service.getOutParamNames();
			for (String outParamName : outParams) {
				ModelParam outParam = service.getParam(outParamName);
				if (!outParam.internal) {
					Object value = result.get(outParamName);
					if (UtilValidate.isNotEmpty(value)) {
						responseData.put(outParamName, value);
					}
				}
			}
			return RestApiUtil.success((String) result.get(ModelService.SUCCESS_MESSAGE), responseData);
		} else {
			return RestApiUtil.error(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), (String) result.get(ModelService.ERROR_MESSAGE));
		}
	}
}
