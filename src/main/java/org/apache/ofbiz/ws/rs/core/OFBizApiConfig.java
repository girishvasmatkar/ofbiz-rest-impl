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
package org.apache.ofbiz.ws.rs.core;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.ofbiz.base.component.ComponentConfig;
import org.apache.ofbiz.base.component.ComponentException;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.ws.rs.OFBizEntityRequestHandler;
import org.apache.ofbiz.ws.rs.OFBizServiceRequestHandler;
import org.apache.ofbiz.ws.rs.bind.Api;
import org.apache.ofbiz.ws.rs.bind.Method;
import org.apache.ofbiz.ws.rs.util.XmlUtil;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;


public class OFBizApiConfig extends ResourceConfig {
    public static final String MODULE = OFBizApiConfig.class.getName();
    public OFBizApiConfig() {
        packages("org.apache.ofbiz.ws.rs.resources");
        packages("org.apache.ofbiz.ws.rs.security.auth");
        packages("org.apache.ofbiz.ws.rs.spi.impl");
        //packages("io.swagger.v3.jaxrs2.integration.resources"); //commenting it out to generate customized OpenApi Spec
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
        if (Debug.verboseOn()) {
            register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), Level.INFO,
                    LoggingFeature.Verbosity.PAYLOAD_ANY, 10000));
        }
        loadSchemaElements();
    }
    
    
    private void loadSchemaElements(){
        Collection<ComponentConfig> components = ComponentConfig.getAllComponents();
        components.forEach(component -> {
            String cName = component.getComponentName();
            try {
                String loc = ComponentConfig.getRootLocation(cName) + "/apidef";
                File folder = new File(loc);
                if (folder.isDirectory() && folder.exists()) {
                    File[] schemaFiles = folder.listFiles((dir, fileName) -> fileName.endsWith(".rest.xml"));
                    for (File schemaFile : schemaFiles) {
                        Debug.logInfo("API Resources file " + schemaFile.getName() + " was found in component " + cName, MODULE);
                        Api apiDef = null;
                        try {
                            apiDef = XmlUtil.loadApiDef(schemaFile);
                            //plainProcessApiDef(apiDef);
                            processApiDef(apiDef);
                        } catch (JAXBException e) {
                            e.printStackTrace();
                        }
                    }

                }
            } catch (ComponentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
    
    private void processApiDef(Api apiDef) {
        List<org.apache.ofbiz.ws.rs.bind.Resource> resources = apiDef.getResource();
        for (org.apache.ofbiz.ws.rs.bind.Resource resource : resources) {
            Resource.Builder resourceBuilder = Resource.builder(resource.getPath());
            resourceBuilder.name(resource.getName());
            List<Method> resourceMethods = resource.getMethod();
            for (Method method : resourceMethods) {
                if (UtilValidate.isEmpty(method.getPath())) { // Add the method to the parent resource
                    ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(method.getType());
                    methodBuilder.produces(MediaType.APPLICATION_JSON);
                    if (method.getEntity() != null) {
                        String entityName = method.getEntity().getName();
                        methodBuilder.handledBy(new OFBizEntityRequestHandler(entityName));
                    } else {
                        String serviceName = method.getService().getName();
                        methodBuilder.handledBy(new OFBizServiceRequestHandler(serviceName));
                    }
                } else { // Create a subresource with path
                    Resource.Builder childResourceBuilder = resourceBuilder.addChildResource(method.getPath());
                    ResourceMethod.Builder childResourceMethodBuilder = childResourceBuilder.addMethod(method.getType());
                    childResourceMethodBuilder.produces(MediaType.APPLICATION_JSON);
                    if (method.getEntity() != null) {
                        String entityName = method.getEntity().getName();
                        childResourceMethodBuilder.handledBy(new OFBizEntityRequestHandler(entityName));
                    } else {
                        String serviceName = method.getService().getName();
                        childResourceMethodBuilder.handledBy(new OFBizServiceRequestHandler(serviceName));
                    }
                }

            }      
            registerResources(resourceBuilder.build());
            
        }

    }
}
