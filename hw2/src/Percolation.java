import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // parameter to store grid size
    private final int N;
    //both union structures
    private final WeightedQuickUnionUF uf1; // for fullness (no vBottom)
    private final WeightedQuickUnionUF uf2; // for percolation (has vTop + vBottom)
    // both virtual node indexes
    private final int vTop;
    private final int vBottom;
    //counter for number of open sites
    private int numOfOpenSites;
    // grid
    private final boolean[][] tiles;
    public Percolation(int N) {
        // sanity check
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;

        // virtual > N*(N-1) ;  and > 0 to be valid
        vTop = N * N;
        vBottom = N * N + 1;

        //initialise both union structures
        uf1 = new WeightedQuickUnionUF(N * N + 1); // all grid tiles + vTop
        uf2 = new WeightedQuickUnionUF(N * N + 2); // all grid tiles + both virtual

        tiles = new boolean[N][N];
        // initially all tiles must be blocked
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = false;
            }
        }

        numOfOpenSites = 0;
    }

    public void open(int row, int col) {
        checkValidRowAndColumn(row, col);

        int index = xyTo1D(row, col);

        if (!isOpen(row, col)) {
            openSite(row, col);
            numOfOpenSites++;
            checkAndUnionNeighbors(row, col, index);
        }

        if (row == 0) { // connect to virtualTop if top row
            uf1.union(index, vTop);
            uf2.union(index, vTop);
        }

        // if row == bottom, call uf2.union(index, vBottom)
        if (row == N - 1) {
            uf2.union(index, vBottom);
        }
    }

    public boolean isOpen(int row, int col) {
        checkValidRowAndColumn(row, col);
        return tiles[row][col];
    }

    public boolean isFull(int row, int col) {
        checkValidRowAndColumn(row, col);
        return isOpen(row, col) && uf1.connected(xyTo1D(row, col), vTop);
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        return uf2.connected(vTop, vBottom);
    }

    // Helper methods below
    // to convert a 2D coordinate to 1D
    private int xyTo1D(int row, int col) {
        return N * row + col; // converts the 2D grid to a 1D array for DSU algorithms
    }
    // to open a site
    private void openSite(int row, int col) {
        tiles[row][col] = true;
    }

    private void checkAndUnionNeighbors(int row, int col, int index) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : dirs) {
            int r = row + d[0];
            int c = col + d[1];
            // if valid indices and the neighbour tile is open, then perform union
            if (r >= 0 && r < N && c >= 0 && c < N && isOpen(r, c)) {
                int neighbor = xyTo1D(r, c);
                uf1.union(index, neighbor);
                uf2.union(index, neighbor);
            }
        }
    }


    private void checkValidRowAndColumn(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N)
            throw new IndexOutOfBoundsException();

    }

}
