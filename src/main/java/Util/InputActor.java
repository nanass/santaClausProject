package Util;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import org.jeromq.ZMQ;

public class InputActor extends UntypedActor{
    String port;
    ActorRef responder;
    ZMQ.Context context;
    ZMQ.Socket pull;

    public InputActor(String port, ActorRef responder){
        this.port = port;
        this.responder = responder;
        context = ZMQ.context(1);
        pull = context.socket(ZMQ.PULL);
        pull.bind("tcp://*:" + port);
    }

    private void run(){
        while (true) {
            Data contents = Data.buildFromBytes(pull.recv());
            responder.tell(contents, getSelf());
        }
    }
    private void stop(){
        pull.close();
        context.term();
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof String){
            if (o.equals("Start")){
                run();
            }
            else if(o.equals("End")){
                stop();
            }
        }
    }
}
