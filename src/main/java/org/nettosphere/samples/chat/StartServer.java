package org.nettosphere.samples.chat;

import WishListProcess.JCSPNetworkServiceIn;
import WishListProcess.JCSPNetworkServiceOut;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.Parallel;

public class StartServer {

    public static void main(String[] args){

        NettoServer ns = new NettoServer();
        Any2OneChannel printOut = Channel.any2one();

        CSProcess[] procs = {
            new AlternateInput(printOut.in()),
            new JCSPNetworkServiceIn(NorthPole.GetChannel.getInput(), "Wish", "5563"),
            new JCSPNetworkServiceOut(printOut.out(), "Print", "5565"),
            new JCSPNetworkServiceOut(printOut.out(), "Print", "5566")
        };

        new Parallel(procs).run();
    }

}
