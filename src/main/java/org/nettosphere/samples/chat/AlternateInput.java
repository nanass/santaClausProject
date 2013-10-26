package org.nettosphere.samples.chat;

import JCSPNorthPole.NorthPoleInterfaceMsg;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
           NorthPoleInterfaceMsg a = (NorthPoleInterfaceMsg)in.read();
            try {
                Future br = b.broadcast(mapper.writeValueAsString(mapper.readValue("{\"message\":\"" + a.msg+ "\",\"who\":\""+a.who+"\"}",Data.class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
