package ProducerConsumer;

import java.util.Observable;
import java.util.Random;

public class Producer extends Observable implements Runnable{
   Buffer buffer;
   Object mutex;
   int counter = 0;
   Random random ;
   int timeToProduce;

    public Producer(Buffer buffer, Object mutex, int timeToProduce) {
        this.buffer = buffer;
        this.mutex = mutex;
        random = new Random();
        this.timeToProduce = timeToProduce;
    }

    public String produce(){
        try{Thread.sleep(random.nextInt(timeToProduce * 1000) + 1000);}// random time for produce
        catch (Exception e){}
        System.out.println("Producer produce item");
        return "item " + counter++;
    }

    @Override
    public void run() {
        while (true){
            String item = produce(); // produce item;
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
