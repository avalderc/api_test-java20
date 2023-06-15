package com.bdd.api.stepdefinition;

import com.bdd.api.step.ApiExecutionStep;
import com.bdd.api.step.ApiHeaderStep;
import com.bdd.api.step.ApiParametersStep;
import com.bdd.util.Util;
import com.bdd.util.UtilApi;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ApiStepDefinition {
    private Scenario scenario;
    @Steps
    private ApiHeaderStep apiHeaderStep;
    @Steps
    private ApiParametersStep apiParametersStep;
    @Steps
    private ApiExecutionStep apiExecutionStep;
    @Before
    public void beforeScenario (Scenario scenario)
    {
        this.scenario = scenario;
        UtilApi.saveVariableOnSession("scenario", this.scenario);
    }

    /** **************** TIPOS DE CONFIGURACIÓN **************** */
    /*@Dado("(?i)^que obtengo el jwt zconnect$")
    public void obtengoTokenDinamico(DataTable dataTable) {
        apiExecutionStep.obtenerJwtZconnect(dataTable);
    }*/
    @Dado("(?i)^(?>que )?configuro (?>e?l[ao]?s? )?(?>header|cabeceras)s?$")
    public void queConfiguroLasCabeceras(DataTable dataTable) {
        apiHeaderStep.configurarCabeceras(dataTable);
    }
    @Dado("(?i)^(?>que )?configuro los parametros$")
    public void configuroParametros(DataTable dataTable) {
        apiParametersStep.configurarParametros(dataTable);
    }
    @Dado("(?i)^(?>que )?configuro los path (?>variables|parametros)$")
    public void configuroPathVariable(DataTable dataTable) {
        apiParametersStep.configurarPathVariable(dataTable);
    }
    /** VALIDAR | MEJORAR */
    @Dado("(?i)^(?>que )?configuro el body request de tipo x-www-form-urlencoded$")
    public void configuroBodyRequestx_www_form_urlencoded(DataTable dataTable) {
        apiParametersStep.configuroFormParametros(dataTable);
    }
    @Dado("(?i)^(?>que )?configuro el body request$")
    public void configuroBodyRequest(DataTable dataTable) {
        apiParametersStep.configurarBodyRequest(dataTable);
    }

    /** ********************** EJECUCIÓN ********************** */
    @Cuando("(?i)^(?>que )?ejecuto el api$")
    public void ejecutoApi(DataTable dataTable) {
        apiExecutionStep.ejecutarAPI(dataTable);
    }

    /** ********************** VALIDACIONES ********************** */
    @Entonces("(?i)^valido (?>si|que)? ?el c[oó]digo de respuesta (?>es|sea)? ?\"?([^\"]*)\"?$")
    public void validoCodigoRespuesta(int codigoRespuesta) {
        apiExecutionStep.validarCodigoRespuesta(codigoRespuesta);
    }
    @Entonces("(?i)^valido los siguientes datos en el json de respuesta$")
    public void validoDatosJsonRespuesta(DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps();
        for (Map<String, String> stringStringMap : list) {
            String nodo = stringStringMap.get("nodo");
            String valor = stringStringMap.get("valor");
            Assert.assertEquals(valor, apiExecutionStep.obtenerValorNodoRespuesta(nodo));
        }
    }
    @Entonces("^valido los siguientes resultados en el json de respuesta$")
    public void validarResultadoJsonRespuesta(DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps();
        for (Map<String, String> stringStringMap : list) {
            String nodo = stringStringMap.get("nodo");
            String valor = stringStringMap.get("valor");
            if(Util.isNumeric(valor)){
                Assert.assertEquals(Double.parseDouble(valor), Double.parseDouble(apiExecutionStep.obtenerNodoRespuesta(nodo)),0.0001);
            }else {
                Assert.assertTrue(Util.matchCI(valor, apiExecutionStep.obtenerNodoRespuesta(nodo)));
            }
        }
    }

}
