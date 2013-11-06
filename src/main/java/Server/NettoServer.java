package Server;

import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Nettosphere;

public class NettoServer {
    public NettoServer(){
        Config.Builder b = new Config.Builder();
        b.resource(NorthPole.class)
                .resource("./src/main/resources")
                .port(8080).host("127.0.0.1").build();
        Nettosphere s = new Nettosphere.Builder().config(b.build()).build();
        s.start();
    }
}
