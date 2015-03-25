The currency Fair will run on Google Cloud Platform using AppEngine Platform as a Service SDK. 
The platform runs in a secured “sandbox” environment on top of Java 6 and ensures scalability to handle additional demand. 
A high representation of the main components involved in this project is: 

•	Api RESTful Server is a stateless and high scalable dispatcher in charge of processing incoming HTTP requests and generates HTTP response with data and resources. It exposes the business logic through API endpoints to perform CRUD operations. 
•	Front-end client offers a web responsive user interface that interacts with the REST server through JSON objects. It also manages the user session since the server is stateless. 
•	Google Data Store ,Use a managed, NoSQL, schemaless database for storing non-relational data. Cloud Datastore automatically scales as you need it and supports transactions as well as robust, SQL-like queries.

Stack:
Google App Engine
REST API
Google Guice (Dependency Injection)
Google Data Store
Jackson
JSP
Jquery
Ajax 
Maven

Functionality:
1. You can post the multiple messages at once using end point http://eloquent-falcon-88915.appspot.com/api/messages/consume which needs json structure. The format should be as follows
[{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "INR", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "originatingCountry" : "FR"},{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "originatingCountry" : "FR"}]
 To test the endpoint there are multiple ways using Google Advanced Rest Client. chrome-extension://hgmloofddffdnphfgcellkdfbfbjeloo/RestClient.html
 or
 In the front end itself there is a option to submit the messages. just enter the json string and hit submit.
 2. In the Architecture ORM layer is missing ( not much time) so I have used Google Data Store (NoSql) to store the data.
 3. Doing some processing of data like grouping of currencies , search the messages by userId etc
 4. Also using Google Geocoding Api to represent the geographical locations on the map where messages originated.
 http://eloquent-falcon-88915.appspot.com/


