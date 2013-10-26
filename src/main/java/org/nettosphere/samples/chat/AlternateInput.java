package org.nettosphere.samples.chat;

import org.atmosphere.cpr.Broadcaster;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AlternateInput extends Thread{
    Broadcaster b;
    public AlternateInput(Broadcaster b) {
        this.b = b;
    }
    private final ObjectMapper mapper = new ObjectMapper();
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String a = "";
        System.out.println("Type quit to stop the server");
        while (!(a.equals("quit"))) {
            try {
                a = br.readLine();
                b.broadcast(mapper.writeValueAsString(mapper.readValue("{\"message\":\"" + a + "\",\"who\":\"Santa\"}",Data.class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
