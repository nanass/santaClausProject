package JCSPNorthPole;

import org.jcsp.lang.*;

public class WaitingRoom implements CSProcess {

    Bucket group;
    AltingChannelInput needToConsult;
    ChannelOutput joinGroup;
    AltingChannelInput openForBusiness;
    AltingChannelInput consultationOver;

    public WaitingRoom( Bucket group, AltingChannelInput needToConsult, ChannelOutput joinGroup,
                        AltingChannelInput openForBusiness, AltingChannelInput consultationOver){
        this.group = group;
        this.needToConsult = needToConsult;
        this.joinGroup = joinGroup;
        this.openForBusiness = openForBusiness;
        this.consultationOver = consultationOver;
    }

    public void run(){
        Skip flush = new Skip();
        Guard[] guards = { needToConsult, consultationOver, flush };
        Alternative vAlt = new Alternative(guards);
        int index = -1;
        int counter = 0;
        boolean[] preCon = { true, true, false };
        openForBusiness.read();
        while (true){
            index = vAlt.select(preCon);
            switch (index) {
                case 0: //Need
                    needToConsult.read();
                    if (counter < 3){
                        joinGroup.write(1);
                        counter = counter + 1;
                    }
                    else {
                        joinGroup.write(0);
                    }
                    break;
                case 1: //Over
                    consultationOver.read();
                    break;
                case 2: //Flush
                    group.flush();
                    counter = 0;
                    break;
            }
            preCon[2] = (group.holding() == 3);
        }
    }
}