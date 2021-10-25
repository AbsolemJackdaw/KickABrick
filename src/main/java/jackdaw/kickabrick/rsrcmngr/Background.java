package jackdaw.kickabrick.rsrcmngr;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import framework.window.Window;

public class Background {

	private double moveScale = 0;
	private BufferedImage img;

	private BufferedImage[] imgs;

	public static float scale;
	public static float sizeX;
	public static float sizeY;

	public double offset = 0;

	private	Random rand = new Random();
	private int indexScrollGround1 = 0;
	private int indexScrollGround2 = 0;

	public Background(BufferedImage img) {
		this.img = img;

		scale = (float)Window.getWidth() / img.getWidth();
		sizeX = img.getWidth() * scale;
		sizeY = img.getHeight() * scale;
	}

	public Background(BufferedImage[] imgs) {
		this.imgs = imgs;

		scale = (float)Window.getWidth() / imgs[0].getWidth();
		sizeX = imgs[0].getWidth() * scale;
		sizeY = imgs[0].getHeight() * scale;

		indexScrollGround1 = rand.nextInt(imgs.length);
		indexScrollGround2 = rand.nextInt(imgs.length);

	}

	public Background moveScale(double d){
		moveScale = d;
		return this;
	}

	public void draw(Graphics2D g){
		if(img != null)
			g.drawImage(img, 
					0, Window.getHeight()/2 - (int)sizeY/2,
					(int)sizeX, (int)sizeY,
					null);
	}

	public void drawMoving(Graphics2D g){

		if(img != null){
			g.drawImage(img, 
					(int)offset, Window.getHeight()/2 - (int)sizeY/2,
					(int)sizeX, (int)sizeY,
					null);

			g.drawImage(img, 
					Window.getWidth() + (int)offset, Window.getHeight()/2 - (int)sizeY/2,
					(int)sizeX, (int)sizeY,
					null);
		}

		if(imgs != null){
			g.drawImage(imgs[indexScrollGround1], 
					(int)offset, Window.getHeight()/2 - (int)sizeY/2,
					(int)sizeX, (int)sizeY,
					null);

			g.drawImage(imgs[indexScrollGround2], 
					Window.getWidth() + (int)offset, Window.getHeight()/2 - (int)sizeY/2,
					(int)sizeX, (int)sizeY,
					null);
		}

	}

	public void update(){

		if(moveScale > 0)
			offset-= moveScale;

		if(offset < -(Window.getWidth())){
			offset = 0;
			indexScrollGround1 = indexScrollGround2;
			
			int size = 0; //fix when only one image is used as background
			if(imgs != null)
				size = imgs.length;
			else
				size = 1;
			
			indexScrollGround2 = rand.nextInt(size);
		}

	}

	private double defaultScroll = 0;

	public void stopScrolling(){
		//only change if the default scroll hasnt been registered yet
		if(defaultScroll == 0){
			defaultScroll = moveScale;
			moveScale = 0;
		}
	}

	public void resumeScrolling(){
		//reset only if a scroll value has been set
		//if default = 0, then no scroll was set and/or never had one
		if(defaultScroll > 0){
			moveScale = defaultScroll;
			defaultScroll = 0;
		}
	}
}
