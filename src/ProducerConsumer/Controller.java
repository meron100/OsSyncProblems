package ProducerConsumer;

import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    @FXML
    ListView<String> buffer;
    @FXML
    Text pitem;
    @FXML
    Text citem;
    @FXML
    TextArea timec;
    @FXML
    TextArea timep;
    @FXML
    TextArea buffersize;

    Thread thread;

    Object mutex = new Object();

    public void start(){

        int bufferSize = Integer.valueOf(buffersize.getText());
        int timeToConsume = Integer.valueOf(timec.getText());
        int timeToProduce = Integer.valueOf(timep.getText());

        Buffer buffer = new Buffer(bufferSize);

        Consumer consumer = new Consumer(buffer, mutex, timeToConsume);
        Producer producer = new Producer(buffer, mutex, timeToProduce);

        consumer.addObserver(this);
        producer.addObserver(this);

        new Thread(consumer).start();
        new Thread(producer).start();
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(()->{

                if (o instanceof Producer) {
                    buffer.getItems().add(arg.toString());
                    pitem.setText(arg.toString());
                } else if (o instanceof Consumer) {
                    String item = "";
                    try{ item = buffer.getItems().remove(0);}
                    catch (Exception e){}
                    citem.setText(item);
                }

        });
    }
}
