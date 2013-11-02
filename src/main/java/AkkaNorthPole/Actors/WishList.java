package AkkaNorthPole.Actors;

import AkkaNorthPole.Messages.InterfaceMsg;
import akka.actor.UntypedActor;
import org.nettosphere.samples.chat.Data;
import org.nettosphere.samples.chat.NorthPole;
import java.util.concurrent.CopyOnWriteArrayList;


public class WishList extends UntypedActor {

    private CopyOnWriteArrayList<Data> wishList = new CopyOnWriteArrayList<Data>();

    @Override
    public void onReceive(Object o) throws Exception {

        if(o instanceof Data){
            Data d = (Data)o;
            addToWishList(d);
        }
        else if(o instanceof String){
            String message = (String)o;
            if(message.equals("Deliver")){
                deliverGifts();
            }
        }
    }

    public void deliverGifts(){
        String output = "";
        System.out.println("Gifts for: ");
        for(Data d : wishList){
            System.out.println(d.getAuthor() + " : " + d.getMessage());
            output += d.getAuthor() + " , " + d.getMessage() +" ";
        }
        wishList.clear();
        NorthPole.SendToActors.aiActorRefRef().tell(new InterfaceMsg("all", output), null);
    }

    public void addToWishList(Data msg){
        if(msg.getType().equals("wishlist")){
            wishList.add(msg);
            System.out.println(msg.getMessage());
        }
    }
}
