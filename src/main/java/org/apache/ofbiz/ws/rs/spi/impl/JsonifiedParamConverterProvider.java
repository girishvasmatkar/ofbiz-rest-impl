package org.apache.ofbiz.ws.rs.spi.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.apache.ofbiz.ws.rs.ApiServiceRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class JsonifiedParamConverterProvider implements ParamConverterProvider {
	private static final ObjectMapper mapper = new ObjectMapper();

	private static ObjectMapper getMapper() {
		return mapper;
	}

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (rawType.getName().equals(ApiServiceRequest.class.getName())) {
			return new ParamConverter<T>() {
				@SuppressWarnings("unchecked")
				@Override
				public T fromString(String value) {
					Map<String, Object> map = null;
					try {
						map = getMapper().readValue(value, new TypeReference<Map<String, Object>>() {
						});
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
					return (T) new ApiServiceRequest(map);
				}

				@Override
				public String toString(T map) {
					return ((ApiServiceRequest) map).getInParams().toString();
				}
			};
		}
		return null;
	}
}
