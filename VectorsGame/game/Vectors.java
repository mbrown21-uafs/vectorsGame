package game;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Vectors {

    private int rounds = 20;
    private char[][] board;

    private int playerScore;
    private int computerScore;

    private int playerLastRound;
    private int computerLastRound;

    private int currentRound = 0;

    private String highScoreName = "None";
    private int highscore = 0;

    private Set<String> scoredVectors;

    public Vectors() {

        board = new char[8][8];

        scoredVectors = new HashSet<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = ' ';
            }
        }
    }

    public void displayBoard() {

        System.out.println("**************************************");
        System.out.println("- V E C T O R S                      *");
        System.out.println("**************************************");

        System.out.printf("- High Score: %-20s   *%n",
                highScoreName + " - " + highscore);

        System.out.println("**************************************");

        System.out.printf("- Player Score: %5d Last Round %3d *%n",
                playerScore, playerLastRound);

        System.out.printf("- Computer Score: %3d Last Round %3d *%n",
                computerScore, computerLastRound);

        System.out.println("**************************************");

        System.out.printf("- Turn %d of %d                        *%n",
                currentRound + 1, rounds);

        System.out.println("**************************************");

        System.out.println("  0   1   2   3   4   5   6   7");

        for (int row = 0; row < 8; row++) {

            System.out.println("---------------------------------");

            for (int col = 0; col < 8; col++) {
                System.out.print("| " + board[row][col] + " ");
            }

            System.out.println("| " + row);
        }

        System.out.println("---------------------------------");
    }

    public boolean playerMove(int row, int col) {

        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return false;
        }

        if (board[row][col] == ' ') {
            board[row][col] = 'X';
            return true;
        }

        return false;
    }

    public void computerMove() {

        Random random = new Random();

        while (true) {

            int row = random.nextInt(8);
            int col = random.nextInt(8);

            if (board[row][col] == ' ') {
                board[row][col] = 'C';
                return;
            }
        }
    }

    public int getPoints(int count) {

        if (count == 3) {
            return 4;
        } else if (count == 4) {
            return 6;
        } else if (count >= 5) {
            return 10;
        }

        return 0;
    }

    public void checkDirection(int row, int col, int rowChange,
                               int colChange) {

        char marker = board[row][col];

        if (marker == ' ') {
            return;
        }

        int previousRow = row - rowChange;
        int previousCol = col - colChange;

        // Do not start in the middle of an existing vector.
        if (previousRow >= 0 && previousRow < 8 &&
            previousCol >= 0 && previousCol < 8 &&
            board[previousRow][previousCol] == marker) {
            return;
        }

        int count = 1;

        int nextRow = row + rowChange;
        int nextCol = col + colChange;

        while (nextRow >= 0 && nextRow < 8 &&
               nextCol >= 0 && nextCol < 8 &&
               board[nextRow][nextCol] == marker) {

            count++;

            nextRow += rowChange;
            nextCol += colChange;
        }

        if (count >= 3) {

            String vectorKey = marker + ":" + row + ":" + col
                    + ":" + rowChange + ":" + colChange + ":" + count;

            if (!scoredVectors.contains(vectorKey)) {

                scoredVectors.add(vectorKey);

                int points = getPoints(count);

                if (marker == 'X') {
                    playerLastRound += points;
                } else {
                    computerLastRound += points;
                }
            }
        }
    }

    public void checkHorizontal() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                checkDirection(row, col, 0, 1);
            }
        }
    }

    public void checkVertical() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                checkDirection(row, col, 1, 0);
            }
        }
    }

    public void checkDiagonalRight() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                checkDirection(row, col, 1, 1);
            }
        }
    }

    public void checkDiagonalLeft() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                checkDirection(row, col, 1, -1);
            }
        }
    }

    public void checkVectors() {

        playerLastRound = 0;
        computerLastRound = 0;

        checkHorizontal();
        checkVertical();
        checkDiagonalRight();
        checkDiagonalLeft();

        playerScore += playerLastRound;
        computerScore += computerLastRound;
    }

    public void loadHighScore() {

        try {

            File file = new File("highscore.txt");

            if (file.exists()) {

                Scanner reader = new Scanner(file);

                highScoreName = reader.nextLine();
                highscore = reader.nextInt();

                reader.close();
            }

        } catch (Exception e) {
            System.out.println("Error loading high score.");
        }
    }

    public void saveHighScore() {

        try {

            PrintWriter writer =
                    new PrintWriter(new FileWriter("highscore.txt"));

            writer.println(highScoreName);
            writer.println(highscore);

            writer.close();

        } catch (Exception e) {
            System.out.println("Error saving high score.");
        }
    }

    public void updateHighScore(Scanner input, int finalScore) {

        if (finalScore > highscore) {

            System.out.println("New High Score!");

            System.out.print("Enter your name: ");

            highScoreName = input.nextLine();
            highscore = finalScore;

            saveHighScore();
        }

        System.out.println("High Score: " + highScoreName
                + " - " + highscore);
    }

    public void run() {

        loadHighScore();

        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of rounds (default 20): ");

        String answer = input.nextLine();

        if (!answer.trim().isEmpty()) {

            try {

                int chosenRounds = Integer.parseInt(answer);

                if (chosenRounds > 0) {
                    rounds = chosenRounds;
                } else {
                    System.out.println("Invalid number. Using 20 rounds.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Using 20 rounds.");
            }
        }

        while (currentRound < rounds) {

            displayBoard();

            boolean valid = false;

            while (!valid) {

                System.out.print("Player(X) Turn (Row Col): ");

                try {

                    int row = input.nextInt();
                    int col = input.nextInt();

                    valid = playerMove(row, col);

                    if (!valid) {
                        System.out.println("Invalid Move, Try Again.");
                    }

                } catch (Exception e) {

                    System.out.println("Invalid Move, Try Again.");

                    input.nextLine();
                }
            }

            computerMove();

            checkVectors();

            currentRound++;
        }

        // Display the final board and scores.
        System.out.println("\nFinal Board:");

        System.out.println("**************************************");
        System.out.println("- V E C T O R S                      *");
        System.out.println("**************************************");

        System.out.printf("- High Score: %-20s   *%n",
                highScoreName + " - " + highscore);

        System.out.println("**************************************");

        System.out.printf("- Player Score: %5d Last Round %3d *%n",
                playerScore, playerLastRound);

        System.out.printf("- Computer Score: %3d Last Round %3d *%n",
                computerScore, computerLastRound);

        System.out.println("**************************************");

        System.out.printf("- Turn %d of %d                        *%n",
                currentRound, rounds);

        System.out.println("**************************************");

        System.out.println("  0   1   2   3   4   5   6   7");

        for (int row = 0; row < 8; row++) {

            System.out.println("---------------------------------");

            for (int col = 0; col < 8; col++) {
                System.out.print("| " + board[row][col] + " ");
            }

            System.out.println("| " + row);
        }

        System.out.println("---------------------------------");

        int finalScore = playerScore - computerScore;

        System.out.println("\nGame Over!");

        System.out.println("Final Player Score: " + playerScore);
        System.out.println("Final Computer Score: " + computerScore);
        System.out.println("Final Score Difference: " + finalScore);

        if (finalScore > 0) {
            System.out.println("Player Wins!");
        } else if (finalScore < 0) {
            System.out.println("Computer Wins!");
        } else {
            System.out.println("It's a Tie!");
        }

        input.nextLine();

        updateHighScore(input, finalScore);
    }
}
