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
        if (size >= items.length) {
            resizeUp();
        }
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size >= items.length) {
            resizeUp();
        }

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
        if (size == 0) {
            return null;
        }

        if (items.length >= 16 && size < items.length / 4) {
            resizeDown();
        }

        int removeAtIndex = Math.floorMod(nextFirst + 1, items.length);
        T itemToRemove = items[removeAtIndex];
        items[removeAtIndex] = null;
        nextFirst = removeAtIndex;
        size--;
        return itemToRemove;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        if (items.length >= 16 && size < items.length / 4) {
            resizeDown();
        }

        int removeAtIndex = Math.floorMod(nextLast - 1, items.length);
        T itemToRemove = items[removeAtIndex];
        items[removeAtIndex] = null;
        nextLast = removeAtIndex;
        size--;
        return itemToRemove;
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
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resizeUp() {
        int oldLen = items.length;
        int newLen = oldLen * 2;
        T[] newItems = (T[]) new Object[newLen];

        int startNew = Math.floorMod(nextLast + size, newLen);
        int oldIndex = Math.floorMod(nextFirst + 1, newLen);

        for (int i = 0; i < size; i++) {
            newItems[Math.floorMod(startNew + i, newLen)] = items[oldIndex];
            oldIndex = Math.floorMod(oldIndex + 1, oldLen);
        }

        nextFirst = Math.floorMod(nextFirst + size, newLen);
        items = newItems; // must point to the resized array now

    }

    private void resizeDown() {
        int oldLen = items.length;
        int newLen = oldLen / 2;
        T[] newItems = (T[]) new Object[newLen];

        int startNew = Math.floorMod(nextFirst / 2 - 1, newLen);  // or other wraparound-friendly anchor
        int oldIndex = Math.floorMod(nextFirst + 1, oldLen);

        for (int i = 0; i < size; i++) {
            newItems[Math.floorMod(startNew + i, newLen)] = items[oldIndex];
            oldIndex = (oldIndex + 1) % oldLen;
        }

        nextFirst = Math.floorMod(startNew - 1, newLen);
        nextLast = Math.floorMod(startNew + size, newLen);
        items = newItems;

    }

    public void clear() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public boolean checkInvariants() {
        // Invariant 1: size is non-negative and does not exceed array length
        if (size < 0 || size > items.length) return false;

        // Invariant 2: nextFirst and nextLast are valid indices
        if (nextFirst < 0 || nextFirst >= items.length) return false;
        if (nextLast < 0 || nextLast >= items.length) return false;

        // Invariant 3: Number of non-null elements == size
        int nonNullCount = 0;
        for (T item : items) {
            if (item != null) nonNullCount++;
        }
        if (nonNullCount != size) return false;

        // Invariant 4: items are stored contiguously in circular fashion
        int index = Math.floorMod(nextFirst + 1, items.length);
        for (int i = 0; i < size; i++) {
            if (items[index] == null) return false;
            index = Math.floorMod(index + 1, items.length);
        }

        return true;
    }


}
