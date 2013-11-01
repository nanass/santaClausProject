package JCSPNorthPole;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.One2OneChannel;

import java.util.ArrayList;
import java.util.List;

public class  Utils {

    public static CSProcess[] concat(CSProcess[] A, CSProcess[] B) {
        int aLen = A.length;
        int bLen = B.length;
        CSProcess[] C = new CSProcess[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    public static List<ChannelOutput> getOutList(One2OneChannel[] chan){
        List<ChannelOutput> outs = new ArrayList<ChannelOutput>();
        for(int i = 0; i < chan.length; i++){
            outs.add(chan[i].out());
        }
        return outs;
    }
}
