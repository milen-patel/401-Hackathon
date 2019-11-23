import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ArcherBoardButtonWidget extends JPanel{
	private JButton resetGameButton;
	private JButton clearBoardButton;
	public ArcherBoardButtonWidget(ActionListener x) {
	
		this.setLayout(new GridLayout(1,2));
		this.setBackground(View.BACKGROUND_GAME_COLOR);
		//this.setPreferredSize(new Dimension(115*3, 100));
		//Add the reset button to the view
		resetGameButton = new JButton("Reset Game");
		resetGameButton.setActionCommand("Reset Button");
		resetGameButton.addActionListener(x);
		this.add(resetGameButton);		

		//Add clear game button to the view
		clearBoardButton = new JButton("Clear Board");
		clearBoardButton.setActionCommand("Clear Button");
		clearBoardButton.addActionListener(x);
		this.add(clearBoardButton);
	}
	
	public Dimension getPrefferedSize() {
		return(new Dimension(115*3, 100));

	}

}
