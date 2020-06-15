package org.apache.ofbiz.ws.rs.spi.impl;

import java.io.IOException;
import java.util.Map.Entry;

import javax.ws.rs.core.Link;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LinkSerializer extends JsonSerializer<javax.ws.rs.core.Link> {
	static final String HREF_PROPERTY = "href";

	@Override
	public void serialize(Link link, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField(HREF_PROPERTY, link.getUri().toString());
		for (Entry<String, String> entry : link.getParams().entrySet()) {
			jsonGenerator.writeStringField(entry.getKey(), entry.getValue());
		}
		jsonGenerator.writeEndObject();
	}
}
