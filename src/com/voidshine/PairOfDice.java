package com.voidshine;

public class PairOfDice {

    int[] _Dice = new int[4];

    public PairOfDice() {
        // Constructor.  Rolls the dice, so that they initially
        // show some random values.
        // Call the roll() method to roll the dice.
        roll();
    }

    public void roll() {
        // Roll the dice by setting each of the dice to be
        // a random number between 1 and 6.
        for (int i = 0; i < _Dice.length; i++) {

            _Dice[i] = (int) (Math.random() * 100) % 6 + 1;
        }
    }

    public static void main(String[] args) {

        int[] _Dice;

        PairOfDice pairofdice = new PairOfDice();
        pairofdice.roll();
        System.out.println("The result of first one is " + pairofdice._Dice[0]);
        System.out.println("The result of first second is " + pairofdice._Dice[1]);
        System.out.println("The result of first third is " + pairofdice._Dice[2]);
        System.out.println("The result of first fourth is " + pairofdice._Dice[3]);


    }


}
