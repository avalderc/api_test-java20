package com.bdd.api.step;

import com.bdd.ServiceDOM;
import com.bdd.builder.ApiConfig;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import static com.bdd.builder.ApiBuilder.apiBuilder;

public class ApiExecutionStep extends ServiceDOM {
    @Steps
    private ApiHeaderStep apiHeaderStep;
    @Steps
    private ApiParametersStep apiParametersStep;
    private EnvironmentVariables environmentVariables;
    private static Response response;

    public Response getResponse() {
        return response;
    }
    private void ejecutarApiBuilder(ApiConfig apiConfig) {
        response = apiConfig.getReqSpec().when().request(apiConfig.getMethod(), apiConfig.getApiUrl());
    }
    private ApiConfig apiConfig(String url, String metodo) {
        return apiBuilder()
                //.withApiType(tipo)
                .withApiURL(url)
                .withMethod(metodo)
                .withHeaders(apiHeaderStep.getHeaders())
                .withFormUrlEncoded(apiParametersStep.getFormUrlEncoded())
                .withPathVariables(apiParametersStep.getPathVariables())
                .withParams(apiParametersStep.getParameters())
                .withBody(apiParametersStep.getBodyRequest())
                .build();
    }
    private void cleanAllConfiguration(){
        apiHeaderStep.setHeaders(null);
        apiParametersStep.setPathVariables(Collections.emptyMap());
        apiParametersStep.setParameters(Collections.emptyMap());
        apiParametersStep.setBodyRequest(StringUtils.EMPTY);
        apiParametersStep.setFormParams(Collections.emptyMap());
    }
    @Step("Ejecutar Api y obtener la respuesta")
    public void ejecutarAPI(DataTable dataTable) {
        // String tipo = getValueFromDataTable(dataTable, "tipo");
        String url = getValueFromDataTable(dataTable, "url");
        String metodo = getValueFromDataTable(dataTable, "metodo");

        ejecutarApiBuilder( apiConfig(url, metodo) );
        System.out.println(response.prettyPrint());
        cleanAllConfiguration();
        /*UtilApi.saveAPIEvidenceCucumberReport(new Object[]{requestSpecification(), response},
                UtilApi.getVariableOnSession(Constants.SCENARIO));*/
    }

    @Step("Validar el código de respuesta")
    public void validarCodigoRespuesta(int codigoRespuesta) {
        validarCodigoRespuesta(response, codigoRespuesta);
    }
    @Step("Obtener valor del nodo de la respuesta")
    public String obtenerNodoRespuesta(String nodo) {
        return String.valueOf(obtenerValorNodoRespuestaJson(nodo, response)).trim();
    }
    @Step("Obtener valor del nodo de la respuesta")
    public String obtenerValorNodoRespuesta(String jsonPathNodo) {
        return response.getBody().jsonPath().getJsonObject(jsonPathNodo);
    }
}
