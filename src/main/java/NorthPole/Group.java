package NorthPole;

import org.nettosphere.samples.chat.AlternateInput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class Group implements Runnable {

    private LinkedBlockingQueue<SantasFriend> group;
    int size;
	final public String type;
    private ArrayList<String> names = new ArrayList<String>();
    private final AlternateInput ai;

    Group(int i, String type, AlternateInput ai)
    {
        group = new LinkedBlockingQueue<SantasFriend>(i);
        size = i;
	    this.type = type;
        this.ai = ai;
    }

    public void add(SantasFriend friend) throws InterruptedException
    {
         names.add(friend.name);
         group.put(friend);
    }

    public int getSize()
    {
        return group.size();
    }

    public void releaseGroup(CyclicBarrier work) throws InterruptedException, BrokenBarrierException {
	    Iterator<SantasFriend> groupList = group.iterator();
	    names.clear();
        while(groupList.hasNext())
        {
	        groupList.next().doneWithSanta();
        }
	    group.clear();
	    work.await();
    }

    public void groupActivity (Santa.UnitOfWork unitOfWork, CyclicBarrier work) throws InterruptedException, BrokenBarrierException {
        Thread.sleep(2000);
        for (String name : names){
            log(name, unitOfWork.name);
        }
    }

    public void run() {}

    public void log(String n, String s){
        System.out.println(n + ": "  + s);
        ai.send(s,n);
    }
}