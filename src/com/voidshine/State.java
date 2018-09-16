package com.voidshine;

// Holds full state of the game and evolves from one step to next
// in combination with Game class.
public class State {
    private Game _Game;
    int _PlayerIndex;
    boolean _IsFinal;
    int _Winner;
    String _Error;
    Dice _Dice;
    Board _Board;

    State(Game game) {
        _Game = game;
        _Winner = -1;
        _Dice = new Dice();
        _Board = new Board();
    }

    State Clone() {
        State s = new State(_Game);
        s._PlayerIndex = _PlayerIndex;
        s._IsFinal = _IsFinal;
        s._Error = _Error;
        s._Winner = _Winner;
        s._Dice = _Dice.Clone();
        s._Board = _Board.Clone();
        return s;
    }

    String PlayerName() {
        return _Game._Players[_PlayerIndex]._Name;
    }

    String WinnerName() {
        return _Game._Players[_Winner]._Name;
    }

    String ToString() {
        StringBuilder sb = new StringBuilder();
        if (_Error != null) {
            sb.append("Error: " + _Error + "\n");
        }
        sb.append(_Board.ToString());
        if (_IsFinal) {
            sb.append("Game is over.\n");
            if (_Winner >= 0) {
                sb.append(WinnerName() + " wins!\n");
            }
        } else {
            sb.append(PlayerName() + " to play.\n");
            sb.append("Dice: " + _Dice.ToString() + "\n");
        }
        return sb.toString();
    }
}
