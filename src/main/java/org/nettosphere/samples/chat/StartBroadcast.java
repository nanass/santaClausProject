package org.nettosphere.samples.chat;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;

public class StartBroadcast {
    StartBroadcast(){
        Broadcaster br = BroadcasterFactory.getDefault().lookup("/");
        AlternateInput ai = new AlternateInput(br);
        new Thread(ai).start();
    }
}
