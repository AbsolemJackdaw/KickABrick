package jackdaw.kickabrick.main;

import framework.GameState;
import framework.GameStateHandler;
import jackdaw.kickabrick.gamestates.GameStateGame;
import jackdaw.kickabrick.gamestates.GameStateMenu;

public class GameStateHandlerKick extends GameStateHandler {

    public static final String GAME = "walkway_gamestate";

    public GameStateHandlerKick() {

        addGameState(GameStateMenu.class, GameState.FIRST_SCREEN);
        addGameState(GameStateGame.class, GAME);

    }
}
