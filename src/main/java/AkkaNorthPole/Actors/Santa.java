package AkkaNorthPole.Actors;

import AkkaNorthPole.Messages.Msg;
import AkkaNorthPole.Messages.NorthPoleMsg;
import akka.actor.ActorRef;

import java.util.HashMap;
import java.util.List;

public class Santa extends NorthPoleActor{

    enum santaState implements State {awake, asleep, welcomingElves,
                                      consultingWithElves, dismissingElves,
                                      hitchingReindeer, deliveringToys,
                                      dismissingReindeer}
    int wait = 0;
    private ActorRef secretary;
    private HashMap<String, Msg> visitorQueue = new HashMap<String, Msg>();
    public Santa(String name) {
        super(name);
        setState(santaState.asleep);
        log("Sleeping");
    }
    @Override
    public void onReceive(Object message) throws Exception {
        Msg msg = (Msg)message;
        switch (msg.nPMsg) {
            case WakeUp :
                 log(msg.group + " woke santa");
                 visitorQueue.put(msg.group, msg);
                 if(getState().equals(santaState.asleep)) {
                     if(visitorQueue.containsKey("Reindeer")) {
                        if(msg.group.equals("Elves")){
                            getSender().tell(new Msg(NorthPoleMsg.Wait, msg.group, msg.who), getSelf());
                        }
                        handleVisitors(visitorQueue.get("Reindeer"));
                        visitorQueue.remove("Reindeer");
                     }
                     else {
                        handleVisitors(visitorQueue.get("Elves"));
                        visitorQueue.remove("Elves");
                     }
                 }
                 else {
                     getSender().tell(new Msg(NorthPoleMsg.Wait, msg.group, msg.who), getSelf());
                 }
            break;
            case Done :
                if (wait > 1) {
                    wait--;
                }
                else {
                    switch((santaState)getState()){
                        case welcomingElves :
                            log("Welcoming elves");
                            setState(santaState.consultingWithElves);
                            doAction(msg.who, new Msg(NorthPoleMsg.Consult, msg.group, msg.who));
                            break;
                        case consultingWithElves :
                            setState(santaState.dismissingElves);
                            doAction(msg.who, new Msg(NorthPoleMsg.GoodBye, msg.group, msg.who));
                            break;
                        case dismissingElves :
                            log("Sleeping");
                            setState(santaState.asleep);
                            secretary.tell(new Msg(NorthPoleMsg.Dismiss, msg.group, msg.who),getSelf());
                            break;
                        case hitchingReindeer :
                            log("Harnessing reindeer");
                            setState(santaState.deliveringToys);
                            doAction(msg.who, new Msg(NorthPoleMsg.Deliver, msg.group, msg.who));
                            break;
                        case deliveringToys :
                            setState(santaState.dismissingReindeer);
                            doAction(msg.who, new Msg(NorthPoleMsg.Unhitch, msg.group, msg.who));
                            break;
                        case dismissingReindeer :
                            log("Sleeping");
                            setState(santaState.asleep);
                            secretary.tell(new Msg(NorthPoleMsg.Dismiss, msg.group, msg.who),getSelf());
                            break;
                    }
                }
                break;
            case Secretary :
                secretary = getSender();
                break;
            default:
                break;
        }
    }

    private void doAction(List<ActorRef> who, Msg what){
        wait = who.size();
        batchSend(who,what);
    }

    private void handleVisitors(Msg msg){
        setState(santaState.awake);
        log("Waking up");
        if(msg.group.equals("Elves")){
            setState(santaState.welcomingElves);
            doAction(msg.who, new Msg(NorthPoleMsg.ComeIn, msg.group, msg.who));
        }
        else if(msg.group.equals("Reindeer")){
            setState(santaState.hitchingReindeer);
            doAction(msg.who, new Msg(NorthPoleMsg.Hitch, msg.group, msg.who));
        }
    }
}
