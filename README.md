#Trade Application
Below features can be achieved using the application. This is a Spring Boot application and has an embeded tomcat.

### Create/Update Trade
  * API - `http://localhost:8080/trade/create`
  * JSON - `    
      {
      "tradeIdentifier": {
      "tradeId": "T5",
      "version": 1
      },
      "counterPartyId": "CP-5",
      "bookingId": "B5",
      "maturityDate": "2021-09-28",
      "expired": false
      }`

### View Trade
  * API - `http://localhost:8080/trade/{TARDE_ID}`
  Eg - `http://localhost:8080/trade/T1`

### View All Trades
* API - `http://localhost:8080/trade/allTrades`

### Info
  * JUnit's are written for all the business logic implementations covering most of the positive/negative scenarios 
  * Created my own DataStructure to store the data instead of using in-memory database
  * All the corner cases and scenarios are handled as mentioned in the document
  * Scheduler is configured to run every day
  * Exceptions are handled with error codes, but configuring error messages can be as part of the improvments