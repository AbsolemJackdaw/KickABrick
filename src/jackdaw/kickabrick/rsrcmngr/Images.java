package jackdaw.kickabrick.rsrcmngr;

import java.awt.image.BufferedImage;

import framework.resourceLoaders.ImageLoader;

public class Images {

	public static BufferedImage dude;
	public static BufferedImage dudeWalk1;
	public static BufferedImage dudeWalk2;

	
	public static BufferedImage overlay_night_background;

	public static BufferedImage stoep;
	public static BufferedImage[] bushes1 = new BufferedImage[3] ;
	public static BufferedImage[] bushes2 = new BufferedImage[3] ;
	public static BufferedImage[] trees = new BufferedImage[3] ;
	public static BufferedImage[] sky = new BufferedImage[3] ;

	public Images(){
		
		dude =	ImageLoader.loadSprite("/dude.png");
		dudeWalk1 =	ImageLoader.loadSprite("/dudewalk1.png");
		dudeWalk2 =	ImageLoader.loadSprite("/dudewalk2.png");
		
		overlay_night_background = ImageLoader.loadSprite("/nightoverlay.png");
		
		stoep = ImageLoader.loadSprite("/background/stoep.png");
		ImageLoader.loadImages(bushes1, "/background/bush1/bush");
		ImageLoader.loadImages(bushes2, "/background/bush2/bush");
		ImageLoader.loadImages(trees, "/background/trees/trees");
		ImageLoader.loadImages(sky, "/background/sky/sky");

	}
}
