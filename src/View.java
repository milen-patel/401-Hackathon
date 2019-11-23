import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* This class models the data of the game itself by encapsulating an instance of the 'Model' class
 * The class is an ActionListener so that it can reset the game when the reset button is clicked
 * The class is an ArcherGameObserver because we observe the state changes of the Model class so that the UI can be updated
 * The class is an ArcherBoard Observer so we can know when the user has fired an arrow at the board and handle it as needed
 */
public class View extends JPanel implements ActionListener, ArcherGameObserver, ArcherBoardObserver{
	//Encapsulate the instance of the game that the UI is modeling
	private Model ArcherGameInstance;
	
	//Create labels to model the state data of 'ArcherGameInstance'
	private JLabel playerOneScoreLabel;
	private JLabel playerTwoScoreLabel;
	private JLabel statusLabel;
	private JLabel xWindLabel;
	private JLabel yWindLabel;
	
	//Create a button so the user can reset the game
	private JButton resetGameButton;
	
	//Create a widget to model the Archer board itself
	private ArcherBoardVisualizerWidget boardView;

	/* Constant for declaring the background color of the entire UI
	 * Other two constants are for declaring the default dimensions of the board
	 * Kept public so that ArcherBoardVisualizerWidget can have the same background as the rest of the program
	 */
	public static final Color BACKGROUND_GAME_COLOR = new Color(177, 211, 227);
	private static final int DEFAULT_X_LENGTH = 346;
	private static final int DEFAULT_Y_LENGTH = 546;
	
