package com.voidshine;

enum Mode {
    PairingDice,
    RollOrStop,
}

// Holds full state of the game and evolves from one step to next
// in combination with Game class.
public class State {
    // Number of columns that must be claimed by a player to win
    static final int CLAIMS_REQUIRED = 3;

    private Game _Game;
    int _PlayerIndex;
    boolean _IsFinal;
    int _Winner;
    String _Error;
    Dice _Dice;
    Board _Board;
    Mode _Mode;

    State(Game game) {
        _Game = game;
        _Winner = -1;
        _Dice = new Dice();
        _Board = new Board();
        _Mode = Mode.PairingDice;
    }

    State Clone() {
        State s = new State(_Game);
        s._PlayerIndex = _PlayerIndex;
        s._IsFinal = _IsFinal;
        s._Error = _Error;
        s._Winner = _Winner;
        s._Dice = _Dice.Clone();
        s._Board = _Board.Clone();
        s._Mode = _Mode;
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
            switch (_Mode) {
                case PairingDice: {
                    sb.append("(" + Board.GetPlayerTokenChar(_PlayerIndex) + ") " + PlayerName() + " just rolled.\n");
                    sb.append("Dice: " + _Dice.ToString() + "\n");
                    break;
                }
                case RollOrStop: {
                    sb.append("(" + Board.GetPlayerTokenChar(_PlayerIndex) + ") " + PlayerName() + " must choose whether to push luck.\n");
                    break;
                }
            }
        }
        return sb.toString();
    }

    // Check win conditions and finalize winner if met
    boolean TryWin() {
        int claimed = _Board.CountClaimsByPlayer(_PlayerIndex);
        if (claimed >= CLAIMS_REQUIRED) {
            _IsFinal = true;
            _Winner = _PlayerIndex;
            return true;
        }
        return false;
    }
}
