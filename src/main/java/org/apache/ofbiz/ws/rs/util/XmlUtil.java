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
package org.apache.ofbiz.ws.rs.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.ofbiz.ws.rs.bind.Api;

public class XmlUtil {

	public static Api loadApiDef(File file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Api.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Api api = (Api) unmarshaller.unmarshal(file);
		return api;
	}

	public static Api loadApiDef(String fileLoc) throws JAXBException {
		File file = new File(fileLoc);
		return loadApiDef(file);
	}
}
