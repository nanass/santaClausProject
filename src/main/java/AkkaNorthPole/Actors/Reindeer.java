package AkkaNorthPole.Actors;

import AkkaNorthPole.Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import akka.actor.ActorRef;

public class Reindeer extends NorthPoleActor{

    private final ActorRef secretary;

    enum ReindeerState implements State {
        onVacation, backFromVacation, inWaitingRoom,
        hitched, deliveringToys
    }

    public Reindeer(String name) {
        this(name,null);
    }

    public Reindeer(String name, ActorRef secretary) {
        super(name);
        this.secretary = secretary;
        setState(ReindeerState.onVacation);
        reQueue(NorthPoleMsg.Back);
    }

    @Override
    public void onReceive(Object message) throws Exception {

        Msg msg = (Msg)message;
        Thread.sleep(randomGenerator.nextInt(100) * 20);
        switch(msg.nPMsg){
            case Back:
                log("Back from vacation");
                secretary.tell(new Msg(NorthPoleMsg.Wait, "Reindeer"), getSelf());
                setState(ReindeerState.backFromVacation);
                break;
            case Allowed:
                log ("In the waiting room");
                setState(ReindeerState.inWaitingRoom);
                break;
            case Full:
                log("Waiting room is full");
                reQueue(NorthPoleMsg.Back);
                break;
            case Last:
                log("Going to wake santa");
                sender().tell(new Msg(NorthPoleMsg.WakeUp, msg.group, msg.who), getSelf());
                break;
            case Hitch:
                log("Getting Hitched");
                setState(ReindeerState.hitched);
                sender().tell(new Msg(NorthPoleMsg.Done, msg.group, msg.who), getSelf());
                break;
            case Deliver:
                log("Delivering Toys");
                setState(ReindeerState.deliveringToys);
                sender().tell(new Msg(NorthPoleMsg.Done, msg.group, msg.who), getSelf());
                break;
            case Unhitch:
                log("Getting unhitched");
                sender().tell(new Msg(NorthPoleMsg.Done, msg.group, msg.who), getSelf());
                break;
            case GoOnVacation:
                log("Going on Vacation");
                setState(ReindeerState.onVacation);
                reQueue(NorthPoleMsg.Back);
                break;
            case Wait:
                log("Waiting");
                reQueue(new Msg(NorthPoleMsg.WakeUp, msg.group, msg.who), getSelf(), 100, getSender());
                break;
            default:
                break;
        }
    }
}
