package jackdaw.kickabrick.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import framework.window.Window;
import jackdaw.kickabrick.rsrcmngr.Images;

public class Player {

	private BufferedImage playerStill;
	private BufferedImage playerKick;
	private BufferedImage[] walking ;

	private int animationWalkTimer;
	private int animationIndex = 0;
	
	private boolean isWalking = true;
	private boolean isKicking = false;

	private double centerX, centerY, centerOffsetX;
	private int playerSize;

	private Rectangle box;
	
	//start with no extra leg power
	private double legForce = 0; 
	
	public Player() {

		playerStill = Images.dude;
		playerKick = Images.dudeKick;
		
		walking = new BufferedImage[]{
				Images.dude,
				Images.dudeWalk1,
				Images.dude,
				Images.dudeWalk2
		};

		playerSize = Window.getGameScale(256);

		centerX =   Window.getWidth() * 0.15; //about a third of the screen width

		centerY = Window.getHeight()/2 - 12;
		
		box = new Rectangle(
				(int)centerX + Window.getGameScale(80),
				(int)centerY,
				Window.getGameScale(85),
				Window.getGameScale(256));
	}

	public void draw(Graphics2D g){

		if(isKicking){
			drawCenteredPlayer(playerKick, g);
		}
		
		//check for if kicking before checking anything else, or it will never draw kicking
		else if(!isWalking)
			drawCenteredPlayer(playerStill, g);
		else if(isWalking){
			drawCenteredPlayer(walking[animationIndex], g);
		}
		
//		g.draw(box);
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

	public Rectangle getBox() {
		return box;
	}
	
	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}
	
	private void drawCenteredPlayer(BufferedImage img, Graphics2D g){
		g.drawImage(img, 
				(int)centerX + (int)centerOffsetX, (int)centerY,
				playerSize,playerSize,
				null);
	}
	
	public void setKicking(boolean isKicking) {
		this.isKicking = isKicking;
	}
	
	public boolean isReadyToKick() {
		return isKicking;
	}
	
	public double getLegForce() {
		return legForce;
	}
	
	public void addLegForce(double legForce) {
		this.legForce += legForce;
	}
}
