package ProducerConsumer;

import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Consumer extends Observable implements Runnable {
    Buffer buffer;
    Object mutex;
    Random random;
    int timeToConsume;

    public Consumer(Buffer buffer, Object mutex, int timeToConsume) {
        this.buffer = buffer;
        this.mutex = mutex;
        this.timeToConsume = timeToConsume;
        random = new Random();
    }

    public void consume(String item){
        try{Thread.sleep(random.nextInt(timeToConsume * 1000) + 1000);}// random time for consume
        catch (Exception e){}
        System.out.println("item : " + item + " consumed");
    }

    @Override
    public void run() {

        while (true){
            String item;
            buffer.waitFull();
            synchronized (mutex){
                item = buffer.remove();
            }
            setChanged(); //for gui
            notifyObservers(); //for gui
            buffer.signalEmpty();
            consume(item);
        }


    }
}
