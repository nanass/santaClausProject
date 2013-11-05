package NorthPole;

import Util.Data;
import Util.OutputService;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class SantasFriend implements Runnable {
    private Random randomGenerator = new Random();
    public final String name;
    private final WaitingRoom waitingRoomRef;
	public final String type;
    private final OutputService out;
    SynchronousQueue<Integer> wait = new SynchronousQueue<Integer>();

    SantasFriend (String name, WaitingRoom waitingRoomRef, String type)
    {
        this.name = name;
        this.waitingRoomRef = waitingRoomRef;
	    this.type = type;
        out = new OutputService("5565");
    }

    public void withSanta() throws InterruptedException
    {
         wait.take();
    }

    public void doneWithSanta() throws InterruptedException
    {
        wait.put(1);
    }

	 public void run()
    {
        try
        {
            Thread.sleep(randomGenerator.nextInt(100) * 200);
        }
        catch (Exception e) {log("Interrupted exception");}
        while(true)
        {
            try
            {
                Thread.sleep(randomGenerator.nextInt(100) * 200);
                log("Working");
                if (waitingRoomRef.receiveVisitor(this).equals("RoomFull"))
                {
                    log("Waiting room is full");
                    Thread.sleep(randomGenerator.nextInt(100) * 200);
                }
                else
                {
                    log ("In the waiting room");
                    withSanta();
                    log("Going to work");
                    Thread.sleep(randomGenerator.nextInt(100) * 200);
                }
            }
            catch (InterruptedException e) {log("Interrupted exception");}
        }
    }
    public void log(String s){
        System.out.println(name + ": "  + s);
        out.send(new Data(name, s));
    }
}