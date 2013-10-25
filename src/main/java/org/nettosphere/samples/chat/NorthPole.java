package org.nettosphere.samples.chat;

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
        return mapper.writeValueAsString(mapper.readValue(message, Data.class));
    }

}