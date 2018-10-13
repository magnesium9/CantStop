package com.voidshine;

import java.util.ArrayList;

public class Board {

    public static final int MAX_HEIGHT = 13;

    public static final int NUM_PAWNS = 3;

    static char GetPlayerTokenChar(int playerIndex) {
        return (char)('A' + playerIndex);
    }

    // Index will be from sums of dice pairs,
    // so the first two elements will remain null
    // (minimum sum of a dice pair is 1 + 1 = 2).
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
        b._PawnsAvailable = _PawnsAvailable;
        return b;
    }

    String ToString() {
        StringBuilder sb = new StringBuilder();
        for (int y = MAX_HEIGHT - 1; y >= 0; y--) {
            for (int x = 2; x < _Columns.length; x++) {
                int[] c = _Columns[x]._Spaces;
                if (y < c.length) {
                    int content = c[y];
                    if (content == Column.EMPTY) {
                        sb.append("[ ]");
                    } else if (content == Column.PAWN) {
                        sb.append("[P]");
                    } else {
                        sb.append("[" + GetPlayerTokenChar(content) + "]");
                    }
                } else {
                    sb.append("   ");
                }
            }
            sb.append("\n");
        }
        for (int x = 2; x < _Columns.length; x++) {
            sb.append(String.format((x >= 10 ? " %2d" : "%2d "), x));
        }
        sb.append("\nPawns Available: " + _PawnsAvailable + "\n");
        return sb.toString();
    }

    ArrayList<Integer> AdvancePawns(int[] columnIndices, int playerIndex) {
        ArrayList<Integer> advanced = new ArrayList<Integer>();
        for (int columnIndex : columnIndices) {
            Column column = _Columns[columnIndex];
            if (!column.IsPlayable()) {
                continue;
            }
            int pawnPosition = column.GetPlayerPosition(Column.PAWN);
            if (pawnPosition == -1) {
                if (_PawnsAvailable <= 0) {
                    continue;
                } else {
                    int playerPosition = column.GetPlayerPosition(playerIndex);
                    pawnPosition = column.GetSuccessor(playerPosition);
                    if (pawnPosition != playerPosition) {
                        column.SetSpace(pawnPosition, Column.PAWN);
                        _PawnsAvailable--;
                        advanced.add(columnIndex);
                    }
                }
            } else {
                int old = pawnPosition;
                column.SetSpace(pawnPosition, Column.EMPTY);
                pawnPosition = column.GetSuccessor(pawnPosition);
                column.SetSpace(pawnPosition, Column.PAWN);

                if (pawnPosition != old) {
                    advanced.add(columnIndex);
                }
            }
        }
        return advanced;
    }

    // Removes all pawns from the board, with no player advancement
    void ClearPawns() {
        for (Column c : _Columns) {
            // Some columns may be null (e.g. at indices 0, 1)
            if (c == null) {
                continue;
            }
            for (int i = 0; i < c._Spaces.length; i++) {
                if (c._Spaces[i] == Column.PAWN) {
                    c._Spaces[i] = Column.EMPTY;
                }
            }
        }
        _PawnsAvailable = NUM_PAWNS;
    }

    // Similar in effect to ClearPawns, but advances given player to pawn positions.
    void AdvancePlayerToPawns(int playerIndex) {
        for (Column c : _Columns) {
            if (c == null) {
                continue;
            }
            int to = c.GetPlayerPosition(Column.PAWN);
            if (to >= 0) {
                int from = c.GetPlayerPosition(playerIndex);
                if (from >= 0) {
                    c.SetSpace(from, Column.EMPTY);
                }
                c.SetSpace(to, playerIndex);
                _PawnsAvailable++;
            }
        }
    }
}
