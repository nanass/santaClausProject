package Util;

import org.jeromq.ZMQ;

public class InputService implements Runnable{

    Command cmd;
    String port;
    ZMQ.Context context;
    ZMQ.Socket subscriber;

    public InputService(Command cmd, String port){
        this.cmd = cmd;
        this.port = port;
        context = ZMQ.context(1);
        subscriber = context.socket(ZMQ.PULL);
        subscriber.bind("tcp://*:"+port);
    }

    public void run() {

        while (!Thread.currentThread ().isInterrupted ()) {
            Data contents = Data.buildFromBytes(subscriber.recv());
            cmd.execute(contents);
        }
        subscriber.close();
        context.term();
    }
}
