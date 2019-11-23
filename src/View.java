import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class View extends JPanel implements ActionListener, ArcherGameObserver, ArcherBoardObserver{
	private Model ArcherGameInstance;
	private JLabel playerOneScoreLabel;
	private JLabel playerTwoScoreLabel;
	private JLabel statusLabel;
	private JLabel xWindLabel;
	private JLabel yWindLabel;
	private JButton resetGameButton;
	private ArcherBoardVisualizerWidget boardView;

	public static final Color BACKGROUND_GAME_COLOR = new Color(177, 211, 227);
	
	public View(Model x) {
		//Encapsulate model instance variable
		ArcherGameInstance = x;
		//Assign ourselves as an observer of the model class
		ArcherGameInstance.addObserver(this);
		//Make it have a vertical box layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Set the default screen resolution
		this.setPreferredSize(new Dimension(346,564));
		//Set Background Color
		this.setBackground(BACKGROUND_GAME_COLOR);
		
		//Add status label to the UI
		statusLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Status:<font size=\"6\">%s </font></b></html>", "  Player 1's Turn"));
		statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(statusLabel);
		
		//Add JLabels to the UI with score
		playerOneScoreLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player One Score:</b> <font size=\"6\"><b>%s</b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE), ArcherGameInstance.getPlayer1Turns()));
		playerTwoScoreLabel = new JLabel(String.format("<html><b>&nbsp;&nbsp;&nbsp;Player Two Score:</b> <font size=\"6\"><b>%s</b></font> <b>Turns: </b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO), ArcherGameInstance.getPlayer2Turns()));
		playerOneScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		playerTwoScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(playerOneScoreLabel);
		this.add(playerTwoScoreLabel);
		
				
		
		//Add the reset button to the view
		resetGameButton = new JButton("<html>&nbsp;&nbsp;&nbsp;Reset Game</html>");
		resetGameButton.setActionCommand("Reset Button");
		resetGameButton.addActionListener(this);
		this.add(resetGameButton);
		//Add visual component of board
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
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Some Button Has Been Clicked");
		if (e.getActionCommand().equals("Reset Button")) {
			System.out.println("Reset button clicked");
			ArcherGameInstance.resetGame();
			//clearBoard();
		}
		
	}
	
	public void clearBoard() {
		System.out.println("Clearing board");
		this.remove(boardView);
		ArcherGameInstance = new Model();
		boardView = new ArcherBoardVisualizerWidget(ArcherGameInstance);
		this.add(boardView);
		boardView.validate();
		boardView.repaint();
	}

	@Override
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
	public void turnChanged() {
				
		if (ArcherGameInstance.whoseTurn()==Model.Players.PLAYERONE)
			statusLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Status:<font size=\"6\">%s </font></b></html>", "  Player 1's Turn"));
		if (ArcherGameInstance.whoseTurn()==Model.Players.PLAYERTWO)
			statusLabel.setText(String.format("<html><b>&nbsp;&nbsp;&nbsp;Status:<font size=\"6\">%s </font></b></html>", "  Player 2's Turn"));
	}

	@Override
	public void ArcherBoardClickEvent(int numPoints) {
		ArcherGameInstance.changePlayerScore(ArcherGameInstance.whoseTurn(), numPoints);
	}

	@Override
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