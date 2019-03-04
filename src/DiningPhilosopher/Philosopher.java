package DiningPhilosopher;

import java.util.Observable;
import java.util.Random;

public class Philosopher extends Observable implements Runnable  {

    public enum State {THINKING, HUNGRY, EATING}

    int numOfPhilosophers;
    Philosopher[] philosophers = new Philosopher[numOfPhilosophers];

    Object o; // sheared for synchronized

    int i;

    private State state;

    Random random;

    public Philosopher(int numOfPhilosophers, Philosopher[] philosophers, Object o, int i) {
        this.numOfPhilosophers = numOfPhilosophers;
        this.philosophers = philosophers;
        this.o = o;
        this.i = i;
        this.state = State.THINKING;
        random = new Random();
    }

    @Override
    public void run() {
        while (true){
            System.out.println("philosopher " + i +  "is" + State.THINKING);
            try{Thread.sleep(random.nextInt(9 * 1000) + 1000);}
            catch (Exception e){}

            pickUp(i);
            putDown(i);
            setState(State.THINKING);
        }
    }

    public State getState(){
            return state;
    }

    public void setState(State state){
            this.state = state;
            setChanged();
            notifyObservers();
            try {Thread.sleep(300); }// for gui
            catch (Exception e){}
    }

    void pickUp(int i) {
            setState(State.HUNGRY);
            try{Thread.sleep(500);}
            catch (Exception e){}// for gui
            System.out.println("philosopher " + i + " is " + State.HUNGRY);
            do {
                    test(i);
                if (philosophers[i].getState().compareTo(State.EATING) != 0) {

                    try { o.wait(); }
                    catch (Exception e) {}
                }
            } while (philosophers[i].getState().compareTo(State.EATING) != 0);

    }

    void putDown(int i) {

        synchronized (o) {
            setState(State.THINKING);
            test((i + (numOfPhilosophers - 1)) % numOfPhilosophers);
            test((i + 1) % numOfPhilosophers);
            System.out.println("philosopher " + i + " is finish with is chopstick");
            o.notifyAll();
        }

    }

    void test(int i) {
        synchronized (o) {
            if (philosophers[(i + (numOfPhilosophers - 1)) % numOfPhilosophers].getState().compareTo(State.EATING) != 0 &&
                    philosophers[i].getState().compareTo(State.HUNGRY) == 0 &&
                    philosophers[(i + 1) % numOfPhilosophers].getState().compareTo(State.EATING) != 0) {
                setState(State.EATING);
            }
        }
        if(getState().compareTo(State.EATING) == 0) {
            System.out.println("philosopher " + i + " is eating");
            try {Thread.sleep(random.nextInt(4 * 1000) + 500); }
            catch (Exception e) { }
            System.out.println("philosopher " + i + " finish to eat");
        }
    }

}
