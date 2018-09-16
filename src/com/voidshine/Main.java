package com.voidshine;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(new String[]{"zimoyoyo", "magnesium9"});
        State state = game.NewGameState();
        Scanner scanner = new Scanner(System.in);
        PrintStream o = System.out;
        while (!state._IsFinal) {
            o.println(state.ToString());
            ArrayList<Move> moves = game.GetMoves(state);
            for (Move m : moves) {
                o.println(m._Action + " -> " + m._Description);
            }
            o.print(" > ");
            String line = scanner.nextLine();
            if (line.equals("quit")) {
                break;
            } else {
                Move chosenMove = null;
                for (Move m : moves) {
                    if (m._Action.equals(line)) {
                        chosenMove = m;
                        break;
                    }
                }
                if (chosenMove == null) {
                    o.println("Invalid action.");
                } else {
                    state = chosenMove._Next;
                }
            }
        }
        o.println("Final game state...");
        o.println(state.ToString());
    }
}
