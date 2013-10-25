package AkkaNorthPole.Messages;

import akka.actor.ActorRef;

import java.util.List;

public class Msg {

    public final NorthPoleMsg nPMsg;
    public final String group;
    public final List<ActorRef> who;

    public Msg (NorthPoleMsg nPMsg){
       this(nPMsg,"",null);
    }
    public Msg (NorthPoleMsg nPMsg, String group){
        this(nPMsg,group,null);
    }
    public Msg(NorthPoleMsg nPMsg, String group, List<ActorRef> who){
        this.nPMsg = nPMsg;
        this.group = group;
        this.who = who;
    }
}
