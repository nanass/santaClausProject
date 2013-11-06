package Wishlist;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class StartWishList {

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("Server");
        system.actorOf(Props.create(WishList.class));
    }

}
