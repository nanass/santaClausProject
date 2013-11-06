package Util;

import java.io.*;

public final class Data implements Serializable{

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

    public byte[] toByteArray(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            bytes = bos.toByteArray();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return bytes;
    }

    public static Data buildFromBytes(byte[] data){
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (Data)o;
    }
}