package Util;
import akka.actor.UntypedActor;
import org.jeromq.ZMQ;

public class OutputActor extends UntypedActor{

    private ZMQ.Socket push;
    private ZMQ.Context context;
    final String port;

    public OutputActor(String port){
        this.port = port;
        context = ZMQ.context(1);
        push = context.socket(ZMQ.PUSH);
        push.connect("tcp://localhost:" + port);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Data){
            Data data = (Data)o;
            push.send(data.toByteArray());
        }
    }
}
