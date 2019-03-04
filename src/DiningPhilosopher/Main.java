package DiningPhilosopher;

import java.util.Arrays;

public class Main {

    public static void main(String[] args){

        Philosopher[] philosophers = new Philosopher[5];
        Object o =new Object();

        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(5 ,philosophers ,o ,i);
        }

        Arrays.asList(philosophers).forEach(philosopher -> new Thread(() -> philosopher.run()).start());

    }

}
