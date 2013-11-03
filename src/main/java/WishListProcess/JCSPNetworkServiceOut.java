package WishListProcess;

import Util.Data;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jeromq.ZMQ;

public class JCSPNetworkServiceOut implements CSProcess {
    private final ChannelOutput out;
    String topic;
    String port;

    public JCSPNetworkServiceOut(ChannelOutput out, String topic, String port){
        this.topic = topic;
        this.port = port;
        this.out = out;
    }

    @Override
    public void run() {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://localhost:"+port);
        subscriber.subscribe(topic.getBytes());
        while (!Thread.currentThread ().isInterrupted ()) {
            String address = subscriber.recvStr();
            Data contents = Data.buildFromBytes(subscriber.recv());
            out.write(contents);
        }
        subscriber.close();
        context.term ();
    }


}
