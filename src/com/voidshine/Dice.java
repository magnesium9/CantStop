package com.voidshine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Dice {
    public static final int COUNT = 4;
    public static final int MAX_VALUE = 6;

    int[] _Values = new int[COUNT];

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

    // Specify a "pairing" index (0, 1, or 2) that indicates
    // the second die to pair with the first (index *after*
    // skipping first).  Returns this sum as well as the
    // sum of the other pair.
    ArrayList<int[]> GetSumPairs() {
        ArrayList<int[]> pairs = new ArrayList<int[]>();
        List<int[]> noDuplicatePairs = new ArrayList<>(new HashSet<>(pairs));
        // Pairings of dice can be derived by holding first
        // die fixed and choosing one of the other three to
        // pair with it; the other pair is then implied.
        for (int i = 0; i < COUNT - 1; i++) {
            int[] sums = new int[2];
            sums[0] = _Values[0] + _Values[i + 1];
            sums[1] = Arrays.stream(_Values).sum() - sums[0];
            pairs.add(sums);
        }

        ArrayList<int[]> cleanpairs;
        cleanpairs = new ArrayList<String>(noDuplicatePairs);

        // Homework: Eliminate duplicates!  Hint, it may be
        // helpful to always have the sums sorted (this also
        // helps user think about what's happening).

        return cleanpairs;
    }
}
