package com.jdrj.socket.server.pojo;

public class ChatObject {

    private String userName;
    private Integer message;

    public ChatObject() {
    }

    public ChatObject(String userName, Integer message) {
        super();
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getMessage() {
        return message;
    }
    public void setMessage(Integer message) {
        this.message = message;
    }

}
