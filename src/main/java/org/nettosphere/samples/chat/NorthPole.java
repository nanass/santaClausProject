package org.nettosphere.samples.chat;

import akka.actor.ActorRef;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.*;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

@ManagedService(path = "/northpole")
public class NorthPole {

    private final ObjectMapper mapper = new ObjectMapper();
    Broadcaster b = BroadcasterFactory.getDefault().get("/");

    @Get
    public void onOpen(final AtmosphereResource r) {
        b.addAtmosphereResource(r);
    }

    @Message
    public String onMessage(String message) throws IOException {
        Data d = mapper.readValue(message, Data.class);
        SendToActors.getActorRef().tell(d, null);
        return mapper.writeValueAsString(d);

    }

    public final static class SendToActors{

        private static ActorRef actorRef;
        private static ActorRef aiActorRef;
        public static void setActorRef(ActorRef a){
            actorRef = a;
        }
        public static ActorRef getActorRef(){
            return actorRef;
        }
        public static void aiActorRefRef(ActorRef a){
            aiActorRef = a;
        }
        public static ActorRef aiActorRefRef(){
            return aiActorRef;
        }
    }
}