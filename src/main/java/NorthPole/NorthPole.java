package NorthPole;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.nettosphere.samples.chat.AlternateInput;
import org.nettosphere.samples.chat.NettoServer;

public class NorthPole
{
     public static void main (String[] args)
     {

        NettoServer ns = new NettoServer();
        Broadcaster b = BroadcasterFactory.getDefault().lookup("/");
        AlternateInput ai = new AlternateInput(b);
        final SnackRoom sr = new SnackRoom();
	    final Santa santa = new Santa(ai, sr);
        final MrsClaus mrsClaus = new MrsClaus(ai, sr);
        final WaitingRoom waitingRoom = new WaitingRoom(santa, 3, "Elf", ai);
	    final WaitingRoom stable = new WaitingRoom(santa, 9, "Reindeer", ai);
        final String[] reindeerNames = { "Dasher","Dancer", "Prancer", "Vixen",
             "Comet","Cupid","Donder","Blitzen","Ruldolph" };
        final String[] elfNames = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        santa.findWaitingRoom(waitingRoom);
	    santa.findStable(stable);
	    (new Thread(santa)).start();
        for (String name : reindeerNames)
        {
            (new Thread(new SantasFriend(name, stable, "Reindeer", ai))).start();
        }
        for (String name : elfNames)
        {
            (new Thread(new SantasFriend("Elf" + name, waitingRoom, "Elf", ai))).start();
        }
         (new Thread(mrsClaus)).start();
     }
}