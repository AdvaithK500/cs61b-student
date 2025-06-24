import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }
    @Test
    public void testAllClosedInitially() {
        Percolation p = new Percolation(3);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertThat(p.isOpen(r, c)).isFalse();
                assertThat(p.isFull(r, c)).isFalse();
            }
        }
        assertThat(p.percolates()).isFalse();
        assertThat(p.numberOfOpenSites()).isEqualTo(0);
    }

    @Test
    public void testFullSiteIsOnlyOpenAndConnectedToTop() {
        Percolation p = new Percolation(3);
        p.open(0, 1); // top row
        p.open(1, 1);
        p.open(2, 1); // now fully connected from top

        assertThat(p.isFull(2, 1)).isTrue(); // full
        assertThat(p.isFull(2, 0)).isFalse(); // not full
        assertThat(p.numberOfOpenSites()).isEqualTo(3);
    }

    @Test
    public void testBackwash() {
        Percolation p = new Percolation(3);
        p.open(0, 2);
        p.open(1, 2);
        p.open(2, 2); // creates path to bottom

        // Connect a site at bottom row that's not connected to top
        p.open(2, 0);

        assertThat(p.percolates()).isTrue();
        assertThat(p.isFull(2, 0)).isFalse(); // <- This ensures no backwash!
    }

    @Test
    public void testPercolatesMinimalPath() {
        Percolation p = new Percolation(3);
        p.open(0, 1);
        p.open(1, 1);
        p.open(2, 1);

        assertThat(p.percolates()).isTrue();
        assertThat(p.numberOfOpenSites()).isEqualTo(3);
    }

    @Test
    public void testPercolatesHorizontalNotEnough() {
        Percolation p = new Percolation(3);
        p.open(1, 0);
        p.open(1, 1);
        p.open(1, 2);

        assertThat(p.percolates()).isFalse(); // can't percolate just horizontally
    }

    @Test
    public void testPercolationStopsIfNoBottomConnection() {
        Percolation p = new Percolation(3);
        p.open(0, 1);
        p.open(1, 1);
        assertThat(p.percolates()).isFalse();
    }


}
