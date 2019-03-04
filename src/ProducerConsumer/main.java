package ProducerConsumer;

public class main {
    public static void main(String[] args){
        Buffer buffer = new Buffer(10);
        Object mutex = new Object();

        Consumer consumer = new Consumer(buffer, mutex,3);
        Producer producer = new Producer(buffer, mutex, 2);

        new Thread(consumer).start();
        new Thread(producer).start();


    }
}
