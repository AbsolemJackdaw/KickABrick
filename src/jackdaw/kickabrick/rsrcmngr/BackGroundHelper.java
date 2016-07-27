package jackdaw.kickabrick.rsrcmngr;

import java.awt.Graphics2D;

public class BackGroundHelper {

	private static Background trees = null;
	private static Background sky = null;
	private static Background bush1 = null;
	private static Background bush2 = null;
	private static Background stoep = null;

	public static void initScrollingBackground(){
		trees = new Background(Images.trees).moveScale(0.3);
		bush1 = new Background(Images.bushes1).moveScale(1);
		bush2 = new Background(Images.bushes2).moveScale(0.5);
		sky = new Background(Images.sky).moveScale(0.1);
		stoep = new Background(Images.stoep);
	}

	public static void drawScrollingBackground(Graphics2D g){

		if(sky != null)
			sky.drawMoving(g);
		if(trees!=null)
			trees.drawMoving(g);
		if(bush2!=null)
			bush2.drawMoving(g);
		if(bush1!=null)
			bush1.drawMoving(g);
		if(stoep!=null)
			stoep.draw(g);

	}

	public static void updateScrollingBackground(){
		if(sky != null)
			sky.update();
		if(trees!=null)
			trees.update();
		if(bush2!=null)
			bush2.update();
		if(bush1!=null)
			bush1.update();
		if(stoep!=null)
			stoep.update();
	}

	private static boolean hasStopped = false;
	public static void stopScrollingBackground(){

		if(!hasStopped){
			sky.stopScrolling();
			trees.stopScrolling();
			bush1.stopScrolling();
			bush2.stopScrolling();
			hasStopped = true;
		}

	}

	public static void resumeScrollingBackground(){
		if(hasStopped){
			sky.resumeScrolling();
			trees.resumeScrolling();
			bush1.resumeScrolling();
			bush2.resumeScrolling();
		}
		hasStopped = false;
	}
}
