package jackdaw.kickabrick.gamestates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import framework.GameState;
import framework.GameStateHandler;
import framework.input.MouseHandler;
import framework.window.Window;
import jackdaw.kickabrick.entity.Kickable;
import jackdaw.kickabrick.player.Player;
import jackdaw.kickabrick.rsrcmngr.BackGroundHelper;
import jackdaw.kickabrick.rsrcmngr.Images;
import jackdaw.kickabrick.util.Util;

public class GameStateGame extends GameState {

	private Player player;

	private Kickable kickable;

	private double maxPower = 35;

	public GameStateGame(GameStateHandler gsh) {
		super(gsh);

		//		BackGroundHelper.initScrollingBackground();
		player = new Player();

		kickable = new Kickable(
				Images.stone, 
				32, 
				Window.getWidth() + Window.getGameScale(10), 
				Window.getHeight() - Window.getGameScale(40),
				1.9);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Window.getWidth(), Window.getHeight());

		BackGroundHelper.drawScrollingBackground(g);
		player.draw(g);

		if(player.isReadyToKick()){
			drawArrowPointingToMouse(g);
		}

		kickable.draw(g);


	}

	@Override
	public void update() {

		BackGroundHelper.updateScrollingBackground();
		player.update();
		kickable.update();

		if(kickable.landed()){
			BackGroundHelper.resumeScrollingBackground();
			player.setWalking(true);
			kickable.setMoving(true);
		}

		if(player.getBox().intersects(kickable.getBox())){

			BackGroundHelper.stopScrollingBackground();

			player.setWalking(false);
			kickable.setMoving(false);

			if(MouseHandler.click){
				if(player.isReadyToKick()){
					kickable.kick(getForce(), getAngle());
					player.setKicking(false);
				}

				//set check after kick, or will kick immediatly after clicked
				if(!player.isReadyToKick() && !kickable.isInAir())
					player.setKicking(true);
			}
		}
	}

	private void drawArrowPointingToMouse(Graphics2D g){

		int startX = player.getBox().x + player.getBox().width;
		int startY = player.getBox().y + player.getBox().height;

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		Util.drawRotating(
				Images.arrow, 
				g,
				startX,
				startY - Window.getGameScale(64), 
				0d, 
				1d, 
				(int)getAngle()*-1, 
				64, 
				true
				);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		drawTrajectory(g, startX, startY);
	}

	private double getAngle(){

		if(MouseHandler.mouseX < player.getBox().x + player.getBox().getWidth())
			return 90d;

		if(MouseHandler.mouseY > player.getBox().y + player.getBox().getHeight())
			return 0d;

		//Considering triangle ABC where angle B = 90°
		//AC is hypotenuse

		//length for x side, or adjecent
		double AB = MouseHandler.mouseX - (double)(player.getBox().x + player.getBox().getWidth());
		//lenght for y side, or opposite
		double BC = (double)(player.getBox().y + player.getBox().getHeight()) - MouseHandler.mouseY ;

		double angle = Math.atan2(BC, AB);
		double degrees = Math.toDegrees(angle);

		return degrees;
	}

	private double getForce(){

		if(MouseHandler.mouseX < player.getBox().x + player.getBox().getWidth())
			return 0d;

		if(MouseHandler.mouseY > player.getBox().y + player.getBox().getHeight())
			return 0d;

		//Considering triangle ABC where angle B = 90°
		//AC is hypotenuse

		//length for x side, or adjecent
		double AB = MouseHandler.mouseX - (player.getBox().getX() + player.getBox().getWidth());
		//lenght for y side, or opposite
		double BC = (player.getBox().getY() + player.getBox().getHeight()) - MouseHandler.mouseY ;

		double AC = Math.sqrt(BC*BC + AB*AB);


		double max = Window.getWidth() - (player.getBox().getX() + player.getBox().getWidth());

		double scaleHypotenuse = (AC / max * maxPower)*2;

		if(scaleHypotenuse > maxPower)
			scaleHypotenuse = maxPower;

		return scaleHypotenuse;
	}
	
	public void drawTrajectory(Graphics2D g, int posX, int posY){
		
		//transparents the image
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));

		//calculate velocity from angle and force
		double toBeVelocityX = getForce()/1.2 * Math.cos(Math.toRadians(getAngle()));
		double toBeVelocityY = getForce() * Math.sin(Math.toRadians(getAngle()));

		//get the time of flight until it hits the ground
		double parabolaTime = (2*toBeVelocityY) / Kickable.gravity ;

		//draw the line which represents the trajectory, where the integer 'i' is the time passed
		for(double i = 0; i < parabolaTime; i+= parabolaTime/10d){

			g.setColor(Color.BLACK);
			//calculate position at time
			double x = (toBeVelocityX * i);
			double y = (toBeVelocityY * i) - (Kickable.gravity/2d*i*i);

			//draw dot at position
			g.fillOval(posX+(int)x-3, posY-(int)y-3, 6, 6);
			
		}
		//reset transparency, or it will flow over to the rest of the game.
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
