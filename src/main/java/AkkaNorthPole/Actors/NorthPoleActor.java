package AkkaNorthPole.Actors;

import AkkaNorthPole.Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import Util.OutputActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;
import Util.Data;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public abstract class NorthPoleActor extends UntypedActor{

    final String name;
    private State state;
    Random randomGenerator = new Random();
    ActorRef output;
    public static Props mkProps(String name) {
        return Props.create(NorthPoleActor.class, name);
    }

    public NorthPoleActor (String name) {
        this.name = name;
        System.out.println(name + " is starting at " + this.self().path().toString());
        output = getContext().actorOf(Props.create(OutputActor.class, "5563"));
    }

    protected void setState(State s) {
        if (state != s) {
            state = s;
        }
    }

    protected State getState(){
        return state;
    }

    public void log(String s){
        System.out.println(name + ": "  + s);
        output.tell(new Data(name, s), getSelf());
    }

    protected void reQueue(NorthPoleMsg msg){
          reQueue(new Msg(msg), self(), randomGenerator.nextInt(100) * 100, self());
    }

    protected void batchSend(List<ActorRef> who, Msg what){
        for (ActorRef a : who){
            a.tell(what, getSelf());
        }
    }
    protected void reQueue(final Msg msg, final ActorRef sender, int time, final ActorRef reciever) {
        context().system().scheduler().scheduleOnce(Duration.create(time, TimeUnit.MILLISECONDS),
        new Runnable() {
            @Override
            public void run() {
                reciever.tell(msg, sender);
            }
        }, context().system().dispatcher());
    }

    @Override
    public void onReceive(Object message) throws Exception {}

}
