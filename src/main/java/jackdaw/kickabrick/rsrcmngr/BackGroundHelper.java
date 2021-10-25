package jackdaw.kickabrick.rsrcmngr;

import java.awt.Graphics2D;

public class BackGroundHelper {

	private static Background[] backgrounds = null; 


	public static void initScrollingBackground(){

		//are put in order to draw. most back first, most front last
		backgrounds = new Background[]{
				new Background(Images.sky).moveScale(0.1),
				new Background(Images.trees).moveScale(0.3),
				new Background(Images.leaves).moveScale(0.3),
				new Background(Images.bushes2).moveScale(0.6),
				new Background(Images.bushes1).moveScale(1),
				new Background(Images.stoep).moveScale(1)
		};
	}

	public static void drawScrollingBackground(Graphics2D g){
		if(backgrounds != null)
			for(Background bg : backgrounds)
				if(bg != null)
					bg.drawMoving(g);
	}

	public static void updateScrollingBackground(){
		if(backgrounds != null)
			for(Background bg : backgrounds)
				if(bg != null)
					bg.update();
	}

	private static boolean hasStopped = false;
	public static void stopScrollingBackground(){

		if(!hasStopped){
			for(Background bg : backgrounds)
				if(bg != null)
					bg.stopScrolling();
			hasStopped = true;
		}

	}

	public static void resumeScrollingBackground(){
		if(hasStopped){
			for(Background bg : backgrounds)
				if(bg != null)
					bg.resumeScrolling();
			hasStopped = false;
		}
	}
}
