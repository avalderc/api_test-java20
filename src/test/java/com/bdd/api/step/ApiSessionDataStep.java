package com.bdd.api.step;

import com.bdd.ServiceDOM;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

public class ApiSessionDataStep extends ServiceDOM {
    @Steps
    private ApiExecutionStep apiExecutionStep;
    @Step("Guardo JWT en sesion")
    public void guardarJWTSesion() {
        String jwt = apiExecutionStep.getResponse().asString();
        //saveVariableOnSession(JWT_TOKEN, jwt);
    }
    @Step("Guardo los datos de la respuesta en sesion")
    public void guardarDatosRespuestaSession(DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        //Map<String, Object> lista = new LinkedHashMap<>();
        Object value = "";
        Response response = apiExecutionStep.getResponse();
        for (Map<String, String> stringStringMap : list) {
            String nodo = stringStringMap.get("nodo");
            String sessionKey = stringStringMap.size()>1?stringStringMap.get("key"):null;

            /*
            if (obtenerValorNodoRespuestaJson(nodo, response).getClass().getSimpleName().equals("ArrayList"))
                value = obtenerValorNodoDeArrayRespuestaJson(nodo, response);
            else if (obtenerValorNodoRespuestaJson(nodo, response).getClass().getSimpleName().equals("String"))
                value = obtenerValorNodoRespuestaJson(nodo, response);
            else value =obtenerValorNodoRespuestaJson(nodo, response).toString();
            saveVariableOnSession((sessionKey==null?nodo:sessionKey), value);
            */
            //lista.put(nodo, value);
        }

        //settingsModel.setValoresSessionList(lista);

    }
}
