package com.voidshine;

import java.util.function.Function;

public class Move {
    // State that results from making this move
    State _Next;

    // Textual description of what's happening to game state if this move is taken.
    String _Description;

    // A short string the user can enter to identify that they want to take this move
    String _Action;

    Move(State from, Function<State, String> nextStateGenerator) {
        _Next = from.Clone();
        _Next._From = from;
        _Description = nextStateGenerator.apply(_Next);
    }
}
