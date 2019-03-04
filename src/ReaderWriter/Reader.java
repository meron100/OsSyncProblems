package ReaderWriter;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Reader  extends Observable implements Runnable{
    AtomicInteger wrt;// kind of mutex
    AtomicInteger mutex; // defend on the read count variable;
    AtomicInteger readcount;
    Random random;// for gui
    int id;// for gui
    public Observer observer;
    int timeToRead;
    int timeIntrval;

    public Reader(AtomicInteger wrt, AtomicInteger readcount, AtomicInteger mutex, int id, int timeToRead, int timeIntrval){
        this.wrt = wrt;
        random = new Random();
        this.readcount = readcount;
        this.mutex = mutex;
        this.id = id;
        this.timeToRead = timeToRead;
        this.timeIntrval = timeIntrval;
    }

    public void runFewReaders(){
        AtomicInteger idGenertor = new AtomicInteger(0);
        setChanged();
        while(true){
            try {Thread.sleep(random.nextInt(timeIntrval * 1000) + 1000);}
            catch (Exception e){}
            Reader r = new Reader(wrt, readcount, mutex, idGenertor.getAndIncrement(), timeToRead, timeIntrval);
            r.addObserver(observer);
            new Thread(r).start();
        }
    }
    public void read(){
        System.out.println("reader " + id + " is reading");

        //for gui
        setChanged();
        notifyObservers("read");
        try {Thread.sleep(random.nextInt(timeToRead * 1000) + 1000);}
        catch (Exception e){}
        System.out.println("reader " + id + " finish reading");
    }

    public void waitMutex(){
        while (mutex.get() == 0);
        mutex.decrementAndGet();
    }

    public void signalMutex(){
        mutex.incrementAndGet();
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


    @Override
    public void run() {
        //for gui
        setChanged();
        notifyObservers("run");

            waitMutex();
            readcount.incrementAndGet();
            if (readcount.get() == 1){
                waitWrt();
            }
            signalMutex();

            read();

            waitMutex();
            readcount.decrementAndGet();
            if(readcount.get() == 0)
                signalWrt();
            signalMutex();

            //for gui
            setChanged();
            notifyObservers("finish");
    }
}
