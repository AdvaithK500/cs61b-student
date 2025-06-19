import deque.ArrayDeque61B;
import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    @Test
    public void testAddFirstBasic() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addFirst("c");
        assertThat(ad.toList()).containsExactly("c").inOrder();

        ad.addFirst("b");
        assertThat(ad.toList()).containsExactly("b", "c").inOrder();

        ad.addFirst("a");
        assertThat(ad.toList()).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testAddFirstWraparound() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // Add many items to cause wraparound (but not trigger resize yet)
        for (int i = 0; i < 8; i++) {
            ad.addFirst(i);
        }

        assertThat(ad.toList()).containsExactly(7, 6, 5, 4, 3, 2, 1, 0).inOrder();
    }

    @Test
    public void testAddLastBasic() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("a");
        assertThat(ad.toList()).containsExactly("a").inOrder();

        ad.addLast("b");
        assertThat(ad.toList()).containsExactly("a", "b").inOrder();

        ad.addLast("c");
        assertThat(ad.toList()).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testAddLastWraparound() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // Add 8 items, which fills the base capacity
        for (int i = 0; i < 8; i++) {
            ad.addLast(i);
        }

        assertThat(ad.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7).inOrder();
    }

    @Test
    public void testGet() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // Edge case: get on empty and negative
        assertThat(ad.get(-2)).isEqualTo(null);

        ad.addFirst(1);
        assertThat(ad.get(0)).isEqualTo(1);

        ad.addLast(2);
        ad.addFirst(0);  // now deque: 0, 1, 2

        assertThat(ad.get(2)).isEqualTo(2);
        assertThat(ad.get(3)).isEqualTo(null);
        assertThat(ad.get(100)).isEqualTo(null);
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // Initially empty
        assertThat(ad.isEmpty()).isTrue();

        // After addFirst
        ad.addFirst(9);
        assertThat(ad.isEmpty()).isFalse();

        // After addLast
        ad.addLast(5);
        assertThat(ad.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // Initially size 0
        assertThat(ad.size()).isEqualTo(0);

        // After 1 insertion
        ad.addFirst(1);
        assertThat(ad.size()).isEqualTo(1);

        // After 2 more insertions
        ad.addFirst(-1);
        ad.addLast(3);
        assertThat(ad.size()).isEqualTo(3);
    }

    @Test
    public void testToListEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.toList()).isEmpty();
    }

    @Test
    public void testToListAddFirst() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addFirst(0);  // deque: 0, 1, 2

        assertThat(deque.toList()).containsExactly(0, 1, 2).inOrder();
    }

    @Test
    public void testToListAddLast() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        assertThat(deque.toList()).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testToListMixed() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("b");
        deque.addFirst("a");
        deque.addLast("c");

        assertThat(deque.toList()).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testRemoveFirst() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addLast(0);
        ad.addLast(1);
        ad.addFirst(-1);
        ad.addLast(2);
        ad.addFirst(-2);

        ad.removeFirst();
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(0, 1, 2).inOrder();
    }

    @Test
    public void testRemoveLast() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addLast(0);
        ad.addLast(1);
        ad.addFirst(-1);
        ad.addLast(2);
        ad.addFirst(-2);

        ad.removeLast();
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(-2, -1, 0).inOrder();
    }

    @Test
    public void testRemoveLastToEmpty() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addLast(1);
        ad.addLast(2);
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.isEmpty()).isTrue();
        assertThat(ad.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveFirstToOne() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.removeFirst();
        ad.removeFirst();
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.toList()).containsExactly(3).inOrder();
    }

    @Test
    public void testRemoveLastToOne() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.toList()).containsExactly(1).inOrder();
    }


}
