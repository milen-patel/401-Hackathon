import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
	//Enumeration to define the two players
	public enum Players { PLAYERONE, PLAYERTWO }

	//Define instance variables
	private int player1Score;
	private int player2Score;
	private double xWind;
	private double yWind;
	private Players currentTurn;
	
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
		player1Score = 0;
		player2Score = 0;
		currentTurn = Players.PLAYERONE;
		notifyObservers("scoreChange");
	}
	
	//Increases the score of a player by a specified amount
	public void changePlayerScore(Players targetPlayer, int amount) {
		//Check for bad input
		if (targetPlayer == null || amount < 0) {
			throw new RuntimeException("Bad input passed to changePlayerScore method");
		}
		
		//If input is valid, then update the player score
		if (targetPlayer == Players.PLAYERONE) {
			player1Score += amount;
		} else if (targetPlayer == Players.PLAYERTWO) {
			player2Score += amount;
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
	    xWind = -10.0 + r.nextDouble() * 20.0; 
	    yWind = -10.0 + r.nextDouble() * 20.0; 
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
		}
	}
	
}
