package game;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Vectors {

	private int rounds = 20;
	private char[][] board;
	private int playerScore;
	private int computerScore;
	int index;
	private int currentRound = 0;
	private String highScoreName = "None";
	private int highscore = 0;
	
	
	
	public Vectors() {
		board = new char[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = ' ';
			}
		}
	}
	
	
	
	public void displayBoard() {
	
	System.out.println("  0   1   2   3   4   5   6   7");
		for(int i = 0; i < 8; i++) {
			
	System.out.println("---------------------------------");
			for(int j = 0; j < 8; j++) {
				System.out.print("| " + board[i][j] + " ");
			}
			System.out.println("| " + i);
		}
	System.out.println("---------------------------------");	
	
	System.out.println("Player Score: " + playerScore);
	System.out.println("Computer Score " + computerScore);
	}
	
	
	
	public boolean playerMove(int row, int col) {
		
		if(row < 0 || row >= 8 || col < 0 || col >= 8) {
			return false;
		}
		
		if(board[row][col] == ' ') {
			board[row][col] = 'X';
			return true;
		}
		return false;
	}
	
	
	public void computerMove() {
		Random random = new Random();
		boolean valid = false;
		
		while(!valid) {
			int row = random.nextInt(8);
			int col = random.nextInt(8);
			
			if(board[row][col] == ' ') {
				board[row][col] = 'C';
				valid = true;
			}
		}
	}
	
	
	
	public int getPoints(int count) {
		int points = 0;
		
		
		while (count >= 5) {
			points += 10;
			count -= 5;
			
		}if (count == 4) {
			points += 6;
		}else if (count == 3) {
			points += 4;
		}
		return points;
	}
	
	
	
	public void checkHorizontal() {
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				
				if(board[row][col] == ' ') {
					continue;
				}
				
				if(col > 0 && board[row][col] == board[row][col - 1]) {
					continue;
				}
				
				int count = 1;
				
				while(col + count < 8 && board[row][col] == board[row][col + count]) {
					count++;
				}
				
				if(count >= 3) {
					addVectorPoints(board[row][col], count);
				}
			}
		}
	}
	
	public void checkVertical() {
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				
				if(board[row][col] == ' ') {
					continue;
				}
				
				if(row > 0 && board[row][col] == board[row - 1][col]) {
					continue;
				}
				
				int count = 1;
				
				while(row + count < 8 && board[row][col] == board[row + count][col]) {
					count++;
				}
				
				if(count >= 3) {
					addVectorPoints(board[row][col], count);
				}
			}
		}
	}
	
	public void checkDiagonalRight() {
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				
				if(board[row][col] == ' ') {
					continue;
				}
				
				if(row > 0 && col > 0 && board[row][col] == board[row - 1][col - 1]) {
					continue;
				}
				
				int count = 1;
				
				while(row + count < 8 && col + count < 8 && board[row][col] == board[row][col + count]) {
					count++;
				}
				
				if(count >= 3) {
					addVectorPoints(board[row][col], count);
				}
			}
		}
	}
	
	public void checkDiagonalLeft() {
	for(int row = 0; row < 8; row++) {
		for(int col = 0; col < 8; col++) {
			
			if(board[row][col] == ' ') {
				continue;
			}
			
			if(col > 0 && board[row][col] == board[row - 1][col + 1]) {
				continue;
			}
			
			int count = 1;
			
			while(row + count < 8 && col - count < 8 && board[row][col] == board[row][col - count]) {
				count++;
			}
			
			if(count >= 3) {
				addVectorPoints(board[row][col], count);
			}
		}
	}
}
	
	public void addVectorPoints(char marker, int count) {
		if(marker == 'X') {
			playerScore += getPoints(count);
		}else if (marker == 'C') {
			computerScore += getPoints(count);
		}
	}
	
	public void checkVectors() {
		playerScore = 0;
		computerScore = 0;
		currentRound++;
		
		checkHorizontal();
		checkVertical();
		checkDiagonalRight();
		checkDiagonalLeft();
	}
	public void saveHighScore() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter("highscore.txt"));
			
			writer.println(highScoreName);
			writer.println(highscore);
			
			writer.close();
			
		}catch (Exception e) {
			System.out.println("Error saving high score.");
		}
	}
	
	public void loadHighScore() {
		try {
			File file = new File("highscore.txt");
			
			if(file.exists()) {
				Scanner reader = new Scanner(file);
				
				highScoreName = reader.nextLine();
				highscore = reader.nextInt();
				
				reader.close();
			}
				
				
		}catch (Exception e) {
			System.out.println("Error loading high Score.");
		}
	}
		
		public void updateHighScore() {
			Scanner input = new Scanner(System.in);
			
			if(playerScore > highscore) {
				System.out.println("New High Score!");
				System.out.print("Enter your Name: ");
				
				highScoreName = input.nextLine();
				highscore = playerScore;
				
				saveHighScore();
			}
			
			System.out.println("High Score: " + highscore + " - " + highScoreName);
		}
		

	public void run() {
		loadHighScore();
		Scanner input = new Scanner(System.in);
		
		while(currentRound < rounds) {
			displayBoard();
			
			boolean valid = false;
			
			while (!valid) {
				System.out.print("Player (X) Turn (Row Col): ");
			
				int row = input.nextInt();
				int col = input.nextInt();
				
				valid = playerMove(row, col);
				
				if(!valid) {
					System.out.println("Invalid Move, Try Again.");
				}
			}
			
			computerMove();
			
			checkVectors();
			
			currentRound++;
			
			System.out.println("Game Over!");
			System.out.println("Final Player Score: " + playerScore);
			System.out.println("Final Computer Score: " + computerScore);
			
			if(playerScore > computerScore) {
				System.out.println("Player Wins!");
			} else if (computerScore > playerScore) {
				System.out.println("Computer Wins!");
			} else {
				System.out.println("It's a Tie!");
			}
		}
		updateHighScore();
	}
}
