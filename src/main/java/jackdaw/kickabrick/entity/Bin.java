package jackdaw.kickabrick.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import framework.window.Window;
import jackdaw.kickabrick.rsrcmngr.Images;

public class Bin extends Kickable{

	public Bin() {
		super(Images.bin, 112, 1000, 0.000001);
		box = new Rectangle(0,0, Window.getGameScale(20)* (size/32), 3); //posX and y get set in update, pointless to fill in here
	}

	@Override
	public void kick(double force, double angle, double forcemodifier) {
		//leave empty so you cannot kick. even though at this point it shouldn't be possible
	}
	
	public boolean intersects(ArrayList<Kickable> theKickables){
		for(Kickable k : theKickables){
			
			if(k instanceof Bin)
				continue; //skip
			
			if(box.intersects(k.getBox()) && k.isFalling()){
				theKickables.remove(k);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.setColor(Color.black);
		g.fill(box);
		
	}
	
	@Override
	public void update() {
		super.update();
		
		box.setLocation((int)(posX + size/4d), (int)(posY + Window.getGameScale(20)));
	}
}
