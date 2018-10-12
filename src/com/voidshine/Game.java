package com.voidshine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Move resign = new Move(from, s -> {
            s._IsFinal = true;
            s._Winner = NextPlayer(s._PlayerIndex);
            return "Resign";
        });
        moves.add(resign);

        switch (from._Mode) {
            case RollOrStop: {
                // Stop move
                Move stop = new Move(from, s -> {
                    s._Board.AdvancePlayerToPawns(s._PlayerIndex);
                    EndTurn(s);
                    return "Stop";
                });
                moves.add(stop);

                // Roll move
                Move roll = new Move(from, s -> {
                    s._Mode = Mode.PairingDice;
                    s._Dice.roll();
                    return "Roll";
                });
                moves.add(roll);
            }
            case PairingDice: {
                // Homework: Break down pair moves into single moves when only one pawn can move.
                // If two pawns can move (one for each dice pair), great; but often only one of
                // the dice pairs will reference a column that has a pawn, and the other cannot
                // be used either because the column is claimed or there are no more pawns to
                // place.  In these cases, the player should be able to choose the single
                // pawn move to make.  Sometimes there may be only one such option, and it should
                // still be presented.  This one is advanced homework; give it a try.
                if (from._Mode == Mode.PairingDice) {
                    // First gather pawn moves to see if any actually advance
                    ArrayList<Move> pawnMoves = new ArrayList<>();
                    for (int[] sums : from._Dice.GetSumPairs()) {
                        Move move = new Move(from, s -> {
                            List<Integer> advanced = s._Board.AdvancePawns(sums, s._PlayerIndex);
                            if (advanced.size() > 0) {
                                s._Mode = Mode.RollOrStop;
                                List<String> stringValues = advanced.stream().map(i -> Integer.toString(i)).collect(Collectors.toList());
                                return "Advance on " + String.join(" and ", stringValues);
                            } else {
                                s._Error = "Pawns could not advance.";
                                return null;
                            }
                        });
                        if (move._Next._Error == null) {
                            pawnMoves.add(move);
                        }
                    }

                    // Then we either provide good pawn moves or a bust/pass-turn move
                    // to acknowledge utter (well, partial) defeat.
                    if (pawnMoves.size() > 0) {
                        moves.addAll(pawnMoves);
                    } else {
                       // No pawn moves -> All Bust!
                        Move bust = new Move(from, s -> {
                            s._Board.ClearPawns();
                            EndTurn(s);
                            return "Bust!  Pass turn.";
                        });
                        moves.add(bust);
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
