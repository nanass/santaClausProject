package org.nettosphere.samples.chat;

import org.atmosphere.cpr.Broadcaster;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.Future;

public class AlternateInput {
    Broadcaster  b;
    public AlternateInput(Broadcaster b){
        this.b = b;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public void send(String msg, String who)  {
        try {
            Future br = b.broadcast(
                    mapper.writeValueAsString(
                            mapper.readValue(
                                    "{\"message\":\"" + msg+ "\","+
                                            "\"who\":\""+ who+"\"" + "," +
                                            "\"type\":\"northPole\"}", Data.class)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
