
package hex;

import java.io.File;
import java.util.Scanner;

public class Assignment6Driver {
    public static void main(String[] args) {

        testGame();
        // System.out.println("\n" + "-".repeat(40) + "\n");
        // playGame("moves1.txt");
        // System.out.println();
        // playGame("moves2.txt");
    }

    private static void playGame(String filename) {
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file)) {
            var game = new HexGame(11);
            var done = false;
            while (!done) {
                if (scanner.hasNextLine()) {
                    Integer bluePosition = Integer.parseInt(scanner.nextLine());
                    done = game.playBlue(bluePosition, false);
                    if (done) {
                        System.out.println("Blue wins with move at position " + bluePosition + "!!");
                        printGrid(game);
                        return;
                    }
                } else {
                    done = true;
                }
                if (scanner.hasNextLine()) {
                    Integer redPosition = Integer.parseInt(scanner.nextLine());
                    done = game.playRed(redPosition, false);
                    if (done) {
                        System.out.println("Red wins with move at position " + redPosition + "!!");
                        printGrid(game);
                        return;
                    }
                }
            }
        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the moves file: " + ex);
        }
    }

    //
    // DONE: You can use this to compare with the output show in the assignment
    // while working on your code
    private static void testGame() {
        HexGame game = new HexGame(11);

        System.out.println("--- red ---");
        game.playRed(1, true);
        game.playRed(11, true);
        game.playRed(122 - 12, true);
        game.playRed(122 - 11, true);
        game.playRed(122 - 10, true);
        game.playRed(121, true);
        game.playRed(61, true);

        System.out.println("--- blue ---");
        game.playBlue(1, true);
        game.playBlue(2, true);
        game.playBlue(11, true);
        game.playBlue(12, true);
        game.playBlue(121, true);
        game.playBlue(122 - 11, true);
        game.playBlue(62, true);

        printGrid(game);
    }

    // DONE: Complete this method
    private static void printGrid(HexGame game) {
        System.out.println(game.toString());
    }

}
