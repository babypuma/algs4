import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int maxSize;
    private int realSize;
    private int curr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        maxSize = 1;
        realSize = 0;
        curr = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return realSize == 0;
    }

    // return the number of items on the queue
    public int size() {
        return realSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        } 
        
        if (curr < maxSize) {
            array[curr++] = item;
        }
        else if ((double) realSize / maxSize < 0.75) {
            int pos;
            do {
                pos = (int) (Math.random() * maxSize);
            } while (array[pos] != null);
            array[pos] = item;
        } 
        else {
            resize(maxSize * 2);
            array[curr++] = item;
        }

        realSize++;
    }

    // delete and return a random item
    public Item dequeue() {
        if (realSize == 0) {
            throw new NoSuchElementException();
        }

        int pos;
        do {
            pos = (int) (Math.random() * curr);
        } while (array[pos] == null);

        Item item = array[pos];
        array[pos] = null;
        realSize--;

        if (maxSize > 8 && (double) realSize / maxSize <= 0.25) {
            resize(maxSize / 2);
        }

        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (realSize == 0) {
            throw new NoSuchElementException();
        }

        int pos;
        do {
            pos = (int) (Math.random() * curr);
        } while (array[pos] == null);

        return array[pos];
    }

    private class RandomizedQueueInterator implements Iterator<Item> {
        private int cursor = 0;

        public boolean hasNext() {
            return cursor < curr;
        }

        public void remove() {
           throw new UnsupportedOperationException();
        }

        public Item next() {
            if (cursor >= curr) {
                throw new NoSuchElementException();
            }

            while (array[cursor] == null) {
                cursor++;
            }

            return array[cursor++];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueInterator();
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        int j = 0;
        for (int i = 0; i < realSize; i++) {
            while (array[j] == null) {
                j++;
            }
            temp[i] = array[j];
            j++;
        }
        array = temp;
        maxSize = max;
        curr = realSize;
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> que = new RandomizedQueue<String>();
        System.out.println(que.isEmpty());
        System.out.println(que.size());
        for (int i = 0; i < 64; i++) {
            que.enqueue(Integer.toString(i));
        } 

        for (int i = 0; i < 64; i++) {
            System.out.print(que.sample() + " ");
        }
        System.out.println();

        while (!que.isEmpty()) {
            System.out.print(que.dequeue() + " ");
        }

        System.out.println();
    }
}
