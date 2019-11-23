import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
	//Enumeration to define the two players
	public enum Players { PLAYERONE, PLAYERTWO }

	//Define instance variables
	private int player1Score;
	private int player2Score;
	private int player1Turns = 5;
	private int player2Turns = 5;
	private double xWind;
	private double yWind;
	private Players currentTurn;
	private boolean isGameOver = false;
	
	//Keep a list of observers
	private List<ArcherGameObserver> observers;
	
	//Create a constructor
	public Model() {
		observers = new ArrayList<ArcherGameObserver>();

		resetGame();
	}
	
	
	//Returns the current score of a player
	public int getPlayerScore(Players x) {
		if (x == Players.PLAYERONE) {
			return player1Score;
		} else if (x == Players.PLAYERTWO) {
			return player2Score;
		} else {
			throw new RuntimeException("Bad input passed to getPlayerScore method");
		}
	}
	
	//Resets the game status
	public void resetGame() {
		System.out.println("Restarting game");
		generateWindValues();
		player1Score = 0;
		player2Score = 0;
		player1Turns = 5;
		player2Turns = 5;
		isGameOver = false;
		currentTurn = Players.PLAYERONE;
		notifyObservers("scoreChange");
		notifyObservers("TurnChange");
	}
	
	//Increases the score of a player by a specified amount
	public void changePlayerScore(Players targetPlayer, int amount) {
		generateWindValues();
		//Check for bad input
		if (targetPlayer == null || amount < 0) {
			throw new RuntimeException("Bad input passed to changePlayerScore method");
		}
		
		//If input is valid, then update the player score
		if (targetPlayer == Players.PLAYERONE) {
			player1Score += amount;
			player1Turns--;
		} else if (targetPlayer == Players.PLAYERTWO) {
			player2Score += amount;
			player2Turns--;
		}
		if (player1Turns == 0 && player2Turns == 0) {
			notifyObservers("gameOver");
			isGameOver = true;
			return;
		}
		//Now switch the turn
		if (currentTurn==Players.PLAYERONE) {
			currentTurn=Players.PLAYERTWO;
		} else if (currentTurn==Players.PLAYERTWO) {
			currentTurn=Players.PLAYERONE;
		}
			
		//Notify observers
		notifyObservers("scoreChange");
		notifyObservers("TurnChange");
	}
	
	//Returns which player currently has a turn
	public Players whoseTurn() {
		return currentTurn;
	}
	
	public void generateWindValues() {
	    Random r = new Random(); 
	    xWind = -50.0 + r.nextDouble() * 100.0; 
	    yWind = -50.0 + r.nextDouble() * 100.0; 
		notifyObservers("WindChange");
	}
	
	public double getXWind() {
		return xWind;
	}
	
	public double getYWind() {
		return yWind;
	}
	
	
	//Handle Observer Methods
	public void addObserver(ArcherGameObserver O) {
		observers.add(O);
	}
	public void removeObserver(ArcherGameObserver O) {
		observers.remove(O);
	}
	public void notifyObservers(String event) {
		if (event=="scoreChange") {
			for (ArcherGameObserver o : observers) {
				o.playerScoreChanged();
			}
		} else if (event=="TurnChange") {
			for (ArcherGameObserver o : observers) {
				o.turnChanged();
			}
		} else if (event=="WindChange") {
			for (ArcherGameObserver o : observers) {
				o.windValuesUpdated();
			}
		} else if (event=="gameOver") {
			for (ArcherGameObserver o : observers) {
				if (player1Score > player2Score) {
					o.gameOver(Players.PLAYERONE);
				} else {
					o.gameOver(Players.PLAYERTWO);
				}
			}
		}
	}
	
	public int getPlayer1Turns() {
		return player1Turns;
	}
	public int getPlayer2Turns() {
		return player2Turns;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
}