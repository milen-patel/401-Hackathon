/* Interface for a class that observers ArcherBoardVisualizerWidget
 * In our program, View observes ArcherBoardVisualizerWidget
 */
public interface ArcherBoardObserver {
	/*
	 * Signals that a user has clicked on the board and that the click is a valid move
	 * numPoints represents the amount of points that should be rewarded to the player whose turn it is
	 */
	public void ArcherBoardClickEvent(int numPoints);
}
