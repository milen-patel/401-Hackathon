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

public class ArcherBoardVisualizerWidget extends JPanel implements MouseListener{
	
	//Define instance variables
	private JLabel picLabel;
	BufferedImage myPicture = null;
	Graphics2D g;
	Model game;
	
	//Keep a list of observers
	List<ArcherBoardObserver> observers;
	
	public ArcherBoardVisualizerWidget(Model game) {
		this.game = game;
		observers = new ArrayList<ArcherBoardObserver>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(View.BACKGROUND_GAME_COLOR);
		//Add picture of the archer board
				String imagePath = "board2.png";
				try {
					myPicture = ImageIO.read(new File(imagePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g = (Graphics2D) myPicture.getGraphics();
				
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.BLUE);
				
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
		if (game.getCurrentTurn() == Model.Players.PLAYERONE) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.GREEN);
		}

		g.drawOval((int)(e.getX()-11+game.getXWind()), (int)(e.getY()-8-game.getYWind()), 25, 25);
		g.drawOval((int)(e.getX()-1+game.getXWind()), (int)(e.getY()+2-game.getYWind()), 5, 5);
		
		this.repaint();
	
		double centerCoor = picLabel.getWidth()/2.0;
		double xDis = Math.abs(centerCoor - Math.abs(e.getX()+game.getXWind()));
		double yDis = Math.abs(centerCoor - Math.abs(e.getY()-game.getYWind()));
		double hypot = Math.sqrt(xDis*xDis + yDis*yDis);
		
		int pointValue = (int)(200-hypot);
		if (pointValue <= 26) {pointValue = 0; } //If we are touching/outside of the edge, no points
		
		
		System.out.println("Point Value: " +pointValue);

		
		notifyObservers(pointValue);
		
		
		
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//Add observer methods
	public void addObserver(ArcherBoardObserver e) {
		observers.add(e);
	}
	public void removeObserver(ArcherBoardObserver e) {
		observers.remove(e);
	}
	public void notifyObservers(int numPoints) {
		for (ArcherBoardObserver e: observers) {
			e.ArcherBoardClickEvent(numPoints);
		}
	}
	
}
