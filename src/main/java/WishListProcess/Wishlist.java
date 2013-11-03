package WishListProcess;

import Util.Data;
import org.jcsp.lang.*;


import java.util.ArrayList;

public class Wishlist implements CSProcess {

    AltingChannelInput wishIn;
    AltingChannelInput delivery;
    ChannelOutput out;
    Alternative alt;
    ArrayList<Data> wishList = new ArrayList<Data>();

    public Wishlist(AltingChannelInput wishIn, AltingChannelInput delivery, ChannelOutput out){
        this.wishIn = wishIn;
        this.delivery = delivery;
        Guard[] guard = {delivery, wishIn};
        alt = new Alternative(guard);
        this.out = out;
    }

    public void run(){
        while(true){
            switch (alt.priSelect()){
                case 0 :
                    delivery.read();
                    String output = "";
                    System.out.println("Gifts for: ");
                    for(Data d : wishList){
                        System.out.println(d.getAuthor() + " : " + d.getMessage());
                        output += d.getAuthor() + " , " + d.getMessage() +" ";
                    }
                    wishList.clear();
                    out.write(new Data("all", output));
                    break;
                case 1 :
                    Data msg = (Data)wishIn.read();
                    if(msg.getType().equals("wishlist")){
                        wishList.add(msg);
                        System.out.println(msg.getMessage());
                    }
                    break;

            }
        }
    }

}
