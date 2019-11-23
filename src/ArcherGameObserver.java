/* Observer interface for 'Model' class.
 * An observer of the model class seeks state information about the current game of archer
 * In this project, View observes model to know when to update its' UI components
 */
public interface ArcherGameObserver {
	
	/* Signals that one players' score has been changed */
	public void playerScoreChanged();
	
	/* Signals that the players' turn has changed */
	public void turnChanged();
	
	/* Signals that new wind values have been generated */
	public void windValuesUpdated();
	
	/* Signals that the game has been won by 'winner' */
	public void gameOver(Model.Players winner);

}
