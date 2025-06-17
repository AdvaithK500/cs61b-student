package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        int value = board[r][c];
        if (value == 0) return 0;

        int targetRow = r;
        while (targetRow > 0 && board[targetRow - 1][c] == 0) {
            targetRow--;
        }

        if (targetRow > 0 && (targetRow - 1) >= minR && board[targetRow - 1][c] == value) {
            board[targetRow - 1][c] *= 2;
            board[r][c] = 0;
            return targetRow; // 1-based return
        }

        if (targetRow != r) {
            board[targetRow][c] = value;
            board[r][c] = 0;
        }

        return 0;
    }

    public static void tiltColumn(int[][] board, int c) {
        int minR = 0;
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][c] != 0) {
                int mergedRow = moveTileUpAsFarAsPossible(board, r, c, minR);
                if (mergedRow != 0) {
                    minR = mergedRow;  // mergedRow is already 1-based
                }
            }
        }

        // Compact the column
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

    public static void tiltUp(int[][] board) {
        for (int c = 0; c < board[0].length; c++) {
            tiltColumn(board, c);
        }
    }


    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.EAST) {
            return;
        } else if (side == Side.WEST) {
            return;
        } else if (side == Side.SOUTH) {
            return;
        } else {
            return;
        }
    }
}
