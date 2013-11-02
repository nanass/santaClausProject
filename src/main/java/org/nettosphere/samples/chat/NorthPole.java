package org.nettosphere.samples.chat;

import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.*;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

@ManagedService(path = "/northpole")
public class NorthPole {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static Broadcaster b = BroadcasterFactory.getDefault().get("/");

    @Get
    public void onOpen(final AtmosphereResource r) {
        b.addAtmosphereResource(r);
    }

    @Message
    public String onMessage(String message) throws IOException {
        Data d = mapper.readValue(message, Data.class);
        WishList.addToWishList(d);
        return mapper.writeValueAsString(d);
    }

    public final static class WishList {

        private static CopyOnWriteArrayList<Data> wishList = new CopyOnWriteArrayList<Data>();

        public static void deliverGifts(){
            String output = "";
            System.out.println("Gifts for: ");
            for(Data d : wishList){
                System.out.println(d.getAuthor() + " : " + d.getMessage());
                output += d.getAuthor() + " , " + d.getMessage() +" ";
            }
            wishList.clear();
            send(output, "all");
        }

        public static void addToWishList(Data msg){
            if(msg.getType().equals("wishlist")){
                wishList.add(msg);
                System.out.println(msg.getMessage());
            }
        }

        public static void send(String msg, String who)  {
            try {
                Future br = b.broadcast(
                        mapper.writeValueAsString(
                                mapper.readValue(
                                        "{\"message\":\"" + msg+ "\","+
                                                "\"who\":\""+ who+"\"" + "," +
                                                "\"type\":\"northPole\"}", Data.class)));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}