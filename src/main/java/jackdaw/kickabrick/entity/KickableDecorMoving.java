package jackdaw.kickabrick.entity;

import java.awt.image.BufferedImage;

public class KickableDecorMoving extends KickableDecor {

	private double movePerTick;
	
	public KickableDecorMoving(BufferedImage img, Kickable k, int scale, double movePerTick) {
		super(img, k, scale);
		this.movePerTick = movePerTick;
	}
	
	@Override
	public void update(boolean move) {
		posX-=movePerTick;
	}

}
