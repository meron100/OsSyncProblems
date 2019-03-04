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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements Observer {

    @FXML
    ListView<Item<String>> buffer;

    // ptoducer item in gui
    @FXML
    Text pitem;

    // consumer item in gui
    @FXML
    Text citem;

    @FXML
    TextArea timec;

    @FXML
    TextArea timep;

    @FXML
    TextArea buffersize;


    Object mutex = new Object();

    Lock lock = new ReentrantLock(true);

    public void start(){

        int bufferSize = Integer.valueOf(buffersize.getText());
        int timeToConsume = Integer.valueOf(timec.getText());
        int timeToProduce = Integer.valueOf(timep.getText());

        Buffer<String> buffer = new Buffer<>(bufferSize);

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
            lock.lock();
            Item<String> item = ((Item<String>)arg);
                if (o instanceof Producer) {
                    item.setPlaceInBuffer(buffer.getItems().size());
                    buffer.getItems().add(item);
                    pitem.setText(item.getItem().toString());
                    updateItemsPlaceInBuffer();
                } else if (o instanceof Consumer) {
                    try{ item = buffer.getItems().remove(0);}
                    catch (Exception e){}
                    citem.setText(item.getItem().toString());
                    updateItemsPlaceInBuffer();
                }

                lock.unlock();
        });
    }

    public void updateItemsPlaceInBuffer(){
        buffer.getItems().stream()
                .forEach(stringItem -> stringItem.setPlaceInBuffer(buffer.getItems().indexOf(stringItem) + 1));
    }


}
