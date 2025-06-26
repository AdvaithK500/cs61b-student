package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Advaith Kumar
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void put(K key, V value) {
        // if key doesnt exist, add a new key value pair i.e. a new node
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> bucket = buckets[index];
        if(!containsKey(key)) {
            bucket.add(new Node(key, value));
            size++;
            // check if loadFactor threshold is crossed
            if ((double) size / buckets.length > loadFactor) {
                resize(2 * buckets.length);
            }

        } else {
            for (Node node: bucket) {
                if (node.key.equals(key)) {
                    node.value = value;
                }
            }
        }

    }

    private void resize(int newCapacity) {
        Collection<Node>[] oldBuckets = this.buckets;
        // create a new double sized array
        this.buckets = (Collection<Node>[]) new Collection[newCapacity];
        // initialise this new buckets array
        initialiseBuckets();
        // set the size
        size = 0;
        for (Collection<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value); // this will insert + rehash correctly
            }
        }
    }

    @Override
    public V get(K key) {
        V value = null;
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> bucket = buckets[index];
        // else traverse through the right bucket and return value
        for (Node node: bucket) {
            if (node.key.equals(key)) {
                value = node.value;
                return value;
            }
        }
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        boolean found = false;
        for (K k: keySet()) {
            if (k.equals(key)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        // set each bucket in the buckets to null
        for (Collection<Node> bucket: buckets) {
            //collections has a clear() method, hence do the below
            bucket.clear();
        }
        //reset size
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        // create a set, loop through all nodes, add the key to this set, return
        Set<K> keys = new HashSet<>();

        for (Collection<Node> bucket: buckets) {
            for (Node node: bucket) {
                keys.add(node.key);
            }
        }

        return keys;
    }

    @Override
    public V remove(K key) {
        V value = null;
        // find the bucket where the node.key == key
        int index = Math.floorMod(key.hashCode(), buckets.length);
        // iterate through the bucket and when key is equal,
        //store its value, remove the node
        Collection<Node> bucket = buckets[index];
        // to avoid concurrent modification, create custom iterator
        Iterator<Node> iter = bucket.iterator();

        while(iter.hasNext()) {
            Node node = iter.next();
            if (node.key.equals(key)) {
                value = node.value;
                iter.remove(); // this is the collections iter remove method, no recursion!
                size--;
                return value;
            }
        }

        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    private class KeyIterator implements Iterator<K> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public K next() {
            return null;
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private final int initialCapacity;
    private final double loadFactor;
    private int size = 0;
    /** Constructors */

    private void initialiseBuckets() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }
    public MyHashMap() {
        // Init an empty hashMap with specified default values
        this.initialCapacity = 16;
        this.loadFactor = 0.75;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        initialiseBuckets();
    }

    public MyHashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = 0.75;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        initialiseBuckets();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        initialiseBuckets();
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();

    }


}
