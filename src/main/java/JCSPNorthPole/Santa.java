package JCSPNorthPole;

import org.jcsp.lang.*;

import java.util.List;
import java.util.Random;

public class Santa implements CSProcess {
    String name = "Santa";
    final ChannelOutput openForBusiness;
    final ChannelOutput consultationOver;
    final ChannelInput harness;
    final ChannelOutput harnessed;
    final ChannelOutput returned;
    final List<ChannelOutput> unharnessList;
    final AltingBarrier stable;
    final AltingBarrier sleigh;
    final AltingChannelInput consult;
    final List<ChannelOutput> consulting;
    final ChannelInput negotiating;
    final List<ChannelOutput> consulted;
    final ChannelOutput printOut;
    final ChannelOutput delivery;
    final Bucket cookiesReady;
    final int deliveryTime = 5000;
    final int consultationTime = 2000;

    public Santa(ChannelOutput openForBusiness, ChannelOutput consultationOver, ChannelInput harness, ChannelOutput harnessed,
            ChannelOutput returned, List<ChannelOutput> unharnessList, AltingBarrier stable, AltingBarrier sleigh,
            AltingChannelInput consult, List<ChannelOutput> consulting, ChannelInput negotiating,
            List<ChannelOutput> consulted, ChannelOutput printOut, Bucket cookiesReady, ChannelOutput delivery){
        this.openForBusiness = openForBusiness;
        this.consultationOver = consultationOver;
        this.harness = harness;
        this.harnessed = harnessed;
        this.returned = returned;
        this.unharnessList = unharnessList;
        this.stable = stable;
        this.sleigh = sleigh;
        this.consult = consult;
        this.consulting = consulting;
        this.negotiating = negotiating;
        this.consulted = consulted;
        this.printOut = printOut;
        this.cookiesReady = cookiesReady;
        this.delivery = delivery;
    }

    public void run(){
        Random rng = new Random();
        CSTimer timer = new CSTimer();
        Guard[] guards = {stable, consult};
        Alternative santaAlt = new Alternative(guards);
        openForBusiness.write(1);
        while (true) {
            int index = santaAlt.priSelect();
            switch (index) {
                case 0 : //Reindeer
                    String[] id = new String[9];
                    log("Harnessing reindeer");
                    for ( int i = 0; i <= 8; i++){
                        id[i] = (String)harness.read();
                    }
                    for ( int i = 0; i <= 8; i++){ harnessed.write(1); }
                    sleigh.sync();
                    delivery.write(1);
                    timer.sleep ( deliveryTime + rng.nextInt(deliveryTime));
                    for ( int i = 0; i <= 8; i++){  returned.write(1); }
                    log("Unharnessing reindeer");
                    for ( int i = 0; i <= 8; i++) {
                        unharnessList.get(i).write(1);
                    }
                    break;
                case 1: //Elves
                    Integer[] id2 = new Integer[9];
                    if (cookiesReady.holding() > 0) {
                        log("Eating Cookies");
                        timer.sleep(2000);
                        cookiesReady.flush();
                    }
                    id2[0] = (Integer)consult.read();
                    log("Welcoming elves");
                    if (cookiesReady.holding() > 0) {
                        log("Eating Cookies");
                        timer.sleep(2000);
                        cookiesReady.flush();
                        log("Welcoming elves");
                    }
                    for ( int i = 1; i <= 2; i++) { id2[i] = (Integer)consult.read(); }
                    for ( int i = 0; i <= 2; i++) {
                        consulting.get(id2[i]).write(1);
                    }
                    for ( int i = 0; i <= 2; i++) { negotiating.read(); }
                    timer.sleep ( consultationTime + rng.nextInt(consultationTime));
                    log("Showing elves out");
                    if (cookiesReady.holding() > 0) {
                        log("Eating Cookies");
                        timer.sleep(2000);
                        cookiesReady.flush();
                        log("Showing elves out");
                    }
                    for ( int i = 0; i <= 2; i++){
                        consulted.get(id2[i]).write(1);
                    }
                    consultationOver.write(1);
                    break;
            }
        }
    }
    public void log(String s){
        System.out.println(name + ": "  + s);
        printOut.write(new NorthPoleInterfaceMsg(s,name));
    }
}

