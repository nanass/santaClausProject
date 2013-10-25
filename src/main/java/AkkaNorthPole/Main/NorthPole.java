package AkkaNorthPole.Main;

import AkkaNorthPole.Actors.Elf;
import AkkaNorthPole.Actors.Reindeer;
import AkkaNorthPole.Actors.Santa;
import AkkaNorthPole.Actors.WaitingRoom;
import AkkaNorthPole. Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.ArrayList;
import java.util.List;

public class NorthPole {
    enum name{ Dasher, Dancer, Prancer, Vixen, Comet, Cupid, Donder, Blitzen, Ruldolph }
    public static void main(String[] args){
        List<ActorRef> elves = new ArrayList<ActorRef>();
        List<ActorRef> reindeer = new ArrayList<ActorRef>();
        ActorSystem system = ActorSystem.create("NorthPole");
        ActorRef santa = system.actorOf(Props.create(Santa.class, "Santa Claus"));
        ActorRef secretary = system.actorOf(Props.create(WaitingRoom.class, "WaitingRoom", santa));
        santa.tell(new Msg(NorthPoleMsg.Secretary), secretary);
        for (int i = 0; i < 9; i++){
            elves.add(system.actorOf(Props.create(Elf.class, "Elf " + String.valueOf(i), secretary)));
        }
        for (name r : name.values()){
            reindeer.add(system.actorOf(Props.create(Reindeer.class, r.toString(), secretary)));
        }
    }
}
