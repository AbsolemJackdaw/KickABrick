package jackdaw.kickabrick.gamestates;

import java.awt.Graphics2D;

import framework.GameState;
import framework.GameStateHandler;
import jackdaw.kickabrick.player.Player;
import jackdaw.kickabrick.rsrcmngr.BackGroundHelper;

public class GameStateGame extends GameState {

	private Player player;
	
	public GameStateGame(GameStateHandler gsh) {
		super(gsh);
		
//		BackGroundHelper.initScrollingBackground();
		player = new Player();
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		BackGroundHelper.drawScrollingBackground(g);
		player.draw(g);
	}
	
	@Override
	public void update() {
		
		BackGroundHelper.updateScrollingBackground();
		player.update();
	}
}
