package org.nettosphere.samples.chat;

import akka.actor.UntypedActor;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;
import AkkaNorthPole.Messages.InterfaceMsg;
import java.util.concurrent.Future;

public class AlternateInput extends UntypedActor{

    Broadcaster b;

    public AlternateInput() {
        this.b = BroadcasterFactory.getDefault().lookup("/");
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onReceive(Object message) throws Exception {
        InterfaceMsg msg = (InterfaceMsg)message;
        Future br = b.broadcast(
                mapper.writeValueAsString(
                        mapper.readValue(
                                "{\"message\":\"" + msg.what+ "\","+
                                        "\"who\":\""+ msg.who+"\"" + "," +
                                        "\"type\":\"northPole\"}", Data.class)));
    }
}
