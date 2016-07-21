package jackdaw.kickabrick.gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import framework.GameStateHandler;
import framework.gamestate.LoadState;
import framework.input.MouseHandler;
import framework.resourceLoaders.ImageLoader;
import framework.window.Window;
import jackdaw.kickabrick.player.Player;
import jackdaw.kickabrick.rsrcmngr.BackGroundHelper;
import jackdaw.kickabrick.rsrcmngr.Images;

public class GameStateMenu extends LoadState{

	private Player player;
	private float alphaFade = 0f;
	private Font font = null;

	//when the player should enter frame
	private boolean queuePlayer = false;
	//the screen offset the player should have from the center
	private double offsetPlayerX = -1;

	private BufferedImage loadingIcon;
	private int loadRotation = 0; 

	public GameStateMenu(GameStateHandler gsh) {
		super(gsh);

		loadingIcon = ImageLoader.loadSprite("/loadingIcon.png");
	}

	@Override
	protected void loadResources() {

		registerFont();
		font = new Font("Fancy Pants NF", Font.PLAIN, 75);

		new Images();
		player = new Player();

		//init these in loading resources, as the images have to be loaded to be displayed
		BackGroundHelper.initScrollingBackground();
		BackGroundHelper.stopScrollingBackground();

	}

	@Override
	public void draw(Graphics2D g) {

		g.setColor(Color.black);
		g.fillRect(0, 0, Window.getWidth(), Window.getHeight());

		BackGroundHelper.drawScrollingBackground(g);

		//Draw player when loaded
		if(player != null){
			player.draw(g);
		}

		//draw game title
		if(font != null){
			g.setFont(font);

			g.setColor(Color.red.darker().darker());
			g.drawString("Kick A Brick",
					Window.getWidth()/2 - g.getFontMetrics().stringWidth("Kick A Brick")/2,
					(int)((double)Window.getHeight()/ 3d));

			g.setColor(new Color(1f,1f,1f,.7f));
			g.drawString("Kick A Brick",
					Window.getWidth()/2 - g.getFontMetrics().stringWidth("Kick A Brick")/2 - 2,
					(int)((double)Window.getHeight()/ 3d) - 2);
			
			if(isDoneLoadingResources()){
				g.setFont(new Font("Fancy Pants NF", Font.PLAIN, 35));
				g.drawString("Click to Start", 
						Window.getWidth()/2 - g.getFontMetrics().stringWidth("Click to Start")/2,
						Window.getHeight() - (int)((double)Window.getHeight()/ 8d));

			}
		}

		if(!isDoneLoadingResources())
			drawRotatingImage(loadingIcon, g, 
					Window.getWidth() - Window.getGameScale(120), 
					Window.getHeight() - Window.getGameScale(120),
					loadRotation, 128);

		//draw fading black screen when all is done and loaded
		g.setColor(new Color(0f,0f,0f, alphaFade));
		g.fillRect(0, 0, Window.getWidth(), Window.getHeight());

	}

	@Override
	public void update() {
		super.update();

		if(!isDoneLoadingResources())
			loadRotation+=5;

		BackGroundHelper.updateScrollingBackground();

		//scroll background when player is centered
		if(offsetPlayerX >= 0d)
			BackGroundHelper.resumeScrollingBackground();

		//update player position
		//set offset for player once player is loaded
		if(player != null){
			player.update();
			if(queuePlayer && offsetPlayerX < 0){
				offsetPlayerX+=1d;
				player.drawOffsetX(offsetPlayerX);
			}
			if(!queuePlayer){
				queuePlayer = true;
				offsetPlayerX = Window.getGameScale(-550d);
				player.drawOffsetX(offsetPlayerX);
			}
		}


		if(isDoneLoadingResources() && offsetPlayerX >= 0){

			if(offsetPlayerX >= 0 && MouseHandler.click){
				System.out.println("CLICKED");
				gsh.changeGameState(1);
			}
		}
	}

	private void registerFont(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		InputStream stream = null;
		stream = GameStateMenu.class.getClassLoader().getResourceAsStream("FancyPants.ttf");

		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, stream));
		} catch (IOException|FontFormatException e) {
			e.printStackTrace();
		}
	}

	private void drawRotatingImage(BufferedImage img, Graphics2D g, int x, int y, int rotation, int scale){

		// Rotation information
		double rotationRequired = Math.toRadians (rotation);

		int imgSize = img.getWidth();
		double diagonal = imgSize* Math.sqrt(2);

		BufferedImage canvas = new BufferedImage(
				(int)(diagonal), 
				(int)(diagonal), 
				img.getType());

		//get grapchics from the canvas
		Graphics2D g2d = (Graphics2D) canvas.getGraphics();

		//rotate canvas internally
		if(rotation < 0)
			g2d.rotate(-rotationRequired, 
					(int)(diagonal/2), 
					(int)(diagonal/2));
		else
			g2d.rotate(rotationRequired,
					(int)(diagonal/2), 
					(int)(diagonal/2));

		//draw image centered
		g2d.drawImage(img, 
				(int)(diagonal/2) - imgSize/2,  
				(int)(diagonal/2) - imgSize/2, null);


		g.drawImage(canvas,
				x, y, 
				Window.getGameScale(scale),
				Window.getGameScale(scale),
				null);
	}
}
