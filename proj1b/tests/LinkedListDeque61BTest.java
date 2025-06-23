import static com.google.common.truth.Truth.assertThat;

import deque.LinkedListDeque61B;
import org.junit.Test;
import java.util.Iterator;

public class LinkedListDeque61BTest {

    @Test
    public void testLinkedListToString() {
        LinkedListDeque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("front");
        lld.addLast("middle");
        lld.addLast("back");
        assertThat(lld.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void testLinkedListEquals() {
        LinkedListDeque61B<Integer> lld1 = new LinkedListDeque61B<>();
        LinkedListDeque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld1.addLast(1); lld1.addLast(2); lld1.addLast(3);
        lld2.addLast(1); lld2.addLast(2); lld2.addLast(3);
        assertThat(lld1.equals(lld2)).isTrue();
    }

    @Test
    public void testLinkedListIterator() {
        LinkedListDeque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(100); lld.addLast(200); lld.addLast(300);

        Iterator<Integer> iter = lld.iterator();
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.next()).isEqualTo(100);
        assertThat(iter.next()).isEqualTo(200);
        assertThat(iter.next()).isEqualTo(300);
        assertThat(iter.hasNext()).isFalse();
    }
}
