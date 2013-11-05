package NorthPole;

import Util.Data;
import Util.OutputService;

public class MrsClaus implements Runnable{

    public MrsClaus(SnackRoom sr){
        this.out = new OutputService("5565");
        this.sr = sr;
    }

    final private OutputService out;
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
        out.send(new Data(name, s));
    }
}
