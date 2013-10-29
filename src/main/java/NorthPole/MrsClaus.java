package NorthPole;


import org.nettosphere.samples.chat.AlternateInput;

public class MrsClaus implements Runnable{

    public MrsClaus(Santa santa, AlternateInput ai){
        this.santa = santa;
        this.ai = ai;
    }
    final private Santa santa;
    final private AlternateInput ai;


    @Override
    public void run() {

    }
}
