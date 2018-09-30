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

    // Modify given state to end current player's turn
    // and begin the next player's turn.
    void EndTurn(State s) {
        s._PlayerIndex = NextPlayer(s._PlayerIndex);
        s._Dice.roll();
        s._Mode = Mode.PairingDice;
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

        switch (from._Mode) {
            case RollOrStop: {
                // Stop move
                Move stop = new Move(from, "Stop", s -> {
                    EndTurn(s);
                });
                moves.add(stop);

                // Roll move
                Move roll = new Move(from, "Roll", s -> {
                    // Homework
                    s._Error = "TODO: Roll state";
                });
                moves.add(roll);
            }
            case PairingDice: {
                if (from._Mode == Mode.PairingDice) {
                    for (int[] sums : from._Dice.GetSumPairs()) {
                        Move move = new Move(from, "Advance on " + sums[0] + " and " + sums[1], s -> {
                            boolean advanced = s._Board.AdvancePawns(sums, s._PlayerIndex);
                            if (advanced) {
                                s._Mode = Mode.RollOrStop;
                            } else {
                                // Bust!
                                s._Board.ClearPawns();
                                EndTurn(s);
                            }
                        });
                        moves.add(move);
                    }
                }
            }
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
