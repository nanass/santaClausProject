package org.nettosphere.samples.chat;

public final class Data {

    private String message;
    private String who;

    public Data() {
        this("", "");
    }

    public Data(String who, String message) {
        this.who = who;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}