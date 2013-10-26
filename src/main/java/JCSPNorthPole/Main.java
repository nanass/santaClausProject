package JCSPNorthPole;

import org.jcsp.lang.*;
import org.nettosphere.samples.chat.AlternateInput;
import org.nettosphere.samples.chat.NettoServer;

import java.util.List;

public class Main {
    enum name{ Dasher, Dancer, Prancer, Vixen, Comet, Cupid, Donder, Blitzen, Ruldolph }
    public static void main(String[] args)
    {
        NettoServer ns = new NettoServer();
        AltingBarrier [] stable = AltingBarrier.create(10);
        AltingBarrier [] sleigh = AltingBarrier.create(10);
        Bucket elfGroup = new Bucket();
        One2OneChannel openForBusiness = Channel.one2one();
        One2OneChannel consultationOver = Channel.one2one();
        Any2OneChannel harness = Channel.any2one();
        One2AnyChannel harnessed = Channel.one2any();
        One2AnyChannel returned = Channel.one2any();
        One2OneChannel[] unharness = Channel.one2oneArray(9);
        List<ChannelOutput> unharnessList = Utils.getOutList(unharness);
        Any2OneChannel needToConsult = Channel.any2one();
        One2AnyChannel joinGroup = Channel.one2any();
        Any2OneChannel consult = Channel.any2one();
        Any2OneChannel negotiating = Channel.any2one();
        One2OneChannel[] consulting = Channel.one2oneArray(10);
        One2OneChannel[] consulted = Channel.one2oneArray(10);
        List<ChannelOutput> consultingList = Utils.getOutList(consulting);
        List<ChannelOutput> consultedList = Utils.getOutList(consulted);
        Any2OneChannel print = Channel.any2one();

        CSProcess[] reindeerProc = new CSProcess[9];
        for (name r : name.values()){
            reindeerProc[r.ordinal()] = new Reindeer(r.toString(), stable[r.ordinal()], sleigh[r.ordinal()], harness.out(), harnessed.in(), returned.in(),  unharness[r.ordinal()].in(), print.out());
        }

        CSProcess[] elfProc = new CSProcess[10];
        for (int i = 1; i <= 10; i++){
            elfProc[i-1] = new Elf("Elf" + String.valueOf(i), i-1, elfGroup, needToConsult.out(), joinGroup.in(), consult.out(),  consulting[i-1].in(),
                                 negotiating.out(), consulted[i-1].in(), print.out());
        }

        CSProcess[] procs = {new Santa(openForBusiness.out(), consultationOver.out(),harness.in(), harnessed.out(), returned.out(),
                                        unharnessList,stable[9],sleigh[9],consult.in(),consultingList,negotiating.in(),consultedList, print.out()),

                             new WaitingRoom(elfGroup,needToConsult.in(),joinGroup.out(),
                                        openForBusiness.in(),consultationOver.in()),
                             new AlternateInput(print.in())};

        procs = Utils.concat(procs, reindeerProc);
        procs = Utils.concat(procs, elfProc);
        new Parallel(procs).run();
    }
}
