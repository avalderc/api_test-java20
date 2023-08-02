# API TESTING
 QA API Testing Framework, Selenium, RestAssured, JAVA 20

Agregar features en `/src/test/resources/features/`

```gherkin
Escenario: Escenario aquí
    Dado que configuro las cabeceras
      | cabeceras     | valor          |
      | Authorization | Bearer {{jwt}} |
    Y que configuro los path parametros
      | pathParam | valor |
      | entity    | posts |
    Y que configuro el body request
      | key    | valor |
      | title  | foo   |
      | body   | bar   |
      | userId | 1     |
    Cuando que ejecuto el api
      | url                                           | metodo |
      | https://jsonplaceholder.typicode.com/{entity} | POST   |
    Entonces valido que el codigo de respuesta sea "201"
    Y valido los siguientes resultados en el json de respuesta
      | nodo | valor |
      | id   | 101   |
```
## VARIABLES
Las variables pueden ser asignadas en `serenity.properties` o en `/src/test/resources/serenity.conf` teniendo en cuenta que de `serenity.conf`
serán usadas las definidas del 'enviroment' y pueden ser llamadas entre dobles llaves: `{{variable}}`; dentro de los headers, los params y el body

Ejemplo:

`/src/test/resources/serenity.conf`
```conf
environments {
    cert {
        jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ"
    }
}
```
...Feature
```gherkin
    Dado que configuro las cabeceras
      | cabeceras     | valor          |
      | Authorization | Bearer {{jwt}} |
```
## HEADERS
Asignar los headers en la tabla, usando el step `Dado que configuro las cabeceras`, con columnas `cabeceras` y `valor`
```gherkin
    Dado que configuro las cabeceras
      | cabeceras     | valor          |
      | Authorization | Bearer {{jwt}} |
```
## PATH PARAMS
Asignar los path params (parametros de url) en la tabla, usando el step `Y que configuro los path parametros`, con columnas `pathParam` y `valor`
```gherkin
    Y que configuro los path parametros
      | pathParam | valor |
      | entity    | posts |
```
Estos parametros son llamados desde la url entre llaves simples: `{pathParam}`
## QUERY PARAMS
Asignar los query params en la tabla, usando el step `Y que configuro los parametros`, con columnas `parametros` y `valor`
```gherkin
    Y que configuro los parametros
      | parametros | valor |
      | offset     | 1     |
```
## BODY
En caso use el body, asignar los parametros json en la tabla, usando el step `Y que configuro el body request`, con columnas `key` y `valor`
```gherkin
    Y que configuro el body request
      | key       | valor |
      | title     | foo   |
      | body.text | bar   |
      | userId    | 1     |
```
## EJECUCION
El step `Cuando que ejecuto el api` ejecutara al api con dirección en la tabla `url` y el metodo en `metodo`, almacenando la respuesta
```gherkin
    Cuando que ejecuto el api
      | url                                           | metodo |
      | https://jsonplaceholder.typicode.com/{entity} | POST   |
```
Se puede ver en este ejemplo como es llamado un pathParam `{entity}`
## VALIDACIONES
Para validar el código de respuesta se llama al siguiente step asignando el código esperado
```gherkin
    Entonces valido que el codigo de respuesta sea "201"
```
También puedes validar los valores de los nodos en el json de respuesta
```gherkin
    Y valido los siguientes resultados en el json de respuesta
      | nodo | valor |
      | id   | 101   |
```
