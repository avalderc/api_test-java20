package com.bdd;

import com.bdd.util.Util;
import io.cucumber.datatable.DataTable;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import net.thucydides.core.steps.ScenarioSteps;
import org.junit.Assert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.bdd.util.Util.getEnvironmentProperty;
import static com.bdd.util.UtilApi.getVariableOnSession;

public class ServiceDOM extends ScenarioSteps {

    public ServiceDOM() {}

    protected Headers configurerHeaders(DataTable dataTable) {
        List<Header> headerList = new LinkedList<>();
        List<Map<String, String>> listCabeceras = dataTable.entries();
        Iterator<Map<String, String>> var4 = listCabeceras.iterator();
        //String cookies = getVariableOnSession("cookies");
        //if(cookies != null) if(!cookies.isEmpty()) headerList.add(new Header("Cookie", cookies));
        while(var4.hasNext()) {
            Map<String, String> stringStringMap = var4.next();
            String cabecera = stringStringMap.get("cabeceras");
            String valor = stringStringMap.get("valor");
            valor = valor == null ? "" : valor;
            String variable = "";
            if(Util.matchCI("\\{\\{.*\\}\\}",valor))
                variable = getVariableOnSession(valor.substring(valor.indexOf("{{")+2,valor.indexOf("}}")));
            variable = variable != null ? variable : getEnvironmentProperty(valor.substring(valor.indexOf("{{")+2,valor.indexOf("}}")));
            valor = variable.isEmpty()
                    ? (String) replaceRequestValue(valor)
                    : valor.replace(valor.substring(valor.indexOf("{{"),valor.indexOf("}}")+2),variable);
            Header header = new Header(cabecera, valor);
            headerList.add(header);
        }
        System.out.println("Headers configurados: ");
        System.out.println(headerList);
        return new Headers(headerList);
    }
    protected Map<String, Object> configurerParameters(DataTable dataTable) {
        List<Map<String, String>> listParemetros = dataTable.entries();
        System.out.println(listParemetros);
        Map<String, Object> parameters = new HashMap<>();
        Iterator<Map<String, String>> var4 = listParemetros.iterator();

        while(var4.hasNext()) {
            Map<String, String> stringStringMap = var4.next();
            String valor = (stringStringMap.get("valor"));
            valor = valor != null ? valor : "";
            valor = Util.matchCI("\\{\\{.*\\}\\}",valor)
                    ? getVariableOnSession(valor.substring(valor.indexOf("{{")+2,valor.indexOf("}}")))
                    : valor;
            parameters.put(stringStringMap.get("parametros"), valor);
        }
        System.out.println("Parametros configurados: ");
        System.out.println(parameters);
        return parameters;
    }

    protected Map<String, Object> configurerPathVariable(DataTable dataTable) {
        List<Map<String, String>> listParemetros = dataTable.entries();
        System.out.println(listParemetros);
        Map<String, Object> pathParams = new HashMap<>();
        Iterator<Map<String, String>> var4 = listParemetros.iterator();

        while(var4.hasNext()) {
            Map<String, String> stringStringMap = var4.next();
            String valor = (stringStringMap.get("valor"));
            valor = valor != null ? valor : "";
            valor = Util.matchCI("\\{\\{.*\\}\\}",valor)
                    ? getVariableOnSession(valor.substring(valor.indexOf("{{")+2,valor.indexOf("}}")))
                    : valor;
            pathParams.put(stringStringMap.get("pathParam"), valor);
        }
        System.out.println("Path Params configurados: ");
        System.out.println(pathParams);


        return pathParams;
    }
    protected void validarCodigoRespuesta(Response response, int statusCode){
        Assert.assertEquals(statusCode, response.statusCode());
    }
    protected Object obtenerValorNodoRespuestaJson(String nodo, Response response){
        return response.getBody().jsonPath().getJsonObject(nodo);
    }
    public static void createResponseFailByFormat(String serviceName, String formatFile, String response) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS");
        LocalDateTime now = LocalDateTime.now();
        File filePath = new File("response/api/"+serviceName+"/"+formatFile);
        filePath.mkdirs();
        String finalFileName = serviceName+"_"+dateTimeFormatter.format(now)+"."+formatFile;
        File file = new File(filePath+"/"+finalFileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(response);
        fileWriter.close();
    }

    public static String getValueFromDataTable(DataTable dataTable, String title) {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        return (String) ((Map)list.get(0)).get(title);
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
}
