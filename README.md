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
