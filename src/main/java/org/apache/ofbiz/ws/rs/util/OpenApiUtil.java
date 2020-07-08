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

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.StringSchema;

public class OpenApiUtil {

	private static final Map<String, String> classAlias = new HashMap<>();
	private static final Map<String, Class<?>> javaToOpenAPiMap = new HashMap<>();

	static {
		classAlias.put("String", "String");
		classAlias.put("java.lang.String", "String");
		classAlias.put("CharSequence", "String");
		classAlias.put("java.lang.CharSequence", "String");
		classAlias.put("Date", "String");
		classAlias.put("java.sql.Date", "String");
		classAlias.put("Time", "String");
		classAlias.put("java.sql.Time", "String");
		classAlias.put("Timestamp", "Timestamp");
		classAlias.put("java.sql.Timestamp", "Timestamp");
		classAlias.put("Integer", "Int");
		classAlias.put("java.lang.Integer", "Int");
		classAlias.put("Long", "Long");
		classAlias.put("java.lang.Long", "Long");
		classAlias.put("BigInteger", "BigInteger");
		classAlias.put("java.math.BigInteger", "BigInteger");
		classAlias.put("Float", "Float");
		classAlias.put("java.lang.Float", "Float");
		classAlias.put("Double", "Float");
		classAlias.put("java.lang.Double", "Float");
		classAlias.put("BigDecimal", "BigDecimal");
		classAlias.put("java.math.BigDecimal", "BigDecimal");
		classAlias.put("Boolean", "Boolean");
		classAlias.put("java.lang.Boolean", "Boolean");
		
		classAlias.put("org.apache.ofbiz.entity.GenericValue", "GenericValue");
		classAlias.put("GenericValue", "GenericValue");
	    classAlias.put("GenericPK", "GenericPK");
	    classAlias.put("org.apache.ofbiz.entity.GenericPK", "GenericPK");
	    classAlias.put("org.apache.ofbiz.entity.GenericEntity", "GenericEntity");
	    classAlias.put("GenericEntity", "GenericEntity");

		classAlias.put("java.util.List", "List");
		classAlias.put("List", "List");
		classAlias.put("java.util.Set", "Set");
		classAlias.put("Set", "Set");
		classAlias.put("java.util.Map", "Map");
		classAlias.put("Map", "Map");
		classAlias.put("java.util.HashMap", "HashMap");
		classAlias.put("HashMap", "HashMap");

		javaToOpenAPiMap.put("String", StringSchema.class);
		javaToOpenAPiMap.put("Integer", IntegerSchema.class);
		javaToOpenAPiMap.put("Long", IntegerSchema.class);
		javaToOpenAPiMap.put("Map", MapSchema.class);
		javaToOpenAPiMap.put("GenericEntity", MapSchema.class);
		javaToOpenAPiMap.put("GenericPK", MapSchema.class);
		javaToOpenAPiMap.put("GenericValue", MapSchema.class);
		javaToOpenAPiMap.put("HashMap", MapSchema.class);
		javaToOpenAPiMap.put("List", ArraySchema.class);
		javaToOpenAPiMap.put("Float", NumberSchema.class);
		javaToOpenAPiMap.put("Double", NumberSchema.class);
		javaToOpenAPiMap.put("BigDecimal", NumberSchema.class);
		javaToOpenAPiMap.put("Timestamp", DateSchema.class);

	}
	
	
	public static Class<?> getOpenApiSchema(String type) {
		return javaToOpenAPiMap.get(classAlias.get(type));
	}
}
