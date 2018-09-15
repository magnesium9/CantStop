package com.voidshine;

import java.util.Arrays;

public class State {
    private Game _Game;
    int _PlayerIndex;
    boolean _IsFinal;
    int _Winner;
    String _Error;
    int[] _Dice;

    State(Game game) {
        _Game = game;
        _Winner = -1;
    }

    State Clone() {
        State s = new State(_Game);
        s._PlayerIndex = _PlayerIndex;
        s._IsFinal = _IsFinal;
        s._Error = _Error;
        s._Winner = _Winner;
        s._Dice = _Dice == null ? null : _Dice.clone();
        return s;
    }

    String PlayerName() {
        return _Game._Players[_PlayerIndex]._Name;
    }

    String WinnerName() {
        return _Game._Players[_Winner]._Name;
    }

    String DiceString() {
//        String.join(" ", Arrays.stream(_Dice).map(d -> Integer::toString).collect(
        return String.join(" ", new String[]{"a", "b", "c", "d",});
    }

    String ToString() {
        StringBuilder sb = new StringBuilder();
        if (_Error != null) {
            sb.append("Error: " + _Error + "\n");
        }
        if (_IsFinal) {
            sb.append("Game is over.\n");
            if (_Winner >= 0) {
                sb.append(WinnerName() + " wins!\n");
            }
        } else {
            sb.append(PlayerName() + " to play.\n");
            sb.append("Dice: " + DiceString() + "\n");
        }
        return sb.toString();
    }
}
