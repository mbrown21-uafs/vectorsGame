package game;

import java.util.Random;
import java.util.Scanner;

public class Vectors {

	private char[][] board;
	private int playerScore;
	private int computerScore;
	int index;
	
	
	
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
		
		
		
		
		
		return points;
	}
	
	
	
	public void checkHorizontal() {
		for(int row = 0; row < 8; row++) {
			int count = 1; 
			
			for(int col = 0; col < 8; col++) {
				if(col > 0 && board[row][col] != ' ' &&
				board[row][col] == board[row][col - 1]) {
					
					count++;
				}else {
					if(count >= 3) {
						if (board[row][col - 1] == 'X') {
							playerScore += getPoints(count);
						}else {
							computerScore += getPoints(count);
						
					}
				}
				count = 1;	
			
			
		}
	}
			if (count >= 3) {
				if(board[row][7] == 'X') {
					playerScore += getPoints(count);
				} else {
					computerScore += getPoints(count);
				}
			}
		}
	}
	
	
	
	public void run() {
		Scanner input = new Scanner(System.in);
		
		displayBoard();
		
		int row;
		int col;
		boolean valid;
		
	while (true) {
		displayBoard();
		
		valid = false;
		
		while (!valid) {
			System.out.print("Player(X) Turn (Row Col): ");
			row = input.nextInt();
			col = input.nextInt();
			
			valid = playerMove(row, col);
			
			if(!valid) {
				System.out.println("Invalid Move, Try Again.");
			}
		}
		
		computerMove();
		
		}	
	}
}
