package edu.udacity.java.nano.chat.model;

/**
 * WebSocket message model
 */
public class Message {

   //Message Contents
    private String msg;

    //User Name
    private String userName;

    //Type of message
    public enum Type {OPEN,CLOSE,SPEAK}

    private Type type;

    //Online users
    private Integer onlineCount;

    public Message(){};

    public Message(String name, String message,Type type, Integer onlineCount) {
        this.userName = name;
        this.msg = message;
        this.type = type;
        this.onlineCount = onlineCount;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

}
