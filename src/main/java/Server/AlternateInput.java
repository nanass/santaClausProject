package Server;

import Util.Data;
import Util.InputActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.concurrent.Future;

public class AlternateInput extends UntypedActor{

    Broadcaster b;
    private ActorRef input;
    public AlternateInput() {
        this.b = BroadcasterFactory.getDefault().lookup("/");
        this.input = getContext().actorOf(Props.create(InputActor.class, "5563", getSelf()));
        input.tell("Start", getSelf());
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onReceive(Object message) throws Exception {
        Data data = (Data)message;
        Future br = b.broadcast(
                mapper.writeValueAsString(
                        mapper.readValue(
                                "{\"message\":\"" + data.getMessage() + "\","+
                                        "\"who\":\""+ data.getWho() +"\"" + "," +
                                        "\"type\":\"northPole\"}", Data.class)));
    }
}
