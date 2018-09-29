package com.voidshine;

public class Board {

    public static final int MAX_HEIGHT = 13;

    public static final int NUM_PAWNS = 3;

    // First index will be from sums of dice pairs,
    // so the first two elements will remain null.
    Column[] _Columns;

    int _PawnsAvailable;

    Board() {
        _Columns = new Column[Dice.MAX_VALUE * 2 + 1];
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
            _Columns[i] = new Column(MAX_HEIGHT - Math.abs(7 - i) * 2);
        }

        _PawnsAvailable = NUM_PAWNS;
    }

    public Board Clone() {
        Board b = new Board();
        for (int i = 2; i < _Columns.length; i++) {
            b._Columns[i] = _Columns[i].Clone();
        }
        return b;
    }

    String ToString() {
        StringBuilder sb = new StringBuilder();
        for (int y = MAX_HEIGHT - 1; y >= 0; y--) {
            for (int x = 2; x < _Columns.length; x++) {
                int[] c = _Columns[x]._Spaces;
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


    boolean AdvancePawns(int[] columnIndices) {
        for (int columnIndex : columnIndices) {
            Column column = _Columns[columnIndex];
            int pawnPosition = column.GetPlayerPosition(Column.PAWN);
        }
        return true;
    }
}
