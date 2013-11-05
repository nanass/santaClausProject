package org.nettosphere.samples.chat;

import Util.Data;
import Util.OutputService;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.*;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

@ManagedService(path = "/northpole")
public class NorthPole {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static Broadcaster b = BroadcasterFactory.getDefault().get("/");

    @Get
    public void onOpen(final AtmosphereResource r) {
        b.addAtmosphereResource(r);
    }

    @Message
    public String onMessage(String message) throws IOException {
        Data d = mapper.readValue(message, Data.class);
        SendToOutput.getOut().send(d);
        return mapper.writeValueAsString(d);
    }

    public static class SendToOutput{
        private static OutputService out;
        public static void setOutputService(OutputService output){
            out = output;
        }
        public static OutputService getOut(){
            return out;
        }
    }
}