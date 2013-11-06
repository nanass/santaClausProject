package Wishlist;

import Util.InputActor;
import Util.OutputActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import Util.Data;
import java.util.concurrent.CopyOnWriteArrayList;


public class WishList extends UntypedActor {

    private CopyOnWriteArrayList<Data> wishList = new CopyOnWriteArrayList<Data>();
    private ActorRef delivery;
    private ActorRef wish;
    private ActorRef out;

    WishList(){
        delivery = getContext().actorOf(Props.create(InputActor.class, "5564", getSelf()));
        wish = getContext().actorOf(Props.create(InputActor.class, "5565", getSelf()));
        out = getContext().actorOf(Props.create(OutputActor.class, "5563"));
        delivery.tell("Start", getSelf());
        wish.tell("Start", getSelf());
    }

    @Override
    public void onReceive(Object o) throws Exception {

        if(o instanceof Data){
            Data d = (Data)o;
            if(d.getMessage().equals("Deliver")){
                deliverGifts();
            }
            else{
                addToWishList(d);
            }
        }
    }

    private void deliverGifts(){
        String output = "";
        System.out.println("Gifts for: ");
        for(Data d : wishList){
            System.out.println(d.getAuthor() + " : " + d.getMessage());
            output += d.getAuthor() + " , " + d.getMessage() +" ";
        }
        wishList.clear();
        out.tell(new Data("all", output), getSelf());
    }

    private void addToWishList(Data msg){
        if(msg.getType().equals("wishlist")){
            wishList.add(msg);
            System.out.println(msg.getMessage());
        }
    }
}
