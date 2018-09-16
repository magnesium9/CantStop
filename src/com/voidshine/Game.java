package com.voidshine;

import java.util.ArrayList;

// A game encapsulates the rules and things that remain constant
// from beginning to end of a play of the game.
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

    ArrayList<Move> GetMoves(State from) {
        ArrayList<Move> moves = new ArrayList<Move>();

        if (from._IsFinal) {
            // No moves to make
            return moves;
        }

        // If game is active we can always accept a resign move
        Move resign = new Move(from, "Resign", s -> {
            s._IsFinal = true;
            s._Winner = NextPlayer(s._PlayerIndex);
        });
        moves.add(resign);

        // Stop move
        Move stop = new Move(from, "Stop", s -> {
            s._PlayerIndex = NextPlayer(s._PlayerIndex);
            s._Dice.roll();
        });
        moves.add(stop);

        // Pairings of dice can be derived by holding first
        // die fixed and choosing one of the other three to
        // pair with it; the other pair is then implied.
        for (int i = 0; i < from._Dice.COUNT - 1; i++) {
            // Homework: Eliminate duplicates!  Hint, it may be
            // helpful to always have the sums sorted (this also
            // helps user think about what's happening).
            int[] sums = from._Dice.GetSums(i);
            Move move = new Move(from, "Advance on " + sums[0] + " and " + sums[1], s -> {

            });
            moves.add(move);
        }

        // Assign easy-to-type action strings for all moves
        int counter = 0;
        for (Move m : moves) {
            m._Action = Integer.toString(counter);
            counter++;
        }

        return moves;
    }
}
