# TheVivek2 Example 

## Getting started with Kotlin Coroutine, Rest APIS, WebSockets, Authorization
~~~
java 17.0.11
maven 3.6.3
mvn clean install
mvn clean install -DskipTests=true

Go to target and run this command  !!>>
java -jar example-v1-0.0.1-SNAPSHOT.jar --spring.profiles.active=INIT_CLIENT_DATA


http://localhost:8282/swagger-ui/index.html  -- Swagger UI 
http://localhost:8282 -- UI connected via WebSocket

~~~

### Refresh SCOPE:
POST http://localhost:8282/actuator/env
{ "name":thevivek2.testing.refreshable.beans.use, "value":"STATIC"}
POST http://localhost:8282/actuator/refresh

### WS enabled UI
http://localhost:8282 - opens UI for WS testing

### WS enabled UI and wrong destination subscribe
http://localhost:8282/?TEST_INVALID_DESTINATION=true  - opens UI and this tries to subscribe 
other user channel and that makes connection disconnected


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
