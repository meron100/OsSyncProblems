package ProducerConsumer;

import java.util.Observable;
import java.util.Random;

public class Producer extends Observable implements Runnable{
   Buffer<String> buffer;
   Object mutex;
   int counter = 0;
   Random random ;
   int timeToProduce;
   private final String item = "item";

    public Producer(Buffer<String> buffer, Object mutex, int timeToProduce) {
        this.buffer = buffer;
        this.mutex = mutex;
        random = new Random();
        this.timeToProduce = timeToProduce;
    }

    public Item<String> produce(){
        try{Thread.sleep(random.nextInt(timeToProduce * 1000) + 1000);}// random time for produce
        catch (Exception e){}
        System.out.println("Producer produce item");
        return new Item<String>(item + counter++, buffer.full.get());
    }

    @Override
    public void run() {
        while (true){
            Item<String> item = produce(); // produce item;
            buffer.waitEmpty();
            synchronized (mutex){
                buffer.add(item);
            }
            setChanged(); //for gui
            notifyObservers(item); //for gui
            buffer.signalFull();
        }

    }
}
