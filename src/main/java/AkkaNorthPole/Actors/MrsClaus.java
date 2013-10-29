package AkkaNorthPole.Actors;

import AkkaNorthPole.Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import akka.actor.ActorRef;
import akka.actor.Props;

public class MrsClaus extends NorthPoleActor{


    public static Props mkProps(String name) {
        return Props.create(NorthPoleActor.class, name);
    }

    private final ActorRef santa;

    public MrsClaus(ActorRef santa){
        super("MrsClaus");
        this.santa = santa;
        reQueue(new Msg(NorthPoleMsg.Back), getSelf(), 1000, getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception{
        Msg msg = (AkkaNorthPole.Messages.Msg)message;
        switch (msg.nPMsg){
            case Back:
                reQueue(new Msg(NorthPoleMsg.Cookies), getSelf(), 2000, santa);
                log("Going to Santa's");
                break;
            case Wait:
                log("Santa is Busy...Waiting");
                reQueue(new Msg(NorthPoleMsg.Cookies), getSelf(), 2000, santa);
                log("Going to Santa's");
                break;
            case Done:
                log("Going back home");
                reQueue(new Msg(NorthPoleMsg.Back), getSelf(), 2000, getSelf());
                break;

        }
    }
}
