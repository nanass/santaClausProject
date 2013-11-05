package org.nettosphere.samples.chat;

import Util.InputService;
import Util.OutputService;

public class RunServer {

    public static void main(String[] args){
        NettoServer ns = new NettoServer();

        OutputService out = new OutputService("5563");
        AlternateInput ai = new AlternateInput();
        NorthPole.SendToOutput.setOutputService(out);
        new Thread(new InputService(ai, "5564")).start();
        new Thread(new InputService(ai, "5565")).start();
    }
}
