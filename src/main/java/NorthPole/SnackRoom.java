package NorthPole;


import java.util.concurrent.LinkedBlockingQueue;

public class SnackRoom {

    private Boolean santaIsWithElves = false;
    private Boolean mrsClausIsWaiting = false;
    private LinkedBlockingQueue<Integer> wait = new LinkedBlockingQueue<Integer>();
    public SnackRoom(){}

    public Boolean isWaiting(){
        return mrsClausIsWaiting;
    }

    public Boolean isWithElves(){
        return santaIsWithElves;
    }

    public Integer waitWithCookies(){
        mrsClausIsWaiting = true;
        Integer a = null;
        try {
            a =  wait.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void setMeeting(){
        santaIsWithElves = true;
    }

    public void doneMeeting(){
        santaIsWithElves = false;
    }

    public void doneEating(Integer a){
        mrsClausIsWaiting = false;
        try {
            wait.put(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
