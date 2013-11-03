package JCSPNorthPole;

import Util.Data;
import org.jcsp.lang.*;

import java.util.Random;

public class Reindeer implements CSProcess
{
    public Reindeer(String name, AltingBarrier stable, AltingBarrier sleigh, ChannelOutput harness, ChannelInput harnessed,
                    ChannelInput returned, ChannelInput unharness, ChannelOutput printOut){
        this.name = name;
        this.stable = stable;
        this.sleigh = sleigh;
        this.harness = harness;
        this.harnessed = harnessed;
        this.returned = returned;
        this.unharness = unharness;
        this.printOut = printOut;
    }

    String name;
    AltingBarrier stable;
    AltingBarrier sleigh;
    ChannelOutput harness;
    ChannelInput harnessed;
    ChannelInput returned;
    ChannelInput unharness;
    ChannelOutput printOut;
    int holidayTime = 4000;

    public void run(){
        Random rng = new Random();
        CSTimer timer = new CSTimer();
        while (true) {
            log("Going on Vacation");
            timer.sleep(holidayTime + rng.nextInt(holidayTime));
            log("Back from vacation");
            stable.sync();
            harness.write(name);
            log("Getting Hitched");
            harnessed.read();
            sleigh.sync();
            log("Delivering Toys");
            returned.read();
            log("Getting unhitched");
            unharness.read();
        }
    }
    public void log(String s){
        printOut.write(new Data(name, s));
    }
}
