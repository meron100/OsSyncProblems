package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Buffer<T> {
    List<Item<T>> buffer;
    AtomicInteger full;// INIT TO 0
    AtomicInteger empty;// init to n

    public Buffer(Integer size) {
        this.full = new AtomicInteger(0);
        this.empty = new AtomicInteger(size);
        buffer = new ArrayList<>();
    }

    public synchronized void add(Item<T> item){
        buffer.add(item);
    }

    public Item<T> remove(){
        return buffer.remove(0);
    }

    public void waitFull() {
        while (full.get() == 0) ;
        full.decrementAndGet();
    }

    public void waitEmpty() {
        boolean toWait = false;
        while (empty.get() == 0) ;
        empty.decrementAndGet();
    }


    public void signalFull(){
        full.incrementAndGet();
    }

    public void signalEmpty(){
        empty.incrementAndGet();
    }

}

