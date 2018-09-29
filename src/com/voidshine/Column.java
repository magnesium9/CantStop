package com.voidshine;

public class Column {
    // Column value indicating empty space
    public static final int EMPTY = -1;

    // The important aspect of this is that it can't be EMPTY or a player index.
    public static final int PAWN = 100;

    // Elements indicate presence of player or
    // pawn or empty space.  Note, to support
    // the original Can't Stop game we need a
    // different model, but this approach is
    // instructive -- we are modeling the
    // "Speed" version of Can't Stop by allowing
    // only one occupant in a space.
    int[] _Spaces;

    Column(int size) {
        _Spaces = new int[size];
        for (int j = 0; j < _Spaces.length; j++) {
            _Spaces[j] = EMPTY;
        }
    }

    Column Clone() {
        Column c = new Column(0);
        c._Spaces = _Spaces.clone();
        return c;
    }

    // Return vertical position of given player.
    // Absence of player on the column returns -1.
    int GetPlayerPosition(int playerIndex) {
        for (int y = _Spaces.length - 1; y >= 0; y--) {
            if (_Spaces[y] == playerIndex) {
                return y;
            }
        }
        return -1;
    }

    int GetLastSpace() {
        return _Spaces[_Spaces.length - 1];
    }
}
