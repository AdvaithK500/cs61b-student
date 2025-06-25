import java.util.*;

public class BSTMap <K extends Comparable<K>, V> implements Map61B<K, V>{

    // need an internal node class
    private int size;
    private BSTMapNode root;

    private class BSTMapNode {
        K key;
        V value;
        BSTMapNode left;
        BSTMapNode right;

        public BSTMapNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public BSTMapNode(K key, V value, BSTMapNode left, BSTMapNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public BSTMap(BSTMapNode root) {
        this.root = root;
        this.size = (root == null) ? 0 : 1;
    }

    public BSTMap() {
        this.size = 0;
        this.root = null;
    }

    @Override
    public void put(K key, V value) {
        root = put(key, value, root); // root points to new updated tree
    }

    private BSTMapNode put(K key, V value, BSTMapNode node) {
        // basically just search the right node and put the new value ( helper for search?)
        // if node not existent, then insert it in the BST ( a helper for insert? )

        // base case: node is null => insert as key not existent in map
        if (node == null) {
            size++;
            return new BSTMapNode(key, value);
        }
        // else key is existent, find the right position and increment current value
        // NOTE: K generic type of key is comparable assumed
        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = put(key, value, node.left);
        } else if (cmp > 0) {
            node.right = put(key, value, node.right);
        } else {
            // key is equal, just update the new value
            node.value = value;
        }

        return node; // last call returns the root node with updated tree
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, BSTMapNode node) {
        // base case: node is null
        if (node == null) {
            return null;
        }

        // node exists, we need to search by comparison
        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return get(key, node.left);
        } else if (cmp > 0) {
            return  get(key, node.right);
        } else {
            return node.value;
        }
    }

    private boolean containsKeyHelper(BSTMapNode node, K key) {
        if (node == null) return false;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return containsKeyHelper(node.left, key);
        } else if (cmp > 0) {
            return containsKeyHelper(node.right, key);
        } else {
            return true;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        // root will be set to null so GC collects all the BSTnodes in the map
        root = null;
        // size is reset
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        // we will do traversal in-order to get sorted keys
        // create the set
        Set<K> keys = new LinkedHashSet<>();
        // add keys in-order in to the keys Set
        collectKeysInOrder(root, keys);
        return keys;
    }

    private void collectKeysInOrder(BSTMapNode node, Set<K> keySet) {
        // base case: node is null
        if (node == null) {
            return;
        }
        collectKeysInOrder(node.left, keySet);
        // do the processing - add the key into keyset
        keySet.add(node.key);
        collectKeysInOrder(node.right, keySet);
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        root = removeHelper(root, key);  // update root
        return value;
    }

    private BSTMapNode removeHelper(BSTMapNode node, K key) {
        // base case: node is null as in the node we want is not existent
        if (node == null) {
            return null;
        }
        // first find the right node to remove, then consider the deletion cases
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = removeHelper(node.left, key);
        } else if (cmp > 0) {
            node.right = removeHelper(node.right, key);
        } else {
            //  this is the tricky case — node.key == key
            // case 1 of deletion: leaf node
            if (node.left == null && node.right == null) {
                size--;
                return null;
            }
            // case 2 of deletion: either right or left child only exist
            else if (node.left == null) {
                size--;
                return node.right; // this deletes the node itself
            }
            else if (node.right == null) {
                size--;
                return node.left; // again same as above
            }
            // case 3: both children exist for the node to delete
            else {
                // find the in order successor on the right subtree
                BSTMapNode successor = findMin(node.right);
                // set the value of current node to the successor node
                node.key = successor.key;
                // now removing the successor same as the leaf removal case
                node.right = removeHelper(node.right, successor.key);
            }

        }

        return node;
    }

    private BSTMapNode findMin(BSTMapNode node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private List<K> collectKeysInOrder(BSTMapNode node, List<K> keys) {
        if (node == null) {
            return keys;
        }
        collectKeysInOrder(node.left, keys);
        // do the processing - add the key into keyset
        keys.add(node.key);
        collectKeysInOrder(node.right, keys);

        return keys;
    }
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private List<K> keysIter;
        private int index;

        public BSTMapIterator() {
            keysIter = new ArrayList<>();
            keysIter = collectKeysInOrder(root, keysIter);
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < keysIter.size();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            K nextKey = keysIter.get(index);
            index++;
            return nextKey;
        }

    }
}
