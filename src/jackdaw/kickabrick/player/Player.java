package jackdaw.kickabrick.player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.window.Window;
import jackdaw.kickabrick.rsrcmngr.Images;

public class Player {

	private BufferedImage playerStill;
	private BufferedImage[] walking ;

	private int animationWalkTimer;
	private int animationIndex = 0;
	
	private boolean isWalking = true;

	private double centerX, centerY, centerOffsetX;
	private int playerSize;

	public Player() {

		playerStill = Images.dude;

		walking = new BufferedImage[]{
				Images.dude,
				Images.dudeWalk1,
				Images.dude,
				Images.dudeWalk2
		};

		playerSize = Window.getGameScale(256);

		centerX = Window.getWidth()/2 - playerSize/1.5d;

		centerY = Window.getHeight()/2 - 12;

	}

	public void draw(Graphics2D g){

		if(!isWalking)
			g.drawImage(playerStill, 
					(int)centerX + (int)centerOffsetX, (int)centerY,
					playerSize,playerSize,
					null);
		else{
			g.drawImage(walking[animationIndex], 
					(int)centerX + (int)centerOffsetX, (int)centerY,
					playerSize,playerSize,
					null);
		}
	}

	public void update(){
		if(isWalking)
			animationWalkTimer++;
		
		if(animationWalkTimer % 10 == 0)
			animationIndex++;
		
		if(animationIndex > walking.length-1)
			animationIndex = 0;
	}
	
	public void drawOffsetX(double d){
		centerOffsetX = d;
	}
}
