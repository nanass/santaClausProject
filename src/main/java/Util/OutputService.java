package Util;

import org.jeromq.ZMQ;

public class OutputService {

    private ZMQ.Socket publisher;
    private ZMQ.Context context;
    final String port;

    public OutputService(String port){
        this.port = port;
        context = ZMQ.context(1);
        publisher = context.socket(ZMQ.PUSH);
        publisher.connect("tcp://localhost:"+port);
    }

    public void send(Data data){
        publisher.send(data.toByteArray());
    }
}
