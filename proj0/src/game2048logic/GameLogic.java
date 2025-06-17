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

        //  Step 0: If the tile is zero, do nothing
        if (value == 0) return 0;

        //  Step 1: find the targetRow to Slide tile up to, through empty cells
        // We look upward until we hit a non-empty cell or the top row (row  == 0 case)
        int targetRow = r;
        while (targetRow > 0 && targetRow > minR && board[targetRow - 1][c] == 0) {
            targetRow--;
        }

        //  Step 2: Try to merge with the tile above
        // If we landed just below a tile of the same value, merge
        if (targetRow > 0 && targetRow != minR && board[targetRow - 1][c] == value) {
            board[targetRow - 1][c] *= 2;  // Merge: double the value
            board[r][c] = 0;               // Clear original tile
            return targetRow;              // Return merge location (1-based row)
        }

        //  Step 3: this is task 2. If we couldn't merge, just move the tile to its new position
        // Only do this if the tile moved at all (to an empty spot)
        if (targetRow != r) {
            board[targetRow][c] = value;   // Move tile into empty space
            board[r][c] = 0;               // Clear original position
        }

        //  No merge happened, return 0 as specified by Task 3
        return 0;
    }


    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // TODO: fill this in in task 5
        return;
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // TODO: fill this in in task 6
        return;
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
