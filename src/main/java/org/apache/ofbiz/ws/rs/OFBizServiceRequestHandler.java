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
package org.apache.ofbiz.ws.rs;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.ofbiz.service.LocalDispatcher;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.model.ResourceMethod;

public class OFBizServiceRequestHandler implements Inflector<ContainerRequestContext, Response> {

	private String serviceName;

	public OFBizServiceRequestHandler(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public OFBizServiceRequestHandler() {
       
    }

	@Inject
    HttpHeaders httpHeaders;
    @Inject
    UriInfo uriInfo;
    
    @Inject
    ExtendedUriInfo extendedUriInfo;
    
    @Inject
    ResourceInfo resourceInfo;
    
    @Inject
    private ServletContext servletContext;

	@Override
	//TODO : This method will have all the action.
	public Response apply(final ContainerRequestContext data) { 
		System.out.println("serviceName to be invked: " + serviceName);
		ResourceMethod resM = extendedUriInfo.getMatchedResourceMethod();
		if (resM != null) {
			System.out.println("Produced Type: " + resM.getProducedTypes().get(0));
		}
		System.out.println("Path: " + uriInfo.getPath());
		System.out.println("Method: " + data.getMethod());
		System.out.println("CONTENT_TYPE Header: " + httpHeaders.getHeaderString((HttpHeaders.CONTENT_TYPE)));
		System.out.println("Accept Header: " + httpHeaders.getHeaderString((HttpHeaders.ACCEPT)));
		String path = uriInfo.getPath();
		String serviceName = path.substring(path.indexOf("/") + 1, path.length());
		System.out.println("serviceName " + serviceName);
		LocalDispatcher dispatcher = (LocalDispatcher) servletContext.getAttribute("dispatcher");
		System.out.println("this : " + this);
		System.out.println("Path Params: "+uriInfo.getPathParameters());
		System.out.println("Thred "+Thread.currentThread().getId());
		return null;
	}

}
