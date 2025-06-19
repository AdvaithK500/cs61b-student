package deque;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/*
* Backing array implementation of Dequeue has the following
* Invariants
* size = number of valid elements ( initially size = 0 )
* nextFirst points to the left of the 1st valid element circularly
* nextLast points to the right of the last valid element circularly
* items.length are powers of 2 for ease of resizing
*
* */

public class ArrayDeque61B<T> implements Deque61B<T> {
    int size;
    T[] items;
    int nextFirst;
    int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        // I arbitrarily choose nextFirst to point to index 4 as in the slides
        nextFirst = 4;
        nextLast = 5;
    }
    @Override
    public void addFirst(T x) {
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        items[nextLast] = x;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = Math.floorMod(nextFirst + 1, items.length);

        for (int i = 0; i < size; i++) {
            returnList.add(items[index]);
            index = Math.floorMod(index + 1, items.length);
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        // index must be modified to behave like 0 indexed using nextFirst or nextLast
        if (index >= size || index < 0) {
            return null;
        }

        index = (index + nextFirst + 1) % items.length;
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
