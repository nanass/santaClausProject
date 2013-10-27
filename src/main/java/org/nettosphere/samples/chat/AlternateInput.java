package org.nettosphere.samples.chat;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Future;

public class AlternateInput {
    Broadcaster  b;
    public AlternateInput(Broadcaster b){
        this.b = b;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public void send(String msg, String who)  {
        try {
            b.broadcast(
                    mapper.writeValueAsString(
                            mapper.readValue(
                                    "{\"message\":\"" + msg +
                                      "\",\"who\":\"" + who +
                                 "\"}",
                                    Data.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
