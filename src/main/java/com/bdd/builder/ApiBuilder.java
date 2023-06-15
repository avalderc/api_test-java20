package com.bdd.builder;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiBuilder {
    private ApiConfig apiConfig;
    private static RequestSpecification requestSpecs;
    public ApiBuilder (ApiConfig ac){
        this.apiConfig = ac;
    }
    public static ApiBuilder apiBuilder(){
        return new ApiBuilder(new ApiConfig());
    }
    public ApiBuilder withApiURL(String url){
        apiConfig.setApiUrl(url);
        return this;
    }
    public ApiBuilder withMethod(String method){
        apiConfig.setMethod(method);
        return this;
    }
    public ApiBuilder withHeaders(Headers headers){
        apiConfig.setHeaders(headers);
        return this;
    }
    public ApiBuilder withParams(Map<String, Object> parameters){
        apiConfig.setParameters(parameters);
        return this;
    }
    public ApiBuilder withFormUrlEncoded(Map<String, Object> formParams){
        apiConfig.setFormUrlEncoded(formParams);
        return this;
    }
    public ApiBuilder withPathVariables(Map<String, Object> pathVariables){
        apiConfig.setPathVariables(pathVariables);
        return this;
    }
    public ApiBuilder withBody(String body){
        apiConfig.setBodyRequest(body);
        return this;
    }
    public ApiConfig build(){
        requestSpecs = RestAssured.given();
        if( apiConfig.getHeaders() != null) requestSpecs.headers(apiConfig.getHeaders());
        if( apiConfig.getParameters() != null ) requestSpecs.queryParams(apiConfig.getParameters());
        if( apiConfig.getPathVariables() != null ) requestSpecs.pathParams(apiConfig.getPathVariables());
        if( apiConfig.getFormUrlEncoded() != null ) requestSpecs.formParams(apiConfig.getFormUrlEncoded());
        if( apiConfig.getBodyRequest() != null ) requestSpecs.body(apiConfig.getBodyRequest());
        apiConfig.setReqSpec(requestSpecs);
        return apiConfig;
    }

    /*public static Response ejecutarApiBuilder(){
        String tipoMetodo = apiBuilder().method.toUpperCase();
        Response response;
        byte var7 = -1;
        switch (tipoMetodo.hashCode()) {
            case 70454:
                if (tipoMetodo.equals("GET")) var7 = 1;
                break;
            case 2451856:
                if (tipoMetodo.equals("POST")) var7 = 0;
                break;
            case 2012838315:
                if (tipoMetodo.equals("DELETE")) var7 = 2;
                break;
        }
        switch (var7) {
            case 0:
                response = (Response) apiConfig.getReqSpec().when().post(apiBuilder().apiUrl, new Object[0]);
                break;
            case 1:
                response = (Response) apiConfig.getReqSpec().when().get(apiBuilder().apiUrl, new Object[0]);
                break;
            case 2:
                response = (Response) apiConfig.getReqSpec().when().delete(apiBuilder().apiUrl, new Object[0]);
                break;
            default:
                response = (Response) apiConfig.getReqSpec().when().delete(apiBuilder().apiUrl, new Object[0]);
        }
        return response;
    }*/
}
