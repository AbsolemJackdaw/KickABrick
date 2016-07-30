package jackdaw.kickabrick.entity;

import java.util.HashMap;

import framework.window.Window;
import jackdaw.kickabrick.rsrcmngr.Images;

public class Entities {

	public static HashMap<String, Kickable> kickableForString = new HashMap<String, Kickable>(); 

	//!string < 5
	public static final String stone = "stone";
	public static final String fridge = "fridge";
	public static final String glace = "glace";
	public static final String can = "metal_can";
	public static final String pizza = "pizza";
	public static final String bottle = "bottle";
	public static final String bin = "the_bin";

	public Entities() {
		loadKickables();
		affiliateDecor();
	}

	private void affiliateDecor() {
		kickableForString.get(glace).setAffiliatedDecor(
				new KickableDecor(
						Images.glace_decor, 
						kickableForString.get(glace),
						Window.getGameScale(256)
						));
		
		kickableForString.get(bottle).setAffiliatedDecor(
				new KickableDecor(
						Images.bottle_decor, 
						kickableForString.get(bottle),
						Window.getGameScale(128)
						));
		
		kickableForString.get(pizza).setAffiliatedDecor(
				new KickableDecorMoving(
						Images.pizza_decor, 
						kickableForString.get(pizza),
						Window.getGameScale(256),
						10d
						));
	}

	private void loadKickables(){
		kickableForString.put(stone, 
				new Kickable(Images.stone, 
						Window.getGameScale(32), 
						1.9d,
						0.6d)
				);

		kickableForString.put(glace, 
				new Kickable(Images.glace, 
						Window.getGameScale(32), 
						1.2d,
						0.8d)
				);
		
		kickableForString.put(fridge, 
				new Kickable(Images.fridge, 
						Window.getGameScale(160), 
						1000d, //no bounce
						0.2d)
				);
		
		kickableForString.put(can, 
				new Kickable(Images.can, 
						Window.getGameScale(32), 
						1.5d,
						0.75d)
				);
		
		kickableForString.put(pizza, 
				new Kickable(Images.pizza, 
						Window.getGameScale(64), 
						1.99d,
						0.6d)
				);
		
		kickableForString.put(bottle, 
				new Kickable(Images.bottle, 
						Window.getGameScale(64), 
						1.8d,
						0.5d)
				);
		kickableForString.put(bin, 
				new Bin());
	}
}
