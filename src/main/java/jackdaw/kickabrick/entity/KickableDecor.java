package jackdaw.kickabrick.entity;

import jackdaw.kickabrick.rsrcmngr.Background;

import java.awt.*;
import java.awt.image.BufferedImage;

public class KickableDecor {

    protected double posX, posY;
    private Kickable affiliatedKickable;
    private BufferedImage icon;
    private int scale;

    public KickableDecor(BufferedImage img, Kickable k, int scale) {

        affiliatedKickable = k;
        icon = img;
        this.scale = scale;

        posX = affiliatedKickable.getPosX();

        posY =
                affiliatedKickable.getPosY() +
                        affiliatedKickable.getSize() -
                        scale -
                        //bottom background is 2 pixels high
                        //and decor is drawn in the middle.
                        //needs to be corrected by 2pixels / half(2) = 1
                        Background.scale * 1;

        System.out.println(Background.scale + " BACKGROUND SCALE");
    }

    public void draw(Graphics2D g) {

//		g.setColor(Color.black);
//		g.fillRect((int)posX, (int)(posY), scale, scale);

        g.drawImage(icon, (int) posX, (int) posY, scale, scale, null);

    }

    public void update(boolean move) {
        if (move)
            posX -= 1d;
    }
}
