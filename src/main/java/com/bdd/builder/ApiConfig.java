package com.bdd.builder;

import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiConfig {
    private String apiUrl;
    private String method;
    private Headers headers;
    private Map<String, Object> parameters;
    private Map<String, Object> formUrlEncoded;
    private Map<String, Object> pathVariables;
    private String bodyRequest;
    private RequestSpecification reqSpec;

    public RequestSpecification getReqSpec() {
        return reqSpec;
    }

    public void setReqSpec(RequestSpecification reqSpec) {
        this.reqSpec = reqSpec;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
    public Map<String, Object> getFormUrlEncoded() {
        return formUrlEncoded;
    }

    public void setFormUrlEncoded(Map<String, Object> formUrlEncoded) {
        this.formUrlEncoded = formUrlEncoded;
    }
    public Map<String, Object> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, Object> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public String getBodyRequest() {
        return bodyRequest;
    }

    public void setBodyRequest(String bodyRequest) {
        this.bodyRequest = bodyRequest;
    }
}
