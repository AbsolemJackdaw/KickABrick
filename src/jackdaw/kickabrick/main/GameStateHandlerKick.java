package jackdaw.kickabrick.main;

import framework.GameStateHandler;
import jackdaw.kickabrick.gamestates.GameStateGame;
import jackdaw.kickabrick.gamestates.GameStateMenu;

public class GameStateHandlerKick extends GameStateHandler {

	public GameStateHandlerKick() {

		addGameState(GameStateMenu.class, 0);
		addGameState(GameStateGame.class, 1);

	}
}
