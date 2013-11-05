package org.nettosphere.samples.chat;

import Util.Command;
import Util.Data;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.Future;

class AlternateInput implements Command {

    Broadcaster  b;
    public AlternateInput(){
        b = BroadcasterFactory.getDefault().lookup("/");
    }
    private final ObjectMapper mapper = new ObjectMapper();

    public void send(Data data)  {
        try {
            Future br = b.broadcast(
                    mapper.writeValueAsString(
                            mapper.readValue(
                                    "{\"message\":\"" + data.getMessage() + "\","+
                                            "\"who\":\""+ data.getWho() + "\"" + "," +
                                            "\"type\":\"northPole\"}", Data.class)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() { }

    @Override
    public void execute(Data data) {
        send(data);
    }

}
