public interface ArcherGameObserver {
	public void playerScoreChanged();
	public void turnChanged();
	public void windValuesUpdated();
	public void gameOver(Model.Players winner);
}
