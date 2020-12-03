# REPLY  VIDEO STREAMING SERVICE

A Micro-service where customers are created / managed and payments are made for their video streaming service


Technologies used        | Version
-------------------------|------------------------------------------------------------------------
Java                     | 8
SpringBoot               | 2.1.4.RELEASE
Maven Wrapper            | 4.0.0
Lombok                   | 1.18.6
Swagger                  | 2.9.2
Rest Assured             | 3.3.0
Cucumber                 | 4.2.6

## Useful Links
Description              | Link
-------------------------|------------------------------------------------------------------------
Users API                | http://localhost:8090/videostreamingservice/users
Payments API             | http://localhost:8090/videostreamingservice/payments
Health                   | http://localhost:8090/actuator/health
Swagger                  | http://localhost:8090/swagger-ui.html

###Note:
* **`Date of birth has to be of ISO 8601, format yyyy-MM-dd`**

* **`protocol is HTTP, not https`**

* **`Lombok is used in this project, so if you need to run the application on IntelliJ, make sure you 'enable 
annotation processor' in preferences.`**

##How to run the project
1. Go to videostreamingservice directroy in Terminal

2. Build the project with maven command (this will run the **Unit Tests and Cucumber Integration Tests**)

**`maven clean install`**

3. Maven run spring boot app, for mac / linux, for windows (replace ./target/videostreamingservice-1.0.0.jar with actual path to the built jar)

**`java -jar ./target/videostreamingservice-1.0.0.jar com.reply.videostreaming.VideostreamingApplication`**

4. Use the swagger ui or postman to test the endpoints


##Known Behaviour
1. Pagination (in retrieval of users) is not supported / implemented as we use Java Collections as the Datastore.

2. Membership state of users (free trail / paid) is not maintained as it is not mentioned

##Areas of improvement
1. Can you Docker for containerization and use of container orchestration engine i.e. kubernetes

2. Can use monitoring / tracing tools for metrics, reporting and alerting

3. Can further enable centralized logging with ELK or splunk for troubleshooting.