	//Constructor for the UI that takes a model of the game 'x'
	public View(Model x) {
		//Encapsulate model instance variable
		ArcherGameInstance = x;
		
		//Assign ourselves as an observer of the model class
		ArcherGameInstance.addObserver(this);
		
		//Make it have a vertical box layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Set the default screen resolution
		this.setPreferredSize(new Dimension(DEFAULT_X_LENGTH,DEFAULT_Y_LENGTH));
		
		//Set Background Color
		this.setBackground(BACKGROUND_GAME_COLOR);
		
		//Add status label to the UI, which at the start of the game, indicates that Player one has the turn
		statusLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Status:<font size=\"6\">%s </font></b></html>", "Player 1's Turn"));
		statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(statusLabel);
		
		//Add JLabels to the UI with score
		playerOneScoreLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player One Score:</b> <font size=\"6\"><b>%s</b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE), ArcherGameInstance.getPlayer1Turns()));
		playerTwoScoreLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player Two Score:</b> <font size=\"6\"><b>%s</b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO), ArcherGameInstance.getPlayer2Turns()));
		playerOneScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		playerTwoScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(playerOneScoreLabel);
		this.add(playerTwoScoreLabel);
		
		//Add the reset button to the view, add ourselves as an observer
		resetGameButton = new JButton("<html>&nbsp;&nbsp;&nbsp;Reset Game</html>");
		resetGameButton.setActionCommand("Reset Button");
		resetGameButton.addActionListener(this);
		this.add(resetGameButton);
		
		//Add visual component of board, add ourselves as an observer
		boardView = new ArcherBoardVisualizerWidget(ArcherGameInstance);
		this.add(boardView);
		boardView.addObserver(this);
			
		//Add wind labels to the UI
		xWindLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Horizontal Wind: &nbsp;&nbsp;&nbsp;<font size=\"6\">%s </font></b></html>", String.format("%.5g%n", ArcherGameInstance.getXWind())));
		yWindLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Vertical Wind: &nbsp;&nbsp;&nbsp;<font size=\"6\">%s </font></b></html>", String.format("%.5g%n", ArcherGameInstance.getYWind())));
		xWindLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		yWindLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(xWindLabel);
		this.add(yWindLabel);		
			
		}
	
	/*
	 * Event handler for when the reset button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		//Validate that the reset button was clicked
		if (e.getActionCommand().equals("Reset Button")) {
			ArcherGameInstance.resetGame();
			//clearBoard();
		}
	
	}
	
	/*
	 * ClearBoard: ideally removes all the markers from the board
	 * TODO: Fix implementation of clear board
	 */
	public void clearBoard() {
		//Remove ourselves from the view
		this.remove(boardView);
		
		//Create a new game and new board model
		ArcherGameInstance = new Model();
		boardView = new ArcherBoardVisualizerWidget(ArcherGameInstance);
		
		//Add ourselves back to the view and update the UI
		this.add(boardView);
		boardView.validate();
		boardView.repaint();
	}

	@Override
	/*
	 * Handler for when the player score is changed, View observers Model
	 * Updates the player scores on the screen with appropriate color coding
	 * The winning player is highlighted green, the losing player is highlighted red
	 * If there is a tie, then both players' scores are highlighted yellow
	 */
	public void playerScoreChanged() {
		if (ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE) > ArcherGameInstance.getPlayerScore((Model.Players.PLAYERTWO))) {
			playerOneScoreLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player One Score:</b> <font size=\"6\"><b><font color='green'>%s</font></b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE), ArcherGameInstance.getPlayer1Turns()));
			playerTwoScoreLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player Two Score:</b> <font size=\"6\"><b><font color='red'>%s</font></b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO), ArcherGameInstance.getPlayer2Turns()));	
		} else if (ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE) < ArcherGameInstance.getPlayerScore((Model.Players.PLAYERTWO))) {
			playerOneScoreLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player One Score:</b> <font size=\"6\"><b><font color='red'>%s</font></b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE), ArcherGameInstance.getPlayer1Turns()));
			playerTwoScoreLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player Two Score:</b> <font size=\"6\"><b><font color='green'>%s</font></b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO), ArcherGameInstance.getPlayer2Turns()));
		} else {
			playerOneScoreLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player One Score:</b> <font size=\"6\"><b><font color='yellow'>%s</font></b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE), ArcherGameInstance.getPlayer1Turns()));
			playerTwoScoreLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player Two Score:</b> <font size=\"6\"><b><font color='yellow'>%s</font></b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO), ArcherGameInstance.getPlayer2Turns()));
		}

	}
	
	@Override
	/*
	 * Handler for when the players turn is changed
	 * This method ensures that the UI is updated to show whose turn it is by updating statusLabel
	 */
	public void turnChanged() {
		if (ArcherGameInstance.getCurrentTurn()==Model.Players.PLAYERONE)
			statusLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Status:<font size=\"6\">%s </font></b></html>", "  Player 1's Turn"));
		if (ArcherGameInstance.getCurrentTurn()==Model.Players.PLAYERTWO)
			statusLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Status:<font size=\"6\">%s </font></b></html>", "  Player 2's Turn"));
	}

	@Override
	/*
	 * Handler for when a position on the board is clicked
	 * Recall that View observers ArcherBoardVisualizerWidget
	 * This method delegates the event handling to the model
	 */
	public void ArcherBoardClickEvent(int numPoints) {
		ArcherGameInstance.changePlayerScore(ArcherGameInstance.getCurrentTurn(), numPoints);
	}

	@Override
	/* Handler for when wind values are updated
	 * Recall that View observes Model
	 * If wind is > 30, the value will be displayed in red
	 * If wind is < 5, the value will be displayed in green
	 * For all other values of wind, it will be displayed yellow
	 */
	public void windValuesUpdated() {
		if (Math.abs(ArcherGameInstance.getXWind()) > 30) {
			xWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Horizontal Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='red'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getXWind())));
		} else if (Math.abs(ArcherGameInstance.getXWind()) < 5) { 
			xWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Horizontal Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='green'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getXWind())));
		} else {
			xWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Horizontal Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='yellow'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getXWind())));
		}
		
		if (Math.abs(ArcherGameInstance.getYWind()) > 30) {
			yWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Vertical Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='red'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getYWind())));	
		} else if (Math.abs(ArcherGameInstance.getYWind()) < 5) {
			yWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Vertical Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='green'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getYWind())));	
		} else {
			yWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Vertical Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='yellow'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getYWind())));	
		}
	}

	@Override
	/*
	 * Handler for when the game is over
	 * This removes the coloring from the wind labels and updates the status label to reflect the current winner
	 */
	public void gameOver(Model.Players winner) {
		playerScoreChanged();
		xWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Horizontal Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\">%s</font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getXWind())));
		yWindLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Vertical Wind:&nbsp;&nbsp;&nbsp;<font size=\"6\">%s</font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getYWind())));	
		
		if (winner == Model.Players.PLAYERONE) {
			statusLabel.setText("<html><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='green'>Player 1 Wins!</font></font></b></html>");
		} else {
			statusLabel.setText("<html><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"6\"><font color='green'>Player 2 Wins!</font></font></b></html>");
		}
	}	
}