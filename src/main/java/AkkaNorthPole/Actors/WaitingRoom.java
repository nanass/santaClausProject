package AkkaNorthPole.Actors;

import AkkaNorthPole.Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import akka.actor.ActorRef;

import java.util.ArrayList;
import java.util.List;

public class WaitingRoom extends NorthPoleActor{

    private List<ActorRef> elves = new ArrayList();
    private List<ActorRef> reindeer = new ArrayList();
    secretaryState reindeerWaiting;
    secretaryState elfWaiting;
    private ActorRef santa;
    enum secretaryState implements State{full, notFull}

    public WaitingRoom(String name) {
        super(name);
    }
    public WaitingRoom(String name, ActorRef santa){
        this(name);
        this.santa = santa;
        reindeerWaiting = secretaryState.notFull;
        elfWaiting = secretaryState.notFull;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        Thread.sleep(randomGenerator.nextInt(100) * 2);
        Msg msg = (Msg)message;
        switch (msg.nPMsg) {
            case Wait :
                if(msg.group.equals("Elves"))
                    queue(elves, msg.group, 3);
                else if(msg.group.equals("Reindeer"))
                    queue(reindeer, msg.group, 9);
                break;
            case Dismiss :
                if(msg.group.equals("Elves")) {
                    batchSend(msg.who, new Msg(NorthPoleMsg.GetToWork));
                    elves.clear();
                    elfWaiting = secretaryState.notFull;
                }
                else if(msg.group.equals("Reindeer")) {
                    batchSend(msg.who, new Msg(NorthPoleMsg.GoOnVacation));
                    reindeer.clear();
                    reindeerWaiting = secretaryState.notFull;
                }
            default:
        }
    }
    private void queue(List<ActorRef> who, String group, int size){
        if((group.equals("Elves") && elfWaiting.equals(secretaryState.notFull)) ||
                (group.equals("Reindeer") && reindeerWaiting.equals(secretaryState.notFull))){
            getSender().tell(new Msg(NorthPoleMsg.Allowed), santa);
            who.add(sender());
            if(who.size() == size){
               if(group.equals("Elves"))
                   elfWaiting = secretaryState.full;
               else if(group.equals("Reindeer"))
                   reindeerWaiting = secretaryState.full;
                getSender().tell(new Msg(NorthPoleMsg.Last, group, who), santa);
            }
        }
        else{
            getSender().tell(new Msg(NorthPoleMsg.Full), getSelf());
        }
    }



}
