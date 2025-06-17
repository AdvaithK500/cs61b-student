import java.util.ArrayList;
//import java.util.Currency;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private class Node {
        Node prev;
        T item;
        Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }

    }

    private int size;
    private Node sentinel;
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

    }
    /*
    * Invariants
    * sentinel.prev points to the last real node ( if it exists, else itself vacuously)
    * sentinel.next points to the first real node ( if it exists, else itself vacuously)
    * size is the number of real nodes
    *  */
    @Override
    public void addFirst(T x) {
        Node oldFirst = sentinel.next; // point to the first real node
        Node newFirstNode = new Node(sentinel, x, oldFirst);
        sentinel.next = newFirstNode;
        oldFirst.prev = newFirstNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node oldLast = sentinel.prev; // store pointer to the last real node
        Node newLastNode = new Node(oldLast, x, sentinel);
        oldLast.next = newLastNode;
        sentinel.prev = newLastNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        // get a pointer iterator through the LL
        Node currNode = sentinel.next;

        while (currNode != sentinel) {
            // keep on adding to the array list the item
            returnList.add(currNode.item);
            currNode = currNode.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null; // as the only element is sentinel node in the list
        }
        Node itemNode = sentinel.next;
        sentinel.next = itemNode.next;
        sentinel.next.prev = sentinel;
        size--;
        return itemNode.item;
    }

    @Override
    public T removeLast() {
        if (sentinel.next == sentinel) {
            return null; // as the only element is sentinel node in the list
        }

        Node itemNode = sentinel.prev;
        itemNode.prev.next = sentinel;
        sentinel.prev = itemNode.prev;
        size--;

        return itemNode.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node currPointer = sentinel.next;
        int currentCount = 0;
        T item = null;
        while (currPointer != sentinel) {
            if (currentCount == index) {
                item = currPointer.item;
            }
            currentCount++;
            currPointer = currPointer.next;
        }
        return item;
    }

    @Override
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node start, int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return start.item;
        }


        return getRecursiveHelper(start.next, index - 1);
    }
}
