package NorthPole;

import org.nettosphere.samples.chat.AlternateInput;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitingRoom
{
    public Group group;
	public Boolean isWaiting;
	ExecutorService es = Executors.newSingleThreadExecutor();
	final Santa santaRef;

	public void releaseGroup() throws InterruptedException
	{
		isWaiting = false;
	}

    WaitingRoom(Santa santaRef, int size, String type, AlternateInput ai)
    {
        this.santaRef = santaRef;
	    group = new Group(size, type, ai);
	    isWaiting = false;
        (new Thread(group)).start();
    }

    public String receiveVisitor(SantasFriend e) throws InterruptedException
    {
        if (group.getSize() < group.size && !isWaiting)
        {
            group.add(e);
            if (group.getSize() == group.size)
            {
	            isWaiting = true;
				es.submit(new MeetSanta(group, group.type));
            }
            return "Waiting";
        }
        else
            return "RoomFull";
    }

    class MeetSanta implements Runnable
    {
	    String type;
	    Group group;
	    MeetSanta(Group group, String type)
	    {
		    this.type = type;
		    this.group = group;
	    }
	    public void run()
	    {
		    Boolean found = false;
		    while(!found)
		    {
			    try
			    {
				    found = santaRef.wake();
			    } catch (InterruptedException e){
                    System.out.println("Interrupted exception at WaitingRoom");}
			      catch (BrokenBarrierException e) {
                      System.out.println("Barrier exception at WaitingRoom");}
		    }
	    }
    }
}