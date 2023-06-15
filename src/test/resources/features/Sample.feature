#language: es

Característica: Sample de prueba
  Como
  Quiero
  Para

  @Sample
  Escenario: Solicitud de consulta de comida para mascotas
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