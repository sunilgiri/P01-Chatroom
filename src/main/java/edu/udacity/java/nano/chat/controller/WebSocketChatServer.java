package edu.udacity.java.nano.chat.controller;

import com.alibaba.fastjson.JSON;
import edu.udacity.java.nano.chat.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);


    //Send message to every logged in users
    private static void sendMessageToAll(String msg) {

        for (Map.Entry<String, Session> userSession : onlineSessions.entrySet()) {

            try {
                userSession.getValue().getBasicRemote().sendText(msg);
            } catch (Exception e) {
                logger.error("Error in sending message" + e.getMessage());

            }
        }

        }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {

        onlineSessions.put(session.getId(),session);

        //Login username
        String username = session.getRequestParameterMap().get("username").get(0);

       //Prepare join message
        Message message = new Message();
        message.setMsg("Joined");
        message.setType(Message.Type.OPEN);
        message.setOnlineCount(onlineSessions.size());
        message.setUserName(username);

        //Convert to JSON
        String messageJson = JSON.toJSONString(message);
        sendMessageToAll(messageJson);
        logger.debug(username + ":" + "joined");
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        Message message = JSON.parseObject(jsonStr,Message.class);
        message.setOnlineCount(onlineSessions.size());
        String messageJson = JSON.toJSONString(message);
        sendMessageToAll(messageJson);
        logger.debug("OnMessage");

    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        String username = session.getRequestParameterMap().get("username").get(0);

        //Update the sessions
        onlineSessions.remove(session.getId());

        //Prepare left message
        Message message = new Message();
        message.setOnlineCount(onlineSessions.size());
        message.setType(Message.Type.CLOSE);
        message.setUserName(username);
        message.setMsg("Left");
        String messageJson = JSON.toJSONString(message);
        sendMessageToAll(messageJson);
        logger.debug(username + ":" + "Logged out of chat");

    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error(error.toString());
        error.printStackTrace();
    }

}
