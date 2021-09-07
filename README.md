# Reloadly Customer Account API

This API is responsible for Customer onboarding and Authentication.

Customer would be able to 
- Create and Manage(Update and Delete) Account
- Get a welcome message and account activation link Email (Using Notification API)
- Login with Email / Password
- Generate Token(JWT) along with login information. (Which can be used to perform transaction on the Transaction API)

### This project is created using
- Java (Spring Boot)
- In-Memory Database with JPA
- JUnit

### Running the application locally
- mvn clean package (To reinstall plugins)
- mvn spring-boot:run (To start the project)

### Basic Onboarding information:
```json
{
  "firstName": "",
  "lastName": "",
  "email": "",
  "phoneNumber": "",
  "password": ""
}
```

And a response of
```json
{
  "status": true,
  "message": "Success",
  "data": {
    "id": 0,
    "firstName": "",
    "lastName": "",
    "email": "",
    "phoneNumber": "",
    "roles": "",
    "createdAt": "",
    "updatedAt": "",
    "token": {
      "accessToken": "",
      "expiredAt": ""
    }
  }
}
```

## Other Links:

### Customer Account API
> **Github**: https://github.com/samtax01/reloadly-customer-account

> **Heroku**: https://reloadly-quiz-customer-api.herokuapp.com/swagger-ui.html


#### Transaction API
> **Github**: https://github.com/samtax01/reloadly-transaction-api

> **Heroku**: https://reloadly-quiz-transaction-api.herokuapp.com/swagger-ui.html

#### Notification API
> **Github**: https://github.com/samtax01/reloadly-notification-api

> **Heroku**: https://reloadly-quiz-notification-api.herokuapp.com/swagger-ui.html
