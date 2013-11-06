package Server;

import Util.OutputActor;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class StartServer {

    public static void main(String[] args){
        NettoServer ns = new NettoServer();
        ActorSystem system = ActorSystem.create("Server");
        system.actorOf(Props.create(AlternateInput.class));
        NorthPole.InjectOutput.wishList = system.actorOf(Props.create(OutputActor.class, "5565"));
    }
}
