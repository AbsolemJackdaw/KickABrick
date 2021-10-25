package jackdaw.kickabrick.player;

import framework.window.Window;
import jackdaw.kickabrick.rsrcmngr.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {

    private BufferedImage playerStill;
    private BufferedImage playerKick;
    private BufferedImage[] walking;

    private int animationWalkTimer;
    private int animationIndex = 0;

    private int smokeTimer;
    private int smokeIndex = 0;

    private boolean isWalking = true;
    private boolean isKicking = false;

    private double centerX, centerY, centerOffsetX;
    private int playerSize;

    private Rectangle box;

    //start with no extra leg power
    private double legForce = 0;

    public Player() {

        playerStill = Images.dude;
        playerKick = Images.dudeKick;

        walking = new BufferedImage[]{
                Images.walk[0],
                Images.walk[1],
                Images.walk[2],
                Images.walk[3],
                Images.walk[4],
                Images.walk[1],
                Images.walk[2],
                Images.walk[3],
        };

        playerSize = Window.getGameScale(256);

        centerX = Window.getWidth() * 0.15; //about a third of the screen width

        centerY = Window.getHeight() / 2f - 12f;

        box = new Rectangle(
                (int) centerX + Window.getGameScale(80),
                (int) centerY,
                Window.getGameScale(85),
                Window.getGameScale(256));
    }

    public void draw(Graphics2D g) {

        //check for if kicking before checking anything else, or it will never draw kicking
        drawCenteredPlayer(isWalking ? walking[animationIndex] : isKicking ? playerKick : playerStill, g);

        //g.draw(box);
    }

    public void update() {
        smokeTimer++;
        if (smokeTimer % 12 == 0)
            smokeIndex++;
        if (smokeIndex > Images.cig.length - 1)
            smokeIndex = 0;

        //prevent leak
        if (smokeTimer > Integer.MAX_VALUE / 2)
            smokeTimer = 0;

        if (isWalking)
            animationWalkTimer++;
        else
            animationWalkTimer = animationIndex = 0;

        if (animationWalkTimer % 12 == 0 && isWalking)
            animationIndex++;

        if (animationIndex > walking.length - 1)
            animationIndex = 0;
    }

    public void drawOffsetX(double d) {
        centerOffsetX = d;
    }

    public Rectangle getBox() {
        return box;
    }

    public void setWalking(boolean isWalking) {
        this.isWalking = isWalking;
    }

    private void drawCenteredPlayer(BufferedImage img, Graphics2D g) {
        g.drawImage(img,
                (int) centerX + (int) centerOffsetX, (int) centerY,
                playerSize, playerSize,
                null);

        g.drawImage(Images.cig[smokeIndex],
                ((int) centerX + (int) centerOffsetX) + Window.getGameScale(128 + 8), (int) centerY + harcodedCigoffset() - Window.getGameScale(4),
                playerSize / 4, playerSize / 4,
                null);
    }

    private int harcodedCigoffset() {
        switch (animationIndex) {
            case 1, 3, 5, 7 -> {
                return Window.getGameScale(4);
            }
            case 2, 6 -> {
                return Window.getGameScale(8);
            }
            default -> {
                return 0;
            }
        }
    }

    public void setKicking(boolean isKicking) {
        this.isKicking = isKicking;
    }

    public boolean isReadyToKick() {
        return isKicking;
    }

    public double getLegForce() {
        return legForce;
    }

    public void addLegForce(double legForce) {
        this.legForce += legForce;
    }
}
