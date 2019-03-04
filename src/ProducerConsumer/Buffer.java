package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Buffer {
    List<String> buffer;
    AtomicInteger full;// INIT TO 0
    AtomicInteger empty;// init to n
    Random random = new Random();

    public Buffer(Integer size) {
        this.full = new AtomicInteger(0);
        this.empty = new AtomicInteger(size);
        buffer = new ArrayList<>();
    }

    public synchronized void add(String item){
        buffer.add(item);
    }

    public String remove(){
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

