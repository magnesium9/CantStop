package com.voidshine;

public class Game {
    Player[] _Players;

    Game(String[] playerNames) {
        _Players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            _Players[i] = new Player(playerNames[i]);
        }
    }

    int NumPlayers() {
        return _Players.length;
    }

    State NewGameState() {
        State s = new State(this);
        s._PlayerIndex = 0;
        s._IsFinal = false;
        return s;
    }

    int NextPlayer(int currentPlayer) {
        return (currentPlayer + 1) % NumPlayers();
    }

    State TakeAction(State from, String actionSpec) {
        State to = from.Clone();
        to._Error = null;
        if (actionSpec.equals("stop")) {
            to._PlayerIndex = NextPlayer(to._PlayerIndex);
        } else if (actionSpec.equals("resign")) {
            to._IsFinal = true;
            to._Winner = (from._PlayerIndex + 1) % NumPlayers();
        } else {
            to._Error = "Invalid action.";
        }
        return to;
    }
}
