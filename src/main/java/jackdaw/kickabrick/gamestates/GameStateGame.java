package jackdaw.kickabrick.gamestates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import framework.GameState;
import framework.GameStateHandler;
import framework.input.MouseHandler;
import framework.window.Window;
import jackdaw.kickabrick.entity.Bin;
import jackdaw.kickabrick.entity.Entities;
import jackdaw.kickabrick.entity.Kickable;
import jackdaw.kickabrick.player.Player;
import jackdaw.kickabrick.rsrcmngr.BackGroundHelper;
import jackdaw.kickabrick.rsrcmngr.Images;
import jackdaw.kickabrick.util.KickCalculation;
import jackdaw.kickabrick.util.Util;

public class GameStateGame extends GameState {

	private Player player;

	private ArrayList<Kickable> kickables = new ArrayList<Kickable>();

	private int encounteredKickable = -1;

	private double maxPower = 35;
	private double maxPowerDefault = 35;

	private int timesKicked;

	public GameStateGame(GameStateHandler gsh) {
		super(gsh);

		player = new Player();

		kickables.add(Entities.kickableForString.get(Entities.stone));
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Window.getWidth(), Window.getHeight());

		BackGroundHelper.drawScrollingBackground(g);

		for(Kickable k : kickables)
			if(k.hasAffiliatedDecor())
				k.getAffiliatedDecor().draw(g);

		if(player.isReadyToKick()){
			drawArrowPointingToMouse(g);
		}
		
		for(Kickable k : kickables)
			if(k.isVisible())
				k.draw(g);
		
		player.draw(g);


	}

	@Override
	public void update() {

		BackGroundHelper.updateScrollingBackground();
		player.update();

		for(Kickable k : kickables){
			
			k.update();
			//bin after update for position related issues
			if(k instanceof Bin)
				if(((Bin) k).intersects(kickables))
					break;
			
			if(k.hasAffiliatedDecor())
				k.getAffiliatedDecor().update(k.getMoving());
			
			if(k.getPosX() < -50){
				//if kickable scrolled offscreen
				kickables.remove(k);
				break;
			}
			
		}

		int kicked = 0;
		//this has a loop for itself so all objects can get updated before stopping to scroll
		for(Kickable k : kickables){
			//if player hits the box of a kickable
			if(player.getBox().intersects(k.getBox()) && !(k instanceof Bin)){
				//set encountered index to the index of the kickable
				//this works because 'kickables' is a list and the 'encounteredkickable' reflects the index
				//of the encountered object
				encounteredKickable = kicked;
				break;
			}
			kicked++;
		}

		if(encounteredKickable > -1){
			//freeze all
			BackGroundHelper.stopScrollingBackground();
			player.setWalking(false);
			
			for(Kickable k : kickables)
				k.setMoving(false);

			//check for click
			if(MouseHandler.click){
				if(player.isReadyToKick()){

					//kick object
					kickables.get(encounteredKickable).kick(KickCalculation.getForce(player, maxPower), KickCalculation.getAngle(player), player.getLegForce());
					player.setKicking(false);

					//set stuff to move again
					BackGroundHelper.resumeScrollingBackground();
					player.setWalking(true);
					for(Kickable k : kickables)
						k.setMoving(true);

					//add and substract needed modifiers
					encounteredKickable = -1;
					player.addLegForce(0.05); 
					timesKicked++;
				}

				//set check after kick, or will kick immediatly after clicked
				if(encounteredKickable > -1)
					if(!player.isReadyToKick() && !kickables.get(encounteredKickable).isInAir()){
						player.setKicking(true);
						maxPower = maxPowerDefault * kickables.get(encounteredKickable).getWeight();
					}
			}
		}
		
		addKickable();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////

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
				(int)KickCalculation.getAngle(player)*-1, 
				Window.getGameScale(64), 
				true
				);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		drawTrajectory(g, startX, startY);
	}

	public void drawTrajectory(Graphics2D g, int posX, int posY){

		if(encounteredKickable <= -1)
			return;

		//transparents the image
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));

		Kickable kicked = kickables.get(encounteredKickable);
		
		double toBeVelocityX = (KickCalculation.getForce(player, maxPower) * (kicked.getWeight() + player.getLegForce()))/1.2 * Math.cos(Math.toRadians(KickCalculation.getAngle(player)));
		double toBeVelocityY = (KickCalculation.getForce(player, maxPower) *	(kicked.getWeight() + player.getLegForce() * Math.sin(Math.toRadians(KickCalculation.getAngle(player)))));
		
		//get the time of flight until it hits the ground
		double timeOfFlight = (2*toBeVelocityY) / (Kickable.gravity); 
		
		//draw the line which represents the trajectory, where the integer 'i' is the time passed
		for(double time = 0; time < timeOfFlight; time+= 2d){

			g.setColor(Color.BLACK);
			//calculate position at time
			double x =  toBeVelocityX * time;
			double y = (toBeVelocityY * time) - (Kickable.gravity/2d*time*time) ;

			//draw dot at position
			g.fillOval(posX+(int)x-3, posY-(int)y-3, 6, 6);

		}
		//reset transparency, or it will flow over to the rest of the game.
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	private void addKickable(){

		String newKickable = getStringForNewKickable();
		if(newKickable.length() < 5)
			return;
		
		if(kickables.contains(Entities.kickableForString.get(newKickable)) /*&& !newKickable.equals(Entities.bin)*/)
			return;

		kickables.add(Entities.kickableForString.get(newKickable));
		player.addLegForce(-player.getLegForce()/2);
	}

	private String getStringForNewKickable(){
		switch(timesKicked){
		case 5 : return Entities.can;
		case 15 : return Entities.bottle;
		case 25 : return Entities.glace;
		case 35 : return Entities.pizza;

		case 10 : return Entities.bin;
		case 20 : return Entities.bin;
		case 30 : return Entities.bin;
		case 40 : return Entities.bin;
		case 50 : return Entities.bin;

		}
		
		return "";
	}
}
