import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	private JButton clearBoardButton;
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
		this.setPreferredSize(new Dimension(115*3,600));
		//Set Background Color
		this.setBackground(BACKGROUND_GAME_COLOR);
		
		
		//Add JLabels to the UI with score
		playerOneScoreLabel = new JLabel(String.format("<html><b>Player One Score:</b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE)));
		playerTwoScoreLabel = new JLabel(String.format("<html><b>Player Two Score:</b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO)));
		playerOneScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		playerTwoScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(playerOneScoreLabel);
		this.add(playerTwoScoreLabel);
		
		//Add status label to the UI
		statusLabel = new JLabel(String.format("<html><b>Status:<font size=\"6\">%s </font></b></html>", "  Player 1's Turn"));
		statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(statusLabel);
				
		
		//Add wind labels to the UI
		xWindLabel = new JLabel(String.format("<html><b>Horizontal Wind: <font size=\"6\">%s </font></b></html>", String.format("%.5g%n", ArcherGameInstance.getXWind())));
		yWindLabel = new JLabel(String.format("<html><b>Vertical Wind: <font size=\"6\">%s </font></b></html>", String.format("%.5g%n", ArcherGameInstance.getYWind())));
		xWindLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		yWindLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		this.add(xWindLabel);
		this.add(yWindLabel);
		
		//Add the reset button to the view
		resetGameButton = new JButton("Reset Game");
		resetGameButton.setActionCommand("Reset Button");
		resetGameButton.addActionListener(this);
		this.add(resetGameButton);		

		//Add clear game button to the view
		clearBoardButton = new JButton("Clear Board");
		clearBoardButton.setActionCommand("Clear Button");
		clearBoardButton.addActionListener(this);
		this.add(clearBoardButton);
		
		//Add visual component of board
		boardView = new ArcherBoardVisualizerWidget(ArcherGameInstance);
		this.add(boardView);
		boardView.addObserver(this);
			
		}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Some Button Has Been Clicked");
		if (e.getActionCommand().equals("Reset Button")) {
			System.out.println("Reset button clicked");
			ArcherGameInstance.resetGame();
			clearBoard();
		}
		
		if (e.getActionCommand().equals("Clear Button")) {
			clearBoard();
		}
		
	}
	
	public void clearBoard() {
		System.out.println("Clearing board");
		this.remove(boardView);
		boardView = new ArcherBoardVisualizerWidget(ArcherGameInstance);
		this.add(boardView);
		boardView.revalidate();
		boardView.repaint();
	}

	@Override
	public void playerScoreChanged() {
		if (ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE) > ArcherGameInstance.getPlayerScore((Model.Players.PLAYERTWO))) {
			playerOneScoreLabel.setText(String.format("<html><b>Player One Score:</b> <font size=\"6\"><b><font color='green'>%s</font></b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE)));
			playerTwoScoreLabel.setText(String.format("<html><b>Player Two Score:</b> <font size=\"6\"><b><font color='red'>%s</font></b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO)));
		} else if (ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE) < ArcherGameInstance.getPlayerScore((Model.Players.PLAYERTWO))) {
			playerOneScoreLabel.setText(String.format("<html><b>Player One Score:</b> <font size=\"6\"><b><font color='red'>%s</font></b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE)));
			playerTwoScoreLabel.setText(String.format("<html><b>Player Two Score:</b> <font size=\"6\"><b><font color='green'>%s</font></b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO)));
		
		} else {
			playerOneScoreLabel.setText(String.format("<html><b>Player One Score:</b> <font size=\"6\"><b><font color='yellow'>%s</font></b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE)));
			playerTwoScoreLabel.setText(String.format("<html><b>Player Two Score:</b> <font size=\"6\"><b><font color='yellow'>%s</font></b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO)));
		}

	}
	
	@Override
	public void turnChanged() {
				
		if (ArcherGameInstance.whoseTurn()==Model.Players.PLAYERONE)
			statusLabel.setText(String.format("<html><b>Status:<font size=\"6\">%s </font></b></html>", "  Player 1's Turn"));
		if (ArcherGameInstance.whoseTurn()==Model.Players.PLAYERTWO)
			statusLabel.setText(String.format("<html><b>Status:<font size=\"6\">%s </font></b></html>", "  Player 2's Turn"));
	}

	@Override
	public void ArcherBoardClickEvent(int numPoints) {
		ArcherGameInstance.changePlayerScore(ArcherGameInstance.whoseTurn(), numPoints);
	}

	@Override
	public void windValuesUpdated() {
		if (Math.abs(ArcherGameInstance.getXWind()) > 30) {
			xWindLabel.setText(String.format("<html><b>Horizontal Wind:<font size=\"6\"><font color='red'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getXWind())));
		} else if (Math.abs(ArcherGameInstance.getXWind()) < 5) { 
			xWindLabel.setText(String.format("<html><b>Horizontal Wind:<font size=\"6\"><font color='green'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getXWind())));
		} else {
			xWindLabel.setText(String.format("<html><b>Horizontal Wind:<font size=\"6\"><font color='yellow'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getXWind())));
		}
		
		if (Math.abs(ArcherGameInstance.getYWind()) > 30) {
			yWindLabel.setText(String.format("<html><b>Vertical Wind:<font size=\"6\"><font color='red'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getYWind())));	
		} else if (Math.abs(ArcherGameInstance.getYWind()) < 5) {
			yWindLabel.setText(String.format("<html><b>Vertical Wind:<font size=\"6\"><font color='green'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getYWind())));	
		} else {
			yWindLabel.setText(String.format("<html><b>Vertical Wind:<font size=\"6\"><font color='yellow'>%s</font> </font></b></html>",  String.format("%.5g%n", ArcherGameInstance.getYWind())));	
		}
	}

	
}
