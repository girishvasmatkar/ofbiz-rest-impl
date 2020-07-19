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
package org.apache.ofbiz.ws.rs.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "error")
public class Error {

    private int statusCode;
    private String statusDescription;
    private String errorMessage;
    private List<String> additionalErrors;

    public Error(int statusCode, String statusDescription, String errorMessage) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.errorMessage = errorMessage;
    }

    public Error(int statusCode, String statusDescription, String errorMessage, List<String> additionalErrors) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.errorMessage = errorMessage;
        this.additionalErrors = additionalErrors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<String> getAdditionalErrors() {
        return additionalErrors;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
