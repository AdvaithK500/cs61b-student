public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        // what if node doesnt have both children? no flipping of colors needed
        // flip the color of node
        node.isBlack = !node.isBlack;
        // flip the color of node's children
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // set a pointer to left child of node
        RBTreeNode<T> p = node.left;
        // set left child to right child of p
        node.left = p.right;
        // set right child of p to node
        p.right = node;

        // set the colors
        p.isBlack = node.isBlack;
        node.isBlack = false;

        return p;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // set a pointer to left child of node
        RBTreeNode<T> p = node.right;
        // set left child to right child of p
        node.right = p.left;
        // set right child of p to node
        p.left = node;

        // set the colors
        p.isBlack = node.isBlack;
        node.isBlack = false;

        return p;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        // Regular BST comparison to find the right position to insert
        if (node == null) {
            // the position to insert is found
            //insert new red leaf node
            return new RBTreeNode<>(false, item);
        }
        // TODO: Handle normal binary search tree insertion.
        int cmp = item.compareTo(node.item);
        // if item > node's item, search right of tree
        if (cmp > 0) {
            node.right = insertHelper(node.right, item);
        } else if (cmp < 0) {
            // if item < node's item, search left of tree
            node.left = insertHelper(node.left, item);
        } else {
            // insertion is done
            return node;
        }
        // now node's left or right child points to the new node we created in the node == null condition
        // then when teh else is entered above
        // we go one call up in the stack
        // so now the node points to the parent of the new node we inserted
        // Now fix-up: handle Red-Black Tree invariants

        // Case 1: Right-leaning red link -> rotate left
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }

        // Case 2: Two consecutive left red links -> rotate right
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        // Case 3: Both children are red -> flip colors
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

}
