package AkkaNorthPole.Actors;

import AkkaNorthPole.Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import akka.actor.ActorRef;

public class Elf extends NorthPoleActor{

    private ActorRef secretary;

    public Elf(String name) {
        super(name);
        setState(ElfState.onVacation);
    }
    public Elf(String name, ActorRef secretary){
        this(name);
        this.secretary = secretary;
        reQueue(NorthPoleMsg.Back);
    }

    enum ElfState implements State{
        onVacation, working, inWaitingRoom, withSanta
    }

    @Override
    public void onReceive(Object message) throws Exception {
        Msg msg = (Msg)message;
        Thread.sleep(randomGenerator.nextInt(100) * 20);
        switch(msg.nPMsg){
            case Back:
                log("Working");
                secretary.tell(new Msg(NorthPoleMsg.Wait, "Elves"), getSelf());
                setState(ElfState.working);
                break;
            case Allowed:
                log ("In the waiting room");
                setState(ElfState.inWaitingRoom);
                break;
            case Full:
                log("Waiting room is full");
                reQueue(NorthPoleMsg.Back);
                break;
            case Last:
                log("Going to wake santa");
                sender().tell(new Msg(NorthPoleMsg.WakeUp, msg.group, msg.who), getSelf());
                break;
            case ComeIn:
                log("Entering Santa's house");
                setState(ElfState.withSanta);
                sender().tell(new Msg(NorthPoleMsg.Done, msg.group, msg.who), getSelf());
                break;
            case Consult:
                log("Consulting with Santa");
                sender().tell(new Msg(NorthPoleMsg.Done, msg.group, msg.who), getSelf());
                break;
            case GoodBye:
                log("Leaving santa's");
                sender().tell(new Msg(NorthPoleMsg.Done, msg.group, msg.who), getSelf());
                break;
            case GetToWork:
                log("Going to work");
                setState(ElfState.onVacation);
                reQueue(NorthPoleMsg.Back);
                break;
            case Wait:
                log("Waiting");
                reQueue(new Msg(NorthPoleMsg.WakeUp, msg.group, msg.who), getSelf(), 100, getSender());
                break;
            case Cookies:
                reQueue(new Msg(NorthPoleMsg.Done, msg.group, msg.who), getSelf(), 100, getSender());
                break;
            default:
                break;

        }
    }
}
