# TMS_V2_API_Service

## Getting started
~~~
java 17.0.11
maven 3.6.3
mvn clean install
java -jar .\target\trade-workstation-0.0.1-SNAPSHOT.jar
http://localhost:8282/swagger-ui/index.html  -- Swagger UI 
http://localhost:8282 -- UI connected via WebSocket
~~~

### Refresh SCOPE:
POST http://localhost:8282/actuator/env
{ "name":tws.testing.refreshable.beans.use, "value":"STATIC"}
POST http://localhost:8282/actuator/refresh

### WS enabled UI
http://localhost:8282 - opens UI for WS testing

### WS enabled UI and wrong destination subscribe
http://localhost:8282/?TEST_INVALID_DESTINATION=true  - opens UI and this tries to subscribe 
other user channel and that makes connection disconnected

### Entity Search Options:
<img src="lets-do-rest-based-entity-search.png" alt="HELLO" width="200" height="100">

### Search:
http://localhost:8282/api/v2/orders?page=0&size=2&search=buyOrSell%3E1
http://localhost:8282/api/v2/trades?search=tradeTime%3E%222020-10-10%2010:10:10%22%20AND%20securitySymbol:VARUN

Using explicit space
http://localhost:8282/api/v2/client/clientDealerMaster?page=0&size=1&search=activeStatus:A AND allowedToTrade:N AND memberBranch.activeStatus:A

http://localhost:8282/api/v2/client?page=0&size=1&sort=id,ASC&sort=activeStatus,DESC
[%20 is also space]

## More...
### if Database has objects (tables...), run this to create migration table
~~~
   system-patch/create_flyway.sql 
   
   Database migration file name should start from V...
~~~
### Client fetch Example
<img src="trade-workstation-client-fetch-example.png" alt="HELLO" width="200" height="100">