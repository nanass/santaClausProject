package AkkaNorthPole.Main;

import AkkaNorthPole.Actors.*;
import AkkaNorthPole. Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.nettosphere.samples.chat.AlternateInput;
import org.nettosphere.samples.chat.NettoServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum name{ Dasher, Dancer, Prancer, Vixen, Comet, Cupid, Donder, Blitzen, Ruldolph }

class NorthPole{
    public static void main(String[] args) throws IOException {

        NettoServer ns = new NettoServer();

        List<ActorRef> elves = new ArrayList<ActorRef>();
        List<ActorRef> reindeer = new ArrayList<ActorRef>();

        ActorSystem system = ActorSystem.create("NorthPole");
        org.nettosphere.samples.chat.NorthPole.SendToActors.aiActorRefRef(system.actorOf(Props.create(AlternateInput.class)));
        ActorRef wishList = system.actorOf(Props.create(WishList.class));
        org.nettosphere.samples.chat.NorthPole.SendToActors.setActorRef(wishList);
        ActorRef santa = system.actorOf(Props.create(Santa.class, "Santa", wishList));
        ActorRef secretary = system.actorOf(Props.create(WaitingRoom.class, "WaitingRoom", santa));
        santa.tell(new Msg(NorthPoleMsg.Secretary), secretary);

        for (int i = 1; i <= 10; i++){
            elves.add(system.actorOf(Props.create(Elf.class, "Elf" + String.valueOf(i), secretary)));
        }
        for (name r : name.values()){
            reindeer.add(system.actorOf(Props.create(Reindeer.class, r.toString(), secretary)));
        }
        ActorRef mrsClaus = system.actorOf(Props.create(MrsClaus.class, santa));

    }
}
