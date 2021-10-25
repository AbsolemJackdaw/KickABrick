package jackdaw.kickabrick.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import framework.window.Window;
import jackdaw.kickabrick.util.Util;

public class Kickable {

	//position on screen
	protected double posX, posY, posYDefault, prevPosY, prevPosX;

	//img to draw
	protected BufferedImage img;

	//how hard and far you can kick the object
	protected double weight = 1;

	//(force of kick - weight) might determine the kick.
	//which means the player should get a little stronger every kick

	//the speed and distance at which the kickable will fly
	protected double velocityX,velocityY, prevVolX, prevVolY;

	protected boolean inAir;
	protected boolean prevAir;

	protected Rectangle box;

	protected boolean moveOnGround = true;

	public static double gravity = 2.1d/2d;

	//number of bounces to make
	protected int bounces;

	//kickables are square objects. this determines the onscreen size
	protected int size;

	protected double bouncyness;

	protected KickableDecor affiliatedDecor = null;

	public Kickable(BufferedImage image, int size, double bouncyness, double weight){
		this(image, size, Window.getWidth() + Window.getGameScale(10), 
				Window.getHeight() - Window.getGameScale(40), bouncyness, weight);
	}

	/**
	 * @param image : the image t be displayed
	 * @param size : the size the image will be scaled to and drawn on screen. generally bigger then the original size
	 * @param x : position x on screen
	 * @param y : position y on screen
	 * @param bouncyness : lower value is more bouncy. range 1.0 ~ 1.99
	 * @param weight : higher value is heavier.
	 * */
	public Kickable(BufferedImage image, int size, int x, int y, double bouncyness, double weight){
		this.img = image;
		this.size = size;

		posX = x;
		posYDefault = posY = y-size;

		box = new Rectangle((int)posX, (int)posY, size, size);

		this.bouncyness = bouncyness;

		if(weight < 0.0)
			weight = 0;
		if(weight > 1.0)
			weight = 1.0;

		this.weight = weight;

	}

	private int imageRotation;
	public void draw(Graphics2D g){

		//		g.setColor(Color.black);
		//		g.fill(box);

		if(!isInAir())
			//			g.drawImage(img, (int)posX, (int)posY, size, size, null);
			Util.drawRotatedImage(img, g, (int)posX, (int)posY, imageRotation, size, true);

		else
			Util.drawRotatedImage(img, g, (int)posX, (int)posY, imageRotation+=30, size, true);

	}

	public void update(){
		//set previous positions
		prevPosY = posY;
		prevPosX = posX;

		if(inAir && !prevAir)
			prevAir = true;

		if(!inAir && prevAir)
			prevAir = false;

		if(inAir){

			posX += velocityX ;

			posY -= velocityY;

			velocityY -= gravity + (1-weight); //invert weight here, as a higher number makes it fall faster
			//let weight affect gravity ? TODO

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
	public void kick(double force, double angle, double forcemodifier){

		force = force * (weight+forcemodifier);

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
		return prevAir && !inAir && bounces == 0;
	}

	public void setMoving(boolean b){
		moveOnGround = b;
	}

	public boolean getMoving(){
		return moveOnGround;
	}

	public void setWeight(double weight) {

		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public boolean isVisible(){
		return posX < Window.getWidth()+ Window.getGameScale(10) && posX > -50;
	}

	public Kickable setAffiliatedDecor(KickableDecor affiliatedDecor) {
		this.affiliatedDecor = affiliatedDecor;
		return this;
	}

	public KickableDecor getAffiliatedDecor() {

		if(affiliatedDecor instanceof KickableDecorMoving)
			return (KickableDecorMoving)affiliatedDecor;
		else
			return affiliatedDecor;
	}

	public boolean hasAffiliatedDecor(){
		return affiliatedDecor != null;
	}

	public int getSize() {
		return size;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public boolean isFalling(){
		return isInAir() && posY > prevPosY;
	}

	public boolean isGoingUp(){
		return isInAir() && posY < prevPosY;
	}
}
