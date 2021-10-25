package jackdaw.kickabrick.rsrcmngr;

import framework.resourceLoaders.ImageLoader;
import framework.resourceLoaders.ResourceLocation;

import java.awt.image.BufferedImage;

public class Images {

    public static BufferedImage dude;
    public static BufferedImage dudeWalk1;
    public static BufferedImage dudeWalk2;
    public static BufferedImage dudeKick;

    public static BufferedImage arrow;

    public static BufferedImage stone;
    public static BufferedImage fridge;
    public static BufferedImage glace;
    public static BufferedImage glace_decor;
    public static BufferedImage can;
    public static BufferedImage bottle;
    public static BufferedImage bottle_decor;
    public static BufferedImage pizza;
    public static BufferedImage pizza_decor;

    public static BufferedImage bin;

    public static BufferedImage overlay_night_background;

    public static BufferedImage stoep;
    public static BufferedImage[] bushes1 = new BufferedImage[3];
    public static BufferedImage[] bushes2 = new BufferedImage[3];
    public static BufferedImage[] trees = new BufferedImage[3];
    public static BufferedImage[] leaves = new BufferedImage[3];
    public static BufferedImage[] sky = new BufferedImage[3];

    public Images() {

        dude = ImageLoader.loadSprite(new ResourceLocation("/dude.png"));
        dudeWalk1 = ImageLoader.loadSprite(new ResourceLocation("/dudewalk1.png"));
        dudeWalk2 = ImageLoader.loadSprite(new ResourceLocation("/dudewalk2.png"));
        dudeKick = ImageLoader.loadSprite(new ResourceLocation("/dudekick.png"));

        overlay_night_background = ImageLoader.loadSprite(new ResourceLocation("/nightoverlay.png"));

        stoep = ImageLoader.loadSprite(new ResourceLocation("/background/stoep.png"));
        bushes1 = ImageLoader.loadArray(new ResourceLocation("/background/bush1/bush"), bushes1.length);
        bushes2 = ImageLoader.loadArray(new ResourceLocation("/background/bush2/bush"), bushes2.length);
        trees = ImageLoader.loadArray(new ResourceLocation("/background/trees/trees"), trees.length);
        leaves = ImageLoader.loadArray(new ResourceLocation("/background/leaves/leaves"), leaves.length);
        sky = ImageLoader.loadArray(new ResourceLocation("/background/sky/sky"), sky.length);

        stone = ImageLoader.loadSprite(new ResourceLocation("/kickables/stone.png"));
        fridge = ImageLoader.loadSprite(new ResourceLocation("/kickables/fridge.png"));
        glace = ImageLoader.loadSprite(new ResourceLocation("/kickables/icecream.png"));
        glace_decor = ImageLoader.loadSprite(new ResourceLocation("/kickables/decor/icecream.png"));
        can = ImageLoader.loadSprite(new ResourceLocation("/kickables/can.png"));
        pizza = ImageLoader.loadSprite(new ResourceLocation("/kickables/pizza.png"));
        pizza_decor = ImageLoader.loadSprite(new ResourceLocation("/kickables/decor/pizza.png"));
        bottle = ImageLoader.loadSprite(new ResourceLocation("/kickables/bottle.png"));
        bottle_decor = ImageLoader.loadSprite(new ResourceLocation("/kickables/decor/bottle.png"));

        bin = ImageLoader.loadSprite(new ResourceLocation("/bin.png"));

        arrow = ImageLoader.loadSprite(new ResourceLocation("/arrow.png"));
    }
}
