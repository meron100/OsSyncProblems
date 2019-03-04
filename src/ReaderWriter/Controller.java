package ReaderWriter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller implements Observer {

    @FXML
    ListView readerList;
    @FXML
    ListView inFileList;
    @FXML
    ListView WriterList;
    @FXML
    TextArea timeRead;
    @FXML
    TextArea readInterval;
    @FXML
    TextArea writerInterval;
    @FXML
    TextArea writeTime;

    volatile AtomicInteger wrt = new AtomicInteger(1);
    volatile AtomicInteger mutex = new AtomicInteger(1);
    volatile AtomicInteger readCount = new AtomicInteger(0);

    Lock lock = new ReentrantLock(true);

    public void start(){

        int readerInterval = Integer.valueOf(readInterval.getText());
        int writerInterval = Integer.valueOf(this.writerInterval.getText());
        int timeRead = Integer.valueOf(this.timeRead.getText());
        int timeWrite = Integer.valueOf(writeTime.getText());


        Reader reader = new Reader(wrt, readCount, mutex, 0, timeRead, readerInterval);
        Writer writer = new Writer(wrt, 0, timeWrite, writerInterval);

        reader.addObserver(this);
        writer.addObserver(this);

        reader.observer = this;
        writer.observer = this;

        new Thread(()->reader.runFewReaders()).start();
        new Thread(()->writer.runFewWriter()).start();

    }


    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(()->{
            lock.lock();
                if (o instanceof Reader) {
                    int id = ((Reader) o).id;
                    if (arg.equals(Constants.RUN)) {
                        readerList.getItems().add("reader " + id);
                    }
                    if (arg.equals(Constants.READ)) {
                        readerList.getItems().remove("reader " + id);
                        inFileList.getItems().add("reader " + id);
                    }
                    if (arg.equals(Constants.FINISH)) {
                        inFileList.getItems().remove("reader " + id);
                    }
                }
                if (o instanceof Writer) {
                    int id = ((Writer) o).id;
                    if (arg.equals(Constants.RUN)) {
                        WriterList.getItems().add("writer " + id);
                    }
                    if (arg.equals(Constants.WRITE)) {
                        WriterList.getItems().remove("writer " + id);
                        inFileList.getItems().add("writer " + id);
                    }
                    if (arg.equals(Constants.FINISH)) {
                        inFileList.getItems().remove("writer " + id);
                    }
                }
                lock.unlock();
        });
    }
}
