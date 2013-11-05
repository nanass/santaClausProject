package WishList;

import Util.Command;
import Util.Data;
import Util.OutputService;

import java.util.concurrent.CopyOnWriteArrayList;

public final class WishList implements Command {

    private static CopyOnWriteArrayList<Data> wishList = new CopyOnWriteArrayList<Data>();
    OutputService out;
    public WishList(OutputService out){
        this.out = out;
    }

    public void deliverGifts(){
        String output = "";
        System.out.println("Gifts for: ");
        for(Data d : wishList){
            System.out.println(d.getAuthor() + " : " + d.getMessage());
            output += d.getAuthor() + " , " + d.getMessage() +" ";
        }
        wishList.clear();
        out.send(new Data("all", output));
    }

    public void addToWishList(Data msg){
        if(msg.getType().equals("wishlist")){
            wishList.add(msg);
            System.out.println(msg.getMessage());
        }
    }

    @Override
    public void execute() {}

    @Override
    public void execute(Data data) {
        if(data.getMessage().equals("Deliver"))
            deliverGifts();
        else
            addToWishList(data);
    }
}