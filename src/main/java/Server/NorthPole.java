package Server;

import Util.Data;
import akka.actor.ActorRef;
import akka.actor.Props;
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

    public static Props mkProps(String name) {
        return Props.create(NorthPole.class);
    }

    @Get
    public void onOpen(final AtmosphereResource r) {
        b.addAtmosphereResource(r);
    }

    @Message
    public String onMessage(String message) throws IOException {
        Data d = mapper.readValue(message, Data.class);
        InjectOutput.wishList.tell(d, null);
        return mapper.writeValueAsString(d);

    }
    public static class InjectOutput{
        public static ActorRef wishList;
    }
}