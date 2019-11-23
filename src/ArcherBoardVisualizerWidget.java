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
	
	//Keep a list of observers
	List<ArcherBoardObserver> observers;
	
	public ArcherBoardVisualizerWidget() {
		observers = new ArrayList<ArcherBoardObserver>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(View.BACKGROUND_GAME_COLOR);
		//Add picture of the archer board
				String imagePath = "/Users/milenpatel/Desktop/board2.png";
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
		// TODO Auto-generated method stub
		System.out.println(e.getX() + ", " + e.getY());
		g.drawOval(e.getX()-11, e.getY()-8, 25, 25);
		g.drawOval(e.getX()-1, e.getY()+2, 5, 5);
		this.repaint();
		//System.out.println(picLabel.getWidth());
		//System.out.println(picLabel.getHeight());
		double centerCoor = picLabel.getWidth()/2.0;
		//Calculate the point value:
		double xDis = Math.abs(centerCoor - e.getX());
		double yDis = Math.abs(centerCoor - e.getY());
		double hypot = Math.sqrt(xDis*xDis + yDis*yDis);
		int pointValue = (int)(200-hypot);
		if (pointValue <= 26) {pointValue = 0; } //If we are touching/outside of the edge, no points
		System.out.println(pointValue);
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
