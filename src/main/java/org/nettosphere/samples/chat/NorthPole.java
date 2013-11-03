package org.nettosphere.samples.chat;

import Util.Data;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.jcsp.lang.*;

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
        Data data = mapper.readValue(message, Data.class);
        GetChannel.getOutput().write(data);
        return mapper.writeValueAsString(data);
    }

    public final static class GetChannel{

        public static Any2OneChannel a2a = Channel.any2one();

        public static AltingChannelInput getInput(){
            return a2a.in();
        }

        public static ChannelOutput getOutput(){
            return a2a.out();
        }
    }

}