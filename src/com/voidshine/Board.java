package com.voidshine;

public class Board {

    public static final int MAX_HEIGHT = 13;

    // First index will be from sums of dice pairs,
    // so the first two elements will remain null.
    // Elements indicate presence of player or
    // pawn or -1 for empty space.
    int[][] _Columns;

    Board() {
        _Columns = new int[Dice.MAX_VALUE * 2 + 1][];
        // 0, 1 -> not possible to sum -> N/A
        //  2 -> 3
        //  3 -> 5
        //  4 -> 7
        //  5 -> 9
        //  6 -> 11
        //  7 -> 13
        //  8 -> 11
        //  9 -> 9
        // 10 -> 7
        // 11 -> 5
        // 12 -> 3
        // Formula: 13 - abs(i - 7) * 2
        for (int i = 2; i < _Columns.length; i++) {
            _Columns[i] = new int[MAX_HEIGHT - Math.abs(7 - i) * 2];
            for (int j = 0; j < _Columns[i].length; j++) {
                _Columns[i][j] = -1;
            }
        }
    }

    public Board Clone() {
        Board b = new Board();
        for (int i = 2; i < _Columns.length; i++) {
            b._Columns[i] = _Columns[i].clone();
        }
        return b;
    }

    // Return vertical position of given player on given column.
    // Absence of player on the column returns -1.
    int GetPlayerPosition(int playerIndex, int column) {
        int[] c = _Columns[column];
        for (int y = c.length - 1; y >= 0; y--) {
            if (c[y] == playerIndex) {
                return y;
            }
        }
        return -1;
    }

    String ToString() {
        StringBuilder sb = new StringBuilder();
        for (int y = MAX_HEIGHT - 1; y >= 0; y--) {
            for (int x = 2; x < _Columns.length; x++) {
                int[] c = _Columns[x];
                if (y < c.length) {
                    int content = c[y];
                    if (content == -1) {
                        sb.append('o');
                    } else {
                        sb.append('A' + content);
                    }
                } else {
                    sb.append(' ');
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
