package WishListProcess;

import Util.Data;
import org.jcsp.lang.*;
import org.jeromq.ZMQ;

public class JCSPNetworkServiceIn implements CSProcess {

    private final ChannelInput in;
    String topic;
    String port;

    public JCSPNetworkServiceIn(ChannelInput in, String topic, String port){
        this.in = in;
        this.topic = topic;
        this.port = port;
    }

    @Override
    public void run() {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:" + port);
        while(!Thread.currentThread().isInterrupted()){
            Data d = (Data)in.read();
            System.out.println(d.getWho() + " : " + d.getMessage());
            publisher.sendMore(topic);
            publisher.send(d.toByteArray());
        }
        publisher.close();
        context.term ();
    }

}