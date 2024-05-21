# the-raven-basic-requirements

**Environment:** Java, SpringBoot, MySQL
</br>
</br>
Database schema creates automatically so you do not need to create it manually. Simply change database properties in "application.properties" file.
</br>
</br>
**Endpoints/JSON examples**:
</br>
**1)** Create customer: (POST) http://localhost:8081/api/customers

request body (JSON):

{

"fullName" : "{full name}",

"email" : "{username}@{domain}",

"phone" : "{plus symbol(+) and 5-13 digits}" // optional field

}

**2)** Read customers: (GET) http://localhost:8081/api/customers

**3)** Read customer by id: (GET) http://localhost:8081/api/customer/{id}

**4)** Delete customer: (DELETE) http://localhost:8081/api/customer/{id}

**5)** Update customer with all fields specified: (PUT) http://localhost:8081/api/customer/{id}

request body (JSON):

{

"fullName" : "{full name}",

"email" : "{username}@{domain}",

"phone" : "{plus symbol(+) and 5-13 digits}"

}

