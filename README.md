# Chat Room
Chat room application implementation using WebSocket.

## Background
WebSocket is a communication protocol that makes it possible to establish a two-way communication channel between a
server and a client.

## Instruction
### Implement the message model
Message model is the message payload that will be exchanged between the client and the server. Implement the Message
class in chat module. Make sure you cover all there basic actions.
1. ENTER
2. CHAT
3. LEAVE

#### Complete WebSocketChatServer
Implemented all TODOs inside WebSocketChatServer follow each method description.

### Build application
mvn install -Dmaven.test.skip=true

### Run the application with command
mvn spring-boot:run

#### Run the functional tests
mvn test -Dwebdriver.chrome.driver={PATH TO CHROME DRIVER}
