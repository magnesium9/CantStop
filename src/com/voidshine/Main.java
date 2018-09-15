package com.voidshine;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(new String[]{"zimoyoyo", "magnesium9"});
        State state = game.NewGameState();
        Scanner scanner = new Scanner(System.in);
        while (!state._IsFinal) {
            System.out.println(state.ToString());
            System.out.print(" > ");
            String line = scanner.nextLine();
            if (line.equals("quit")) {
                break;
            } else {
                state = game.TakeAction(state, line);
            }
        }
        System.out.println("Final game state...");
        System.out.println(state.ToString());
    }
}
