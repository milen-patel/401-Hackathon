import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
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



public class View extends JPanel implements ActionListener, ArcherGameObserver, MouseListener{
	private Model ArcherGameInstance;
	private JLabel playerOneScoreLabel;
	private JLabel playerTwoScoreLabel;
	private JLabel statusLabel;
	private JButton resetGameButton;
	
	private static final Color BACKGROUND_GAME_COLOR = new Color(36, 93, 0);
	
	public View(Model x) {
		//Encapsulate model instance variable
		ArcherGameInstance = x;
		//Assign ourselves as an observer of the model class
		ArcherGameInstance.addObserver(this);
		//Make it have a vertical box layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Set the default screen resolution
		this.setPreferredSize(new Dimension(115*3,243*3));
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
		
		
		//Add picture of the archer board
		String imagePath = "/Users/milenpatel/Desktop/board2.png";
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Graphics2D g = (Graphics2D) myPicture.getGraphics();
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLUE);
		g.drawOval(100, 100, 25, 25);
		g.drawOval(110, 110, 5, 5);
		
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		this.add(picLabel);
		
		
		//Add the reset button to the view
		resetGameButton = new JButton("Reset Game");
		resetGameButton.setActionCommand("Reset Button");
		resetGameButton.addActionListener(this);
		this.add(resetGameButton);
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Some Button Has Been Clicked");
		if (e.getActionCommand().equals("Reset Button")) {
			System.out.println("Reset button clicked");
			//TODO Make the game reset here
		}
		//TODO Stop making this be useless
		if (ArcherGameInstance.whoseTurn() == Model.Players.PLAYERTWO)
			ArcherGameInstance.changePlayerScore(Model.Players.PLAYERTWO, 4);
		else 
			ArcherGameInstance.changePlayerScore(Model.Players.PLAYERONE, 4);
		System.out.println("player score changed");
		System.out.println(ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE));
	}

	@Override
	public void playerScoreChanged() {
		playerOneScoreLabel.setText(String.format("<html><b>Player One Score:</b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERONE)));
		playerTwoScoreLabel.setText(String.format("<html><b>Player Two Score:</b> <font size=\"6\"><b>%s</b></font></html>", ArcherGameInstance.getPlayerScore(Model.Players.PLAYERTWO)));
	}
	
	@Override
	public void turnChanged() {
		if (ArcherGameInstance.whoseTurn()==Model.Players.PLAYERONE)
			statusLabel.setText(String.format("<html><b>Status:<font size=\"6\">%s </font></b></html>", "  Player 1's Turn"));
		if (ArcherGameInstance.whoseTurn()==Model.Players.PLAYERTWO)
			statusLabel.setText(String.format("<html><b>Status:<font size=\"6\">%s </font></b></html>", "  Player 2's Turn"));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX() + ", " + e.getY());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX() + ", " + e.getY());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
