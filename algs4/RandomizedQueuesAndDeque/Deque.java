import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> { 
    private Node head;
    private Node tail;
    private int length;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    // construct an empty deque 
    public Deque() { 
        head = null;
        tail = null;
        length = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        } 

        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.prev = null;
        head.next = oldHead;

        if (oldHead == null) {
            tail = head;
        }
        else {
            oldHead.prev = head;
        }

        length++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        } 

        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        tail.prev = oldTail;
        tail.next = null;

        if (oldTail == null) {
            head = tail;
        }
        else {
            oldTail.next = tail;
        }

        length++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        
        Node oldHead = head; 
        head = head.next;
        if (head == null) {
            tail = null;
        }
        else {
            head.prev = null;
        }

        length--;
        
        return oldHead.item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (length == 0) {
            throw new NoSuchElementException();
        }

        Node oldTail = tail;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        }
        else {
            tail.next = null;
        }
        
        length--;

        return oldTail.item;
    }

    private class DequeInterator implements Iterator<Item> {
        private Node curr = head;

        public boolean hasNext() {
            return curr != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (curr == null) {
                throw new NoSuchElementException();
            }

            Item item = curr.item;
            curr = curr.next;

            return item;
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeInterator();
    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> deq = new Deque<String>();
        System.out.println("Empty qeque: " + deq.isEmpty());
        System.out.println("Deque's size: " + deq.size());
        for (int i = 0; i < 10; i++) {
            deq.addFirst(Integer.toString(i));
        }

        System.out.println("Deque's size: " + deq.size());
        for (String s: deq) {
            System.out.print(s + " ");
        } 
        System.out.println();

        for (int i = 0; i < 10; i++) {
            deq.addLast(Integer.toString(i + 10)); 
        }

        System.out.println("Deque's size: " + deq.size());
        for (String s: deq) {
            System.out.print(s + " ");
        }
        System.out.println();

        while (deq.size() > 10) {
            if (deq.size() % 2 == 0) {
                System.out.println(deq.removeFirst());
            }
            else {
                System.out.println(deq.removeLast());
            }
        }
        System.out.println("Deque's size: " + deq.size());

        while (!deq.isEmpty()) {
            if (deq.size() % 2 == 0) {
                System.out.println(deq.removeFirst());
            }
            else {
                System.out.println(deq.removeLast());
            }
        }
        System.out.println("Deque's size: " + deq.size());
    }
}
