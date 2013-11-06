package AkkaNorthPole.Main;

import AkkaNorthPole.Actors.*;
import AkkaNorthPole. Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import Util.OutputActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum name{ Dasher, Dancer, Prancer, Vixen, Comet, Cupid, Donder, Blitzen, Ruldolph }

class NorthPole{
    public static void main(String[] args) throws IOException {

        List<ActorRef> elves = new ArrayList<ActorRef>();
        List<ActorRef> reindeer = new ArrayList<ActorRef>();

        ActorSystem system = ActorSystem.create("NorthPole");
        ActorRef wishList = system.actorOf(Props.create(OutputActor.class, "5564"));
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
