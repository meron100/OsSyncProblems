package DiningPhilosopher;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

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


    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {

            int i = ((Philosopher) o).i;
            Philosopher.State state = ((Philosopher) o).getState();
            Circle circleToPaint = null;
            switch (i) {
                case 0:
                    circleToPaint = p0;
                    break;
                case 1:
                    circleToPaint = p1;
                    break;
                case 2:
                    circleToPaint = p2;
                    break;
                case 3:
                    circleToPaint = p3;
                    break;
                case 4:
                    circleToPaint = p4;
                    break;
            }

            switch (state) {
                case EATING:
                    Circle finalCircleToPaint = circleToPaint;
                    Platform.runLater(()-> finalCircleToPaint.setFill(Color.GREEN));
                    break;
                case THINKING:
                    Circle finalCircleToPaint2 = circleToPaint;
                    Platform.runLater(()-> finalCircleToPaint2.setFill(Color.BLUE));
                    break;
                case HUNGRY:
                    Circle finalCircleToPaint3 = circleToPaint;
                    Platform.runLater(()-> finalCircleToPaint3.setFill(Color.RED));
                    break;
            }
        }
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
