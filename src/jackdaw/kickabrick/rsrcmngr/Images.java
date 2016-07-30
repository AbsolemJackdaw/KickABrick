package jackdaw.kickabrick.rsrcmngr;

import java.awt.image.BufferedImage;

import framework.resourceLoaders.ImageLoader;

public class Images {

	public static BufferedImage dude;
	public static BufferedImage dudeWalk1;
	public static BufferedImage dudeWalk2;
	public static BufferedImage dudeKick;

	public static BufferedImage arrow;

	public static BufferedImage stone;
	public static BufferedImage fridge;
	public static BufferedImage glace;
	public static BufferedImage glace_decor;
	public static BufferedImage can;
	public static BufferedImage bottle;
	public static BufferedImage bottle_decor;
	public static BufferedImage pizza;
	public static BufferedImage pizza_decor;

	public static BufferedImage bin;

	public static BufferedImage overlay_night_background;

	public static BufferedImage stoep;
	public static BufferedImage[] bushes1 = new BufferedImage[3] ;
	public static BufferedImage[] bushes2 = new BufferedImage[3] ;
	public static BufferedImage[] trees = new BufferedImage[3] ;
	public static BufferedImage[] leaves = new BufferedImage[3] ;
	public static BufferedImage[] sky = new BufferedImage[3] ;

	public Images(){
		
		dude =	ImageLoader.loadSprite("/dude.png");
		dudeWalk1 =	ImageLoader.loadSprite("/dudewalk1.png");
		dudeWalk2 =	ImageLoader.loadSprite("/dudewalk2.png");
		dudeKick =	ImageLoader.loadSprite("/dudekick.png");

		overlay_night_background = ImageLoader.loadSprite("/nightoverlay.png");
		
		stoep = ImageLoader.loadSprite("/background/stoep.png");
		ImageLoader.loadImages(bushes1, "/background/bush1/bush");
		ImageLoader.loadImages(bushes2, "/background/bush2/bush");
		ImageLoader.loadImages(trees, "/background/trees/trees");
		ImageLoader.loadImages(leaves, "/background/leaves/leaves");
		ImageLoader.loadImages(sky, "/background/sky/sky");
		
		stone = ImageLoader.loadSprite("/kickables/stone.png");
		fridge = ImageLoader.loadSprite("/kickables/fridge.png");
		glace = ImageLoader.loadSprite("/kickables/icecream.png");
		glace_decor = ImageLoader.loadSprite("/kickables/decor/icecream.png");
		can = ImageLoader.loadSprite("/kickables/can.png");
		pizza = ImageLoader.loadSprite("/kickables/pizza.png");
		pizza_decor = ImageLoader.loadSprite("/kickables/decor/pizza.png");
		bottle = ImageLoader.loadSprite("/kickables/bottle.png");
		bottle_decor = ImageLoader.loadSprite("/kickables/decor/bottle.png");

		bin = ImageLoader.loadSprite("/bin.png");

		arrow = ImageLoader.loadSprite("/arrow.png");
	}
}
