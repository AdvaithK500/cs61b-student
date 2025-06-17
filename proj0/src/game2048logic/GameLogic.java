package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c     the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        int value = board[r][c];
        if (value == 0) return 0;

        int targetRow = r;
        while (targetRow > minR && board[targetRow - 1][c] == 0) {
            targetRow--;
        }

        // ✅ FIXED: Only allow merge if the targetRow - 1 is >= minR
        if (targetRow > 0 && (targetRow - 1) >= minR && board[targetRow - 1][c] == value) {
            board[targetRow - 1][c] *= 2;
            board[r][c] = 0;
            return targetRow; // returns 1-based row index
        }

        if (targetRow != r) {
            board[targetRow][c] = value;
            board[r][c] = 0;
        }

        return 0;
    }

    /** Simulates tilting column `c` upwards */
    public static void tiltColumn(int[][] board, int c) {
        int minR = 0;

        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][c] != 0) {
                int mergedRow = moveTileUpAsFarAsPossible(board, r, c, minR);

                // ✅ FIXED: Only update minR if a merge occurred
                if (mergedRow != 0) {
                    minR = mergedRow - 1;
                }
            }
        }

        // Final compaction: bring all tiles up to remove gaps
        int writeRow = 0;
        for (int readRow = 0; readRow < board.length; readRow++) {
            if (board[readRow][c] != 0) {
                if (readRow != writeRow) {
                    board[writeRow][c] = board[readRow][c];
                    board[readRow][c] = 0;
                }
                writeRow++;
            }
        }
    }

    /** Tilts all columns upwards */
    public static void tiltUp(int[][] board) {
        for (int c = 0; c < board[0].length; c++) {
            tiltColumn(board, c);
        }
    }

    /** Handles tilt in any direction */
    public static void tilt(int[][] board, Side side) {
        if (side == Side.NORTH) {
            tiltUp(board);
        } else if (side == Side.SOUTH) {
            rotateLeft(board);
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            rotateRight(board);
        } else if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
        }
    }
}
