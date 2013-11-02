package NorthPole;

import org.nettosphere.samples.chat.AlternateInput;

public class MrsClaus implements Runnable{

    public MrsClaus(AlternateInput ai, SnackRoom sr){
        this.ai = ai;
        this.sr = sr;
    }

    final private AlternateInput ai;
    final private SnackRoom sr;
    final private String name = "MrsClaus";

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(5000);
                log("Going to Santa's");
                Thread.sleep(10000);
                log("Waiting with cookies");
                sr.waitWithCookies();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Done with Cookies");
        }
    }
    public void log(String s){
        System.out.println(name + ": "  + s);
        ai.send(s,name);
    }
}
