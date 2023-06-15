package com.bdd.api.step;

import com.bdd.JSONElement;
import com.bdd.ServiceDOM;
import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApiParametersStep extends ServiceDOM {
    private static String bodyRequest;
    private static Map<String, Object> parameters;
    private static Map<String, Object> pathVariables;
    private static Map<String, Object> formParams;
    private static Map<String, Object> formUrlEncoded;
    public String getBodyRequest() {
        return bodyRequest;
    }

    public void setBodyRequest(String bodyRequest) {
        ApiParametersStep.bodyRequest = bodyRequest;
    }
    public Map<String, Object> getParameters() {
        return parameters;
    }
    public void setParameters(Map<String, Object> parameters) {
        ApiParametersStep.parameters = parameters;
    }
    public Map<String, Object> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, Object> pathVariables) {
        ApiParametersStep.pathVariables = pathVariables;
    }
    public Map<String, Object> getFormParams() {
        return formParams;
    }
    public Map<String, Object> getFormUrlEncoded() {
        return formUrlEncoded;
    }
    public void setFormParams(Map<String, Object> formParams) {
        ApiParametersStep.formParams = formParams;
    }
    @Step("Configurar los parametros")
    public void configurarParametros(DataTable dataTable) {

        List<Map<String, String>> listParemetros = dataTable.entries();
        //System.out.println(listParemetros);
        Map<String, Object> parameters = new HashMap<>();
        Iterator<Map<String, String>> var4 = listParemetros.iterator();

        while(var4.hasNext()) {
            Map<String, String> stringStringMap = var4.next();
            String valor = (stringStringMap.get("valor"));
            valor = valor != null ? valor : "";
            /*valor = Util.matchCI("\\{\\{.*\\}\\}",valor)
                    ? getVariableOnSession(valor.substring(valor.indexOf("{{")+2,valor.indexOf("}}")))
                    : valor;*/
            parameters.put(stringStringMap.get("parametros"), valor);
        }

        ApiParametersStep.parameters = parameters;
        //System.out.println("parametros: " + parametros);
        //parametros = configurerParameters(dataTable);
    }
    @Step("Configurar las variables path")
    public void configurarPathVariable(DataTable dataTable) {
        pathVariables = configurerPathVariable(dataTable);
    }
    @Step("Configurar los form parametros$")
    public void configuroFormParametros(DataTable dataTable) {
        formParams = configurerParameters(dataTable);
    }
    @Step("Configurar el body request para enviarlo")
    public void configurarBodyRequest(DataTable dataTable) {
        bodyRequest = "";
        List<Map<String, String>> list = dataTable.entries();
        JSONObject json_obj=new JSONObject();
        for (Map<String, String> valMap : list) {

            String key = valMap.get("key");
            String value = valMap.get("valor");
            try {
                expand(json_obj, key, value);
                //json_obj.put(key,value);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(json_obj);

    }

    private Object replaceRequestValue(String valor) {
        Object newValor = valor.replace("%BAR%", "|").replace("%WHITE%", " ");
        if(valor.matches("\".*\"")){
            newValor = ((String) newValor).substring(
                    ((String) newValor).indexOf("\"")+1,
                    ((String) newValor).lastIndexOf("\"")
            );
        }
        if (valor.contains("boolean:") && valor.split(":")[1].matches("false|true")) {
            newValor = Boolean.parseBoolean(valor.split(":")[1]);
        } else if (valor.contains("int:")) {
            newValor = Integer.parseInt(valor.split(":")[1]);
        } else if (valor.contains("double:")) {
            newValor = Double.parseDouble(valor.split(":")[1]);
        } else if (valor.contains("string:")) {
            newValor = valor.split(":")[1];
        }
        return newValor;
    }
    private String getJsonTypeString(String content) {
        String bodyRequest = "";
        char c = content.charAt(0);
        if (c == '[') {
            JSONArray jsonObject = new JSONArray(content);
            bodyRequest = jsonObject.toString();
        } else if (c == '{') {
            JSONObject jsonObject = new JSONObject(content);
            bodyRequest = jsonObject.toString();
        } else {
            //UtilApi.logger(this.getClass()).log(Level.WARNING, "Se esperaba el caracter [,{ al inicio.");
        }

        return bodyRequest;
    }
    private void expand(Object parent, String key, Object value) {
        JSONElement element = new JSONElement(parent);
        if (!key.contains(".")) { // End
            element.put(key, value);
            return;
        }

        String innerKey = key.substring(0, key.indexOf("."));
        String remaining = key.substring(key.indexOf(".") + 1);

        if (element.has(innerKey)) {
            expand(element.get(innerKey), remaining, value);
            return;
        }

        Object object = element.newInstance();
        Object put = element.put(innerKey, object);
        expand(put, remaining, value);
    }
}
