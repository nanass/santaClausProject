package org.nettosphere.samples.chat;

public final class Data {

    private String message;
    private String who;
    private String type;
    private String author;

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

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return author;
    }
}