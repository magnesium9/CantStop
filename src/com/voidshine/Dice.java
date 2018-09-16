package com.voidshine;

import java.util.Arrays;

public class Dice {

    int[] _Values = new int[4];

    public Dice() {
        // Constructor.  Rolls the dice, so that they initially
        // show some random values.
        // Call the roll() method to roll the dice.
        roll();
    }

    public Dice Clone() {
        Dice d = new Dice();
        d._Values = _Values.clone();
        return d;
    }

    public void roll() {
        // Roll the dice by setting each of the dice to be
        // a random number between 1 and 6.
        for (int i = 0; i < _Values.length; i++) {
            _Values[i] = (int)(Math.random() * 6) + 1;
        }
    }

    String ToString() {
//        if (_Values == null) {
//            return "none";
//        }
        return String.join(" ", Arrays.stream(_Values).mapToObj(String::valueOf).toArray(String[]::new));
    }

}
