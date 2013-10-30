package JCSPNorthPole;

import org.jcsp.lang.Bucket;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.CSTimer;
import org.jcsp.lang.ChannelOutput;

public class MrsClaus implements CSProcess {

    private final Bucket deliverCookies;
    private final ChannelOutput printOut;
    private final String name = "MrsClaus";
    private final CSTimer timer = new CSTimer();

    MrsClaus(Bucket deliverCookies, ChannelOutput printOut){
        this.deliverCookies = deliverCookies;
        this.printOut = printOut;
    }

    @Override
    public void run() {
        while(true){
           log("Going to Santa's");
           timer.sleep(10000);
           log("Waiting with Cookies");
           deliverCookies.fallInto();
           log("Leaving Santa's");
           timer.sleep(5000);
        }
    }

    public void log(String s){
        System.out.println(name + ": "  + s);
        printOut.write(new NorthPoleInterfaceMsg(s,name));
    }

}
