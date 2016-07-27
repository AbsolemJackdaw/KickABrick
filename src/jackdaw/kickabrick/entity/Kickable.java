package jackdaw.kickabrick.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import jackdaw.kickabrick.util.Util;

public class Kickable {

	//position on screen
	private double posX, posY, posYDefault;

	//img to draw
	private BufferedImage img;

	//how hard and far you can kick the object
	public int weight = 0;

	//(force of kick - weight) might determine the kick.
	//which means the player should get a little stronger every kick

	//the speed and distance at which the kickable will fly
	private double velocityX,velocityY, prevVolX, prevVolY;

	private boolean inAir;
	private boolean prevAir;

	private Rectangle box;

	private boolean moveOnGround = true;

	public static double gravity = 2.1d/2d;

	//number of bounces to make
	private int bounces;

	//kickables are square objects. this determines the onscreen size
	private int size;
	
	private double bouncyness;

	public Kickable(BufferedImage image, int size, int x, int y, double bouncyness){
		this.img = image;
		this.size = size;

		posX = x;
		posYDefault = posY = y-size;

		box = new Rectangle((int)posX, (int)posY-size, size, size);
		
		this.bouncyness = bouncyness;

	}

	private int imageRotation;
	public void draw(Graphics2D g){

		if(!isInAir())
			g.drawImage(img, (int)posX, (int)posY, size, size, null);
		else
			Util.drawRotatedImage(img, g, (int)posX, (int)posY, imageRotation+=15, size, true);

//		g.setColor(Color.black);
//		g.draw(box);

	}

	public void update(){

		if(inAir && !prevAir)
			prevAir = true;

		if(!inAir && prevAir)
			prevAir = false;

		if(inAir){

			posX += velocityX ;

			posY -= velocityY;

			velocityY -= gravity;
			//let weight affect gravity ?
			
			if(posY > posYDefault){

				//reset all if no bounces left
				if(bounces == 0){
					posY = posYDefault;
					inAir = false;

					velocityX = 0;
					velocityY = 0;
				}

				//decrease bounces
				//rebounce if > 0
				//aka reset velocity x and y to prevVX/2 and prevVY/2
				else if (bounces > 0){
					velocityX = prevVolX/bouncyness;
					velocityY = prevVolY/bouncyness;

					prevVolX = velocityX;
					prevVolY = velocityY;

					bounces --;
				}
			}
		}else{

			if(moveOnGround)
				posX -= 1.0;
		}

		box.setLocation((int)posX, (int)posY);

	}

	/**Kick the kickable !*/
	public void kick(double force, double angle){
		
		//TODO
		//let weight affect force so it goes slower

		inAir = true;

		prevVolX = velocityX = force/1.2 * Math.cos(Math.toRadians(angle));
		prevVolY = velocityY = force * Math.sin(Math.toRadians(angle));

		bounces = (int)(force/6d);
		
	}

	public boolean isInAir() {
		return inAir;
	}

	public Rectangle getBox() {
		return box;
	}

	public boolean landed(){
		return prevAir==true && inAir==false && bounces == 0;
	}

	public void setMoving(boolean b){
		moveOnGround = b;
	}
}
