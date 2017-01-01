package server;

import java.io.IOException;
import java.util.List;

public interface IPlayerListener {

	/* (non-Javadoc)
	 * @see server.IPlayerListener#run()
	 */
	void run();

	/* (non-Javadoc)
	 * @see server.IPlayerListener#firstContact(java.util.List)
	 */
	void firstContact(List<Game> games);

	/* (non-Javadoc)
	 * @see server.IPlayerListener#OutMessege(java.lang.String)
	 */
	void OutMessege(String back);

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getOpponent()
	 */
	IPlayerListener getOpponent();

	/* (non-Javadoc)
	 * @see server.IPlayerListener#setOpponent(server.PlayerListener)
	 */
	void setOpponent(IPlayerListener opponent2);

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getGame()
	 */
	Game getGame();

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getX()
	 */
	int getX();

	/* (non-Javadoc)
	 * @see server.IPlayerListener#close()
	 */
	void close() throws IOException;

	/* (non-Javadoc)
	 * @see server.IPlayerListener#getMyPlayer()
	 */
	IPlayerS getMyPlayer();

	void objectToClient(Object board);

	void start();
	void setX(int x);
	
	void setGame(Game game);
	
	void myMove();

}