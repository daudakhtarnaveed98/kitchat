
# KitChat
## About
KitChat is an artificially intelligent chatting application with following features:-

 1. Text message sending.
 2. Voice message sending, this message gets converted into text through an AI powered API.
 3. Picture, document or any other file sending.
 4. Group messaging (not available yet).
 5. All your communication will remain at your own device as there will be no central database (not available yet).

Currently we are in alpha testing and more features are being implemented and tested everyday.

## Architecture
KitChat uses micro-service based architecture, currently it has following two micro-services:-

 - **Registry:** It provides Restful endpoints for user registration, login and update.
 - **Message:** This micro-service provides Restful endpoints for managing user session, send messages, uploading and downloading files.

### Architecture Diagram
KitChat architecture is as follows:-

![alt text](https://github.com/daudakhtarnaveed98/kitchat/blob/master/kitchat-architecture.png)

### How To Run
1. Clone this repository using `git clone https://github.com/daudakhtarnaveed98/kitchat.git`
2. Edit the `application.properties` file in `kitchat/registry/src/main/resources`
3. Edit the line `spring.datasource.url=jdbc:mysql://localhost/your_db_name?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC`
4. Change MySQL username  in `spring.datasource.username=your_username` to your MySQL username
5. Change MySQL password  in `spring.datasource.password=your_password` to your MySQL password
6. Now go to `kitchat/eureka-server/` and type `./mvnw clean install spring-boot:run` the eureka server will start.
7.  Edit the `application.properties` file in `kitchat/message/src/main/resources`
8. Edit the line `spring.datasource.url=jdbc:mysql://localhost/your_db_name?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC`
9. Change MySQL username  in `spring.datasource.username=your_username` to your MySQL username
10. Change MySQL password  in `spring.datasource.password=your_password` to your MySQL password
11. Now go to `kitchat/eureka-server/` and type `./mvnw clean install spring-boot:run` the eureka server will start.
12. Now go to `kitchat/message/` and type `./mvnw clean install spring-boot:run` the message micro-service will start.
13. Now go to `kitchat/registry/` and type `./mvnw clean install spring-boot:run` the registry micro-service will start.
14. Now use the application.
