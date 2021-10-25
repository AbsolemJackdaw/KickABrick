package jackdaw.kickabrick.main;

import framework.GamePanel;
import framework.GameStateHandler;

public class GamePanelKick extends GamePanel {

    @Override
    public GameStateHandler getCustomGameStateHandler() {
        return new GameStateHandlerKick();
    }
}
