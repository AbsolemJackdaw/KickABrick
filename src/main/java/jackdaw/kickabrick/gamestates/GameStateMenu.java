package jackdaw.kickabrick.gamestates;

import framework.GameStateHandler;
import framework.gamestate.LoadState;
import framework.input.MouseHandler;
import framework.resourceLoaders.ImageLoader;
import framework.resourceLoaders.ResourceLocation;
import framework.window.Window;
import jackdaw.kickabrick.entity.Entities;
import jackdaw.kickabrick.main.GameStateHandlerKick;
import jackdaw.kickabrick.player.Player;
import jackdaw.kickabrick.rsrcmngr.BackGroundHelper;
import jackdaw.kickabrick.rsrcmngr.Images;
import jackdaw.kickabrick.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameStateMenu extends LoadState {

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

        loadingIcon = ImageLoader.loadSprite(new ResourceLocation("/loadingIcon.png"));
    }

    @Override
    protected void loadResources() {

        registerFont();
        font = new Font("Fancy Pants NF", Font.PLAIN, Window.getGameScale(75));

        new Images();
        player = new Player();

        //init these in loading resources, as the images have to be loaded to be displayed
        BackGroundHelper.initScrollingBackground();
        BackGroundHelper.stopScrollingBackground();

        new Entities();

    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, Window.getWidth(), Window.getHeight());

        BackGroundHelper.drawScrollingBackground(g);

        //Draw player when loaded
        if (player != null) {
            player.draw(g);
        }

        //draw game title
        if (font != null) {
            g.setFont(font);

            g.setColor(Color.red.darker().darker());
            g.drawString("Kick A Brick",
                    Window.getWidth() / 2 - g.getFontMetrics().stringWidth("Kick A Brick") / 2,
                    (int) ((double) Window.getHeight() / 3d));

            g.setColor(new Color(1f, 1f, 1f, .7f));
            g.drawString("Kick A Brick",
                    Window.getWidth() / 2 - g.getFontMetrics().stringWidth("Kick A Brick") / 2 - 2,
                    (int) ((double) Window.getHeight() / 3d) - 2);

            if (isDoneLoadingResources()) {
                if (offsetPlayerX >= 0) {

                    g.setFont(new Font("Fancy Pants NF", Font.PLAIN, Window.getGameScale(35)));
                    g.drawString("Click to Start",
                            Window.getWidth() / 2 - g.getFontMetrics().stringWidth("Click to Start") / 2,
                            Window.getHeight() - (int) ((double) Window.getHeight() / 8d));
                }
            }
        }

        if (!isDoneLoadingResources())
            Util.drawRotatedImage(loadingIcon, g,
                    Window.getWidth() - Window.getGameScale(150),
                    Window.getHeight() - Window.getGameScale(150),
                    loadRotation, Window.getGameScale(98), true);

        //draw fading black screen when all is done and loaded
        g.setColor(new Color(0f, 0f, 0f, alphaFade));
        g.fillRect(0, 0, Window.getWidth(), Window.getHeight());

    }

    @Override
    public void update() {
        super.update();

        if (!isDoneLoadingResources())
            loadRotation -= 5;

        BackGroundHelper.updateScrollingBackground();

        //scroll background when player is centered
        if (offsetPlayerX >= 0d)
            BackGroundHelper.resumeScrollingBackground();

        //update player position
        //set offset for player once player is loaded
        if (player != null) {
            player.update();
            if (queuePlayer && offsetPlayerX < 0) {
                offsetPlayerX += 1d;
                player.drawOffsetX(offsetPlayerX);
            }
            if (!queuePlayer) {
                queuePlayer = true;
                offsetPlayerX = Window.getGameScale(-300);
                player.drawOffsetX(offsetPlayerX);
            }
        }


        if (isDoneLoadingResources() && offsetPlayerX >= 0) {

            if (MouseHandler.click) {
                gsh.changeGameState(GameStateHandlerKick.GAME);
            }
        }
    }

    private void registerFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        InputStream stream = null;
        stream = GameStateMenu.class.getClassLoader().getResourceAsStream("FancyPants.ttf");

        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, stream));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
