import deque.ArrayDeque61B;
import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Test
    public void testResizeUpAddLast() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Fill up to capacity (8)
        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }

        // Now trigger resize
        deque.addLast(8);
        deque.addLast(9);

        // Assert correct size
        assertThat(deque.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    public void testResizeUpAddFirst() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Fill using addFirst — reverse order
        for (int i = 0; i < 8; i++) {
            deque.addFirst(i);
        }

        // Force resize
        deque.addFirst(8);
        deque.addFirst(9);

        // Reverse the expected order
        assertThat(deque.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).inOrder();
    }

    @Test
    public void testResizeUpMixedAdds() {
        Deque61B<String> deque = new ArrayDeque61B<>();

        // Alternate adds
        deque.addLast("c");
        deque.addFirst("b");
        deque.addLast("d");
        deque.addFirst("a");
        deque.addLast("e");
        deque.addLast("f");
        deque.addFirst("X");
        deque.addFirst("Y");

        // Fill capacity and force resize
        deque.addLast("g");
        deque.addFirst("Z");

        assertThat(deque.toList()).containsExactly("Z", "Y", "X", "a", "b", "c", "d", "e", "f", "g").inOrder();
    }

    @Test
    public void testWastefulArrayAfterShrink() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int i = 0; i < 32; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 30; i++) {
            deque.removeLast();
        }

        for (int i = 0; i < 20; i++) {
            deque.addLast(i); // should crash or behave badly if array wasn't resized down
        }

        assertThat(deque.toList().size()).isEqualTo(22);
    }

    @Test
    public void testRapidAddRemove() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Fill up with 100 elements to trigger multiple resizeUps
        for (int i = 0; i < 100; i++) {
            deque.addLast(i);
        }
        assertThat(deque.size()).isEqualTo(100);

        // Remove 90 elements to trigger resizeDown (if implemented to halve at 1/4 size)
        for (int i = 0; i < 90; i++) {
            deque.removeFirst();
        }

        // Remaining elements should be 90 to 99
        assertThat(deque.toList()).containsExactly(90,91,92,93,94,95,96,97,98,99).inOrder();
        assertThat(deque.size()).isEqualTo(10);
    }

    @Test
    public void testMixedAddRemoveResizing() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // Add to front to reverse order
        for (int i = 0; i < 64; i++) {
            deque.addFirst(i);
        }

        // Remove from end and check order
        for (int i = 0; i < 32; i++) {
            assertThat(deque.removeLast()).isEqualTo(i);
        }

        // Check remaining 32 items in reverse
        List<Integer> expected = new ArrayList<>();
        for (int i = 63; i >= 32; i--) {
            expected.add(i);
        }
        assertThat(deque.toList()).containsExactlyElementsIn(expected).inOrder();
    }

    @Test
    public void testRepeatedResizingDown() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        for (int round = 0; round < 5; round++) {
            for (int i = 0; i < 128; i++) {
                deque.addLast(i);
            }
            for (int i = 0; i < 120; i++) {
                deque.removeFirst();  // Should cause multiple downsizes
            }
            assertThat(deque.size()).isEqualTo(8);
            ((ArrayDeque61B<Integer>) deque).clear();  // You'll need to add a clear() method or remove all manually
        }
    }

    @Test
    public void testArrayDequeToString() {
        ArrayDeque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void testArrayDequeEquals() {
        ArrayDeque61B<Integer> ad1 = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> ad2 = new ArrayDeque61B<>();
        ad1.addLast(1); ad1.addLast(2); ad1.addLast(3);
        ad2.addLast(1); ad2.addLast(2); ad2.addLast(3);
        assertThat(ad1.equals(ad2)).isTrue();
    }

    @Test
    public void testArrayDequeIterator() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10); ad.addLast(20); ad.addLast(30);

        Iterator<Integer> iter = ad.iterator();
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.next()).isEqualTo(10);
        assertThat(iter.next()).isEqualTo(20);
        assertThat(iter.next()).isEqualTo(30);
        assertThat(iter.hasNext()).isFalse();
    }

}
