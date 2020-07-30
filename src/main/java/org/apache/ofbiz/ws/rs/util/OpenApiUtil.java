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
import java.util.List;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.model.ModelEntity;
import org.apache.ofbiz.entity.model.ModelField;
import org.apache.ofbiz.service.ModelParam;
import org.apache.ofbiz.service.ModelService;
import org.apache.ofbiz.webapp.WebAppUtil;
import org.apache.ofbiz.ws.rs.listener.ApiContextListener;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

public final class OpenApiUtil {

    private static final String MODULE = OpenApiUtil.class.getName();

    private OpenApiUtil() {

    }

    private static final Map<String, String> CLASS_ALIAS = new HashMap<>();
    private static final Map<String, Class<?>> JAVA_OPEN_API_MAP = new HashMap<>();
    private static final Map<String, String> FIELD_TYPE_MAP = new HashMap<String, String>();

    static {
        CLASS_ALIAS.put("String", "String");
        CLASS_ALIAS.put("java.lang.String", "String");
        CLASS_ALIAS.put("CharSequence", "String");
        CLASS_ALIAS.put("java.lang.CharSequence", "String");
        CLASS_ALIAS.put("Date", "String");
        CLASS_ALIAS.put("java.sql.Date", "String");
        CLASS_ALIAS.put("Time", "String");
        CLASS_ALIAS.put("java.sql.Time", "String");
        CLASS_ALIAS.put("Timestamp", "Timestamp");
        CLASS_ALIAS.put("java.sql.Timestamp", "Timestamp");
        CLASS_ALIAS.put("Integer", "Integer");
        CLASS_ALIAS.put("java.lang.Integer", "Integer");
        CLASS_ALIAS.put("Long", "Long");
        CLASS_ALIAS.put("java.lang.Long", "Long");
        CLASS_ALIAS.put("BigInteger", "BigInteger");
        CLASS_ALIAS.put("java.math.BigInteger", "BigInteger");
        CLASS_ALIAS.put("Float", "Float");
        CLASS_ALIAS.put("java.lang.Float", "Float");
        CLASS_ALIAS.put("Double", "Float");
        CLASS_ALIAS.put("java.lang.Double", "Float");
        CLASS_ALIAS.put("BigDecimal", "BigDecimal");
        CLASS_ALIAS.put("java.math.BigDecimal", "BigDecimal");
        CLASS_ALIAS.put("Boolean", "Boolean");
        CLASS_ALIAS.put("java.lang.Boolean", "Boolean");

        CLASS_ALIAS.put("org.apache.ofbiz.entity.GenericValue", "GenericValue");
        CLASS_ALIAS.put("GenericValue", "GenericValue");
        CLASS_ALIAS.put("GenericPK", "GenericPK");
        CLASS_ALIAS.put("org.apache.ofbiz.entity.GenericPK", "GenericPK");
        CLASS_ALIAS.put("org.apache.ofbiz.entity.GenericEntity", "GenericEntity");
        CLASS_ALIAS.put("GenericEntity", "GenericEntity");

        CLASS_ALIAS.put("java.util.List", "List");
        CLASS_ALIAS.put("List", "List");
        CLASS_ALIAS.put("java.util.Set", "Set");
        CLASS_ALIAS.put("Set", "Set");
        CLASS_ALIAS.put("java.util.Map", "Map");
        CLASS_ALIAS.put("Map", "Map");
        CLASS_ALIAS.put("java.util.HashMap", "HashMap");
        CLASS_ALIAS.put("HashMap", "HashMap");

        JAVA_OPEN_API_MAP.put("String", StringSchema.class);
        JAVA_OPEN_API_MAP.put("Integer", IntegerSchema.class);
        JAVA_OPEN_API_MAP.put("Long", IntegerSchema.class);
        JAVA_OPEN_API_MAP.put("Boolean", BooleanSchema.class);
        JAVA_OPEN_API_MAP.put("Map", MapSchema.class);
        JAVA_OPEN_API_MAP.put("GenericEntity", MapSchema.class);
        JAVA_OPEN_API_MAP.put("GenericPK", MapSchema.class);
        JAVA_OPEN_API_MAP.put("GenericValue", MapSchema.class);
        JAVA_OPEN_API_MAP.put("HashMap", MapSchema.class);
        JAVA_OPEN_API_MAP.put("List", ArraySchema.class);
        JAVA_OPEN_API_MAP.put("Set", ArraySchema.class);
        JAVA_OPEN_API_MAP.put("Collection", ArraySchema.class);
        JAVA_OPEN_API_MAP.put("Float", NumberSchema.class);
        JAVA_OPEN_API_MAP.put("Double", NumberSchema.class);
        JAVA_OPEN_API_MAP.put("BigDecimal", NumberSchema.class);
        JAVA_OPEN_API_MAP.put("BigInteger", IntegerSchema.class);
        JAVA_OPEN_API_MAP.put("Timestamp", DateSchema.class);

        FIELD_TYPE_MAP.put("id", "String");
        FIELD_TYPE_MAP.put("indicator", "String");
        FIELD_TYPE_MAP.put("date", "String");
        FIELD_TYPE_MAP.put("id-vlong", "String");
        FIELD_TYPE_MAP.put("description", "String");
        FIELD_TYPE_MAP.put("numeric", "Int"); //
        FIELD_TYPE_MAP.put("long-varchar", "String");
        FIELD_TYPE_MAP.put("id-long", "String");
        FIELD_TYPE_MAP.put("currency-amount", "BigDecimal");
        FIELD_TYPE_MAP.put("value", "value");
        FIELD_TYPE_MAP.put("email", "String");
        FIELD_TYPE_MAP.put("currency-precise", "BigDecimal");
        FIELD_TYPE_MAP.put("very-short", "String");
        FIELD_TYPE_MAP.put("date-time", "Timestamp");
        FIELD_TYPE_MAP.put("credit-card-date", "String");
        FIELD_TYPE_MAP.put("url", "String");
        FIELD_TYPE_MAP.put("credit-card-number", "String");
        FIELD_TYPE_MAP.put("fixed-point", "BigDecimal");
        FIELD_TYPE_MAP.put("name", "String");
        FIELD_TYPE_MAP.put("short-varchar", "String");
        FIELD_TYPE_MAP.put("comment", "String");
        FIELD_TYPE_MAP.put("time", "String");
        FIELD_TYPE_MAP.put("very-long", "String");
        FIELD_TYPE_MAP.put("floating-point", "Float");
        FIELD_TYPE_MAP.put("object", "Byte");
        FIELD_TYPE_MAP.put("byte-array", "Byte");
        FIELD_TYPE_MAP.put("blob", "Byte");

    }

