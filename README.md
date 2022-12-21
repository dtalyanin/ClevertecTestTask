# Test task solution by Dzmitry Talianin
* ### Java 17
* ### Spring Boot 2.7.6
* ### Gradle 
* ### BD: PostgreSQL

Program generates a variant of the receipt based on the transmitted data. Endpoint is **/receipt** with request parameters:

* required: at least one parameter with key - 'item' plus product ID in database, value - quantity (for example item1=10)
* not required - discount card with key 'card' and value card number (for example card=1234)

Program writes the order data to receipt file. Response depends on the accepted header: if text/plain, then return a **text file** for download. Else return **json**.
