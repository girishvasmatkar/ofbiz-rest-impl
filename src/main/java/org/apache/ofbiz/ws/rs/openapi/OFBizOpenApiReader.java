/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.apache.ofbiz.ws.rs.openapi;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiReader;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.tags.Tag;

public class OFBizOpenApiReader implements OpenApiReader {

	OpenAPIConfiguration openApiConfiguration;
	private Components components;
	private Paths paths;
	private Set<Tag> openApiTags;

	public OFBizOpenApiReader() {
		paths = new Paths();
		openApiTags = new LinkedHashSet<>();
		components = new Components();
	}

	@Override
	public void setConfiguration(OpenAPIConfiguration openApiConfiguration) {
		this.openApiConfiguration = openApiConfiguration;
	}

	@Override
	public OpenAPI read(Set<Class<?>> classes, Map<String, Object> resources) {
		OpenAPI openApi = openApiConfiguration.getOpenAPI();
		Tag serviceResourceTag = new Tag().name("exportedServices").description("OFBiz services that are exposed via REST interface with export attribute set to true");
		openApiTags.add(serviceResourceTag);
		openApi.setTags(new ArrayList<Tag>(openApiTags));
		return openApi;
	}

}