    public static Class<?> getOpenApiTypeForAttributeType(String attributeType) {
        return JAVA_OPEN_API_MAP.get(CLASS_ALIAS.get(attributeType));
    }

    public static Class<?> getOpenApiTypeForFieldType(String fieldType) {
        return JAVA_OPEN_API_MAP.get(FIELD_TYPE_MAP.get(fieldType));
    }

    public static Schema<Object> getInSchema(ModelService service) {
        Schema<Object> parentSchema = new Schema<Object>();
        parentSchema.setDescription("In Schema for service: " + service.name + " request");
        parentSchema.setType("object");
        service.getInParamNamesMap().forEach((name, type) -> {
            parentSchema.addProperties(name, getAttributeSchema(service, service.getParam(name)));
        });
        return parentSchema;
    }

    private static Schema<?> getAttributeSchema(ModelService service, ModelParam param) {
        Debug.log(
                "Attribute '" + param.name + "' and type '" + param.type + "'",
                MODULE);
        Schema<?> schema = null;
        Class<?> schemaClass = getOpenApiTypeForAttributeType(param.type);
        if (schemaClass == null) {
            Debug.logWarning(
                    "Attribute '" + param.name + "' ignored as it is declared as '" + param.type + "' and corresponding OpenApi Type Mapping not found.",
                    MODULE);
            return null;
        }
        try {
            schema = (Schema<?>) schemaClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        List<ModelParam> children = param.getChildList();
        Delegator delegator = WebAppUtil.getDelegator(ApiContextListener.getApplicationCntx());
        if (schema instanceof ArraySchema) {
            ((ArraySchema) schema).setItems(getAttributeSchema(service, children.get(0)));
        } else if (schema instanceof MapSchema) {
            if (isTypeGenericEntityOrGenericValue(param.type)) {
                if (UtilValidate.isEmpty(param.entityName)) {
                    Debug.logWarning(
                            "Attribute '" + param.name + "' ignored as it is declared as '" + param.type + "' but does not have entity-name defined.",
                            MODULE);
                    return null;
                } else {
                    schema = getSchemaForEntity(delegator.getModelEntity(param.entityName));
                }
            } else if (UtilValidate.isEmpty(param.getChildList())) {
                Debug.logWarning(
                        "Attribute '" + param.name + "' ignored as it is declared as '" + param.type + "' but does not have any child attributes.",
                        MODULE);
                return null;
            } else {
                for (ModelParam childParam : children) {
                    schema.addProperties(childParam.name, getAttributeSchema(service, childParam));
                }

            }

        }
        return schema;
    }

    public static Schema<Object> getOutSchema(ModelService service) {
        Schema<Object> parentSchema = new Schema<Object>();
        parentSchema.setDescription("Out Schema for service: " + service.name + " response");
        parentSchema.setType("object");
        parentSchema.addProperties("statusCode", new IntegerSchema().description("HTTP Status Code"));
        parentSchema.addProperties("statusDescription", new StringSchema().description("HTTP Status Code Description"));
        parentSchema.addProperties("successMessage", new StringSchema().description("Success Message"));
        ObjectSchema dataSchema = new ObjectSchema();
        parentSchema.addProperties("data", dataSchema);
        service.getOutParamNamesMap().forEach((name, type) -> {
            Schema<?> schema = null;
            Class<?> schemaClass = getOpenApiTypeForAttributeType(type);
            if (schemaClass == null) {
                return;
            }
            try {
                schema = (Schema<?>) schemaClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (schema instanceof ArraySchema) {
                ArraySchema arraySchema = (ArraySchema) schema;
                arraySchema.items(new StringSchema());
            }
            dataSchema.addProperties(name, schema.description(name));
        });
        return parentSchema;
    }

    private static boolean isTypeGenericEntityOrGenericValue(String type) {
        if (type == null) {
            return false;
        }
        return type.matches("org.apache.ofbiz.entity.GenericValue|GenericValue|org.apache.ofbiz.entity.GenericEntity|GenericEntity");
    }

    private static Schema<?> getSchemaForEntity(ModelEntity entity) {
        Schema<?> dataSchema = new Schema<>();
        dataSchema.setType("object");
        List<String> fields = entity.getAllFieldNames();
        for (String fieldNm : fields) {
            ModelField field = entity.getField(fieldNm);
            Schema<?> schema = null;
            Class<?> schemaClass = getOpenApiTypeForFieldType(field.getType());
            if (schemaClass == null) {
                continue;
            }
            try {
                schema = (Schema<?>) schemaClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            dataSchema.addProperties(fieldNm, schema.description(fieldNm));
        }
        return dataSchema;
    }

}
