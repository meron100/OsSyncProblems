package DiningPhilosopher;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements Observer {

    @FXML
    Circle p0;
    @FXML
    Circle p1;
    @FXML
    Circle p2;
    @FXML
    Circle p3;
    @FXML
    Circle p4;

    @FXML
    Circle table;

    @FXML
    Line p0p1Chopstick;
    @FXML
    Line p1p2Chopstick;
    @FXML
    Line p2p3Chopstick;
    @FXML
    Line p3p4Chopstick;
    @FXML
    Line p4p0Chopstick;


    Lock lock = new ReentrantLock(true);

    @Override
    public void update(Observable o, Object arg) {
       lock.lock();
            int i = ((Philosopher) o).i;
            Philosopher.State state = ((Philosopher) o).getState();
            Circle circleToPaint = null;
            Line chopStickToPaint1 = null;
            Line chopStickToPaint2 = null;
            switch (i) {
                case 0:
                    circleToPaint = p0;
                    chopStickToPaint1 = p0p1Chopstick;
                    chopStickToPaint2 = p4p0Chopstick;
                    break;
                case 1:
                    circleToPaint = p1;
                    chopStickToPaint1 = p1p2Chopstick;
                    chopStickToPaint2 = p0p1Chopstick;
                    break;
                case 2:
                    circleToPaint = p2;
                    chopStickToPaint1 = p2p3Chopstick;
                    chopStickToPaint2 = p1p2Chopstick;
                    break;
                case 3:
                    circleToPaint = p3;
                    chopStickToPaint1 = p3p4Chopstick;
                    chopStickToPaint2 = p2p3Chopstick;
                    break;
                case 4:
                    circleToPaint = p4;
                    chopStickToPaint1 = p4p0Chopstick;
                    chopStickToPaint2 = p3p4Chopstick;
                    break;
            }

            switch (state) {
                case EATING:
                    Circle finalCircleToPaint = circleToPaint;
                    Line finalChopStickToPaint = chopStickToPaint1;
                    Line finalChopStickToPaint1 = chopStickToPaint2;
                    Platform.runLater(()-> {finalCircleToPaint.setFill(Color.GREEN);
                                            finalChopStickToPaint.setStroke(Color.GREEN);
                                            finalChopStickToPaint1.setStroke(Color.GREEN);});

                    break;
                case THINKING:
                    Circle finalCircleToPaint2 = circleToPaint;
                    Line finalChopStickToPaint2 = chopStickToPaint1;
                    Line finalChopStickToPaint3 = chopStickToPaint2;

                    Platform.runLater(()-> {finalCircleToPaint2.setFill(Color.BLUE);
                                            finalChopStickToPaint2.setStroke(Color.BLUE);
                                            finalChopStickToPaint3.setStroke(Color.BLUE);});
                    break;
                case HUNGRY:
                    Circle finalCircleToPaint3 = circleToPaint;
                    Line finalChopStickToPaint4 = chopStickToPaint1;
                    Line finalChopStickToPaint5 = chopStickToPaint2;
                    Platform.runLater(()-> {finalCircleToPaint3.setFill(Color.RED);
                                            finalChopStickToPaint4.setStroke(Color.RED);
                                            finalChopStickToPaint5.setStroke(Color.RED);});
                    break;
            }
        lock.unlock();
    }

    public void start(){
        Philosopher[] philosophers = new Philosopher[5];
        Object o =new Object();

        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(5 ,philosophers ,o ,i);
        }

        Arrays.asList(philosophers).forEach(philosopher -> philosopher.addObserver(this));

        Arrays.asList(philosophers).forEach(philosopher -> new Thread(() -> philosopher.run()).start());
    }
}
