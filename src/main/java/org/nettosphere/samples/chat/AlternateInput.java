package org.nettosphere.samples.chat;

import Util.Data;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import java.io.IOException;
import java.util.concurrent.Future;

public class AlternateInput implements CSProcess {
    final ChannelInput in;

    Broadcaster b;

    public AlternateInput(ChannelInput in) {
        this.b = BroadcasterFactory.getDefault().lookup("/");
        this.in = in;
    }

    private final ObjectMapper mapper = new ObjectMapper();
    public void run() {
        while (true) {

            Data a = (Data)in.read();

            try {
                Future br = b.broadcast(
                        mapper.writeValueAsString(
                                mapper.readValue(
                                        "{\"message\":\"" + a.getMessage() + "\","+
                                         "\"who\":\""+a.getWho()+"\"" + "," +
                                         "\"type\":\"northPole\"}",Data.class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
