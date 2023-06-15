package com.bdd.api.step;

import com.bdd.ServiceDOM;
import io.cucumber.datatable.DataTable;
import io.restassured.http.Headers;
import net.thucydides.core.annotations.Step;

public class ApiHeaderStep extends ServiceDOM {
    private static Headers headers;
    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        ApiHeaderStep.headers = headers;
    }

    @Step("que configuro las cabeceras")
    public void configurarCabeceras(DataTable dataTable) {
        headers = configurerHeaders(dataTable);
    }


}
