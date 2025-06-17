import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDeque61BTest {

    @Test
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back");
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle");
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(9);
        assertThat(lld1.isEmpty()).isFalse();

        lld1.addLast(9);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addFirst(0);
        assertThat(lld1.size()).isEqualTo(1);

        lld1.addFirst(-1);
        lld1.addLast(2);
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public void testGet() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.get(-2)).isEqualTo(null);
        lld1.addFirst(1);
        assertThat(lld1.get(0)).isEqualTo(1);

        lld1.addLast(2);
        lld1.addFirst(0);
        assertThat(lld1.get(2)).isEqualTo(2);
        assertThat(lld1.get(3)).isEqualTo(null);
        assertThat(lld1.get(100)).isEqualTo(null);
    }

    @Test
    public void testGetRecursive() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.getRecursive(-2)).isEqualTo(null);
        lld1.addFirst(1);
        assertThat(lld1.getRecursive(0)).isEqualTo(1);

        lld1.addLast(2);
        lld1.addFirst(0);
        assertThat(lld1.getRecursive(2)).isEqualTo(2);
        assertThat(lld1.getRecursive(3)).isEqualTo(null);
        assertThat(lld1.getRecursive(100)).isEqualTo(null);
    }

    @Test
    public void testRemoveFirst() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);

        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(0, 1, 2).inOrder();
    }

    @Test
    public void testRemoveLast() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        lld1.addLast(2);
        lld1.addFirst(-2);

        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly(-2, -1, 0).inOrder();
    }

    @Test
    public void testIsEmptyAndSize() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(5);
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addFirst(0);
        lld1.addFirst(-1);
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void testRemoveLastToEmpty() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeLast();
        deque.removeLast();
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveFirstToOne() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.removeFirst();
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(3).inOrder();
    }

    @Test
    public void testRemoveLastToOne() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.removeLast();
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void testSizeAfterRemoveFromEmpty() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.removeFirst();
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testToListEmpty() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        assertThat(deque.toList()).isEmpty();
    }
}
