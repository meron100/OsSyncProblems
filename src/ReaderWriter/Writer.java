package ReaderWriter;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Writer extends Observable implements Runnable{
    AtomicInteger wrt;// kind of mutex
    Random random;// for gui
    int id;// for gui
    Observer observer;
    int timeToWrite;
    int timeIntrval;

    public Writer(AtomicInteger wrt, int id, int timeToWrite, int timeIntrval){
        this.wrt = wrt;
        this.id = id;
        random = new Random();
        this.timeToWrite = timeToWrite;
        this.timeIntrval = timeIntrval;

    }

    public void waitWrt(){
        synchronized (Writer.class) {
            while (wrt.get() == 0) ;
            wrt.decrementAndGet();
        }

    }

    public void signalWrt(){
        wrt.incrementAndGet();
    }

    public void runFewWriter(){
        AtomicInteger idGenretor = new AtomicInteger(0);
        do{
            try{Thread.sleep(random.nextInt( timeIntrval * 1000) + 1000);}
            catch (Exception e){}
            Writer writer = new Writer(wrt, idGenretor.getAndIncrement(), timeToWrite, timeIntrval);
            writer.addObserver(observer);
            new Thread(writer).start();
        }while (true);
    }

    public void write(){
        //for gui
        setChanged();
        notifyObservers("write");

        System.out.println("writer " + id + " is writing");
        try {Thread.sleep(random.nextInt(timeToWrite * 1000) + 1000);}
        catch (Exception e){}
        System.out.println("writer " + id + " finish writing");
    }


    @Override
    public void run() {
        //for gui
        setChanged();
        notifyObservers("run");

        waitWrt();

        write();

        signalWrt();

        //for gui
        setChanged();
        notifyObservers("finish");
    }
}
