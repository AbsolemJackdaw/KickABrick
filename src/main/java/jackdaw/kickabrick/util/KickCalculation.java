package jackdaw.kickabrick.util;

import framework.input.MouseHandler;
import framework.window.Window;
import jackdaw.kickabrick.player.Player;

public class KickCalculation {

	public static double getAngle(Player player){

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
	
	public static double getForce(Player player, double maxPower){

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

		return scaleHypotenuse ;
	}
}
