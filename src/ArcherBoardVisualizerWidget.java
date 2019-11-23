import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This class is a widget that displays an archer board and can be interacted with
 * The class is a MouseListener because we need to know where the mouse is when it is clicked
 */
public class ArcherBoardVisualizerWidget extends JPanel implements MouseListener{
	
	//The image of the board will be stored on a JLabel
	private JLabel picLabel;
	//We will use a BufferedImage to get the picture into our program
	BufferedImage myPicture = null;
	//We will use Graphics2D to draw over the image
	Graphics2D graphicsTool;
	//We will encapsulate information about the game to validate that the game isn't over when a spot is clicked
	Model game;
	
	//Keep a list of observers
	List<ArcherBoardObserver> observers;
	
	public ArcherBoardVisualizerWidget(Model game) {
		//Encapsulate the game
		this.game = game;
		
		//Instantiate an ArrayList to hold a list of observers
		observers = new ArrayList<ArcherBoardObserver>();
		
		//Set the layout and background for our widget
		//For background, use the static public field to maintain color consistency
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(View.BACKGROUND_GAME_COLOR);
		
		//Add picture of the archer board
		String imagePath = "board2.png";
		try {
			myPicture = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Set up the graphicsTool variable so we can draw circles over our image
		graphicsTool = (Graphics2D) myPicture.getGraphics();		
		graphicsTool.setStroke(new BasicStroke(3));
		graphicsTool.setColor(Color.BLUE);
				
		//At the image to the JLabel so the user can see it
		picLabel = new JLabel(new ImageIcon(myPicture));
		this.add(picLabel);
		
		//Register ourselves as a MouseListener for the board visual area
		picLabel.addMouseListener(this);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//If the game is over then do nothing
		if (game.isGameOver()) {
			return;
		}
		
		//Adjust the color of the circles based on whose turn it is
		//Player one gets Blue circles and Player two gets Green circles
		if (game.getCurrentTurn() == Model.Players.PLAYERONE) {
			graphicsTool.setColor(Color.BLUE);
		} else {
			graphicsTool.setColor(Color.GREEN);
		}

		//Draw ovals based on where the user clicks and the wind lies
		graphicsTool.drawOval((int)(e.getX()-11+game.getXWind()), (int)(e.getY()-8-game.getYWind()), 25, 25);
		graphicsTool.drawOval((int)(e.getX()-1+game.getXWind()), (int)(e.getY()+2-game.getYWind()), 5, 5);
		
		//Repaint the screen so the Ovals can be seen
		this.repaint();
	
		//To calculate score, we measure our distance from the center of the picture
		//We need an X distance and a Y distance to compute this desired value, 'hypot'
		double centerCoor = picLabel.getWidth()/2.0;
		double xDis = Math.abs(centerCoor - Math.abs(e.getX()+game.getXWind()));
		double yDis = Math.abs(centerCoor - Math.abs(e.getY()-game.getYWind()));
		double hypot = Math.sqrt(xDis*xDis + yDis*yDis);
		
		//Assign a point value based on the proximity to the center
		int pointValue = (int)(200-hypot);
		
		//If we are touching/outside of the edge, no points
		if (pointValue <= 26) {
			pointValue = 0; 
		} 
		
		//Notify observers that a valid move has been made and pass along the amount of points that the move generated
		notifyObservers(pointValue);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	//Add observer method
	public void addObserver(ArcherBoardObserver e) {
		observers.add(e);
	}
	
	//Remove observer method
	public void removeObserver(ArcherBoardObserver e) {
		observers.remove(e);
	}
	
	//Notify observer method
	public void notifyObservers(int numPoints) {
		for (ArcherBoardObserver e: observers) {
			e.ArcherBoardClickEvent(numPoints);
		}
	}
	
}