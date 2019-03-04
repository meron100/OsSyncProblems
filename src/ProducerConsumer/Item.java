package ProducerConsumer;

public class Item<T> {
    T item;
    int placeInBuffer;

    public Item(T itemNum, int placeInTheBuffer) {
        item = itemNum;
        placeInBuffer = placeInTheBuffer;
    }

    public void setPlaceInBuffer(int placeInBuffer) {
        this.placeInBuffer = placeInBuffer;
    }

    public T getItem() {
        return item;
    }

    @Override
    public String toString() {
        return placeInBuffer + ") " + item.toString();
    }
}
