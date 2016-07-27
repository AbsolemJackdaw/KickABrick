package jackdaw.kickabrick.main;

import framework.GamePanel;
import framework.GameStateHandler;

@SuppressWarnings("serial")
public class GamePanelKick extends GamePanel {

	public GamePanelKick() {
	}
	
	@Override
	public GameStateHandler getCustomGameStateHandler() {
		return new GameStateHandlerKick();
	}
}
