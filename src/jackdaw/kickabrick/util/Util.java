package jackdaw.kickabrick.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Util {

	/**
	 * a rotated image will always be drawn in it's size*size square.
	 * that square never changes.
	 * a 45° flipped image will therefore be drawn smaller, because the 
	 * diagonal of an image is always bigger then one of the sides.
	 * 
	 * Thus we create a canvas, with sides as big as the diagonal of the image,
	 * so it can fit in, and will not be distorted when rotated 45°
	 * */
	public static void drawRotatedImage(BufferedImage img, Graphics2D g, int x, int y, int rotation, int scale, boolean centerImage){
		drawRotating(img, g, x, y, 0.5, 0.5, rotation, scale, centerImage);
	}
	
	public static void drawWithRotationLeftTop(BufferedImage img, Graphics2D g, int x, int y, int rotation, int scale, boolean centerImage){
		drawRotating(img, g, x, y, 0, 0, rotation, scale, centerImage);
	}
	
	public static void drawWithRotationRightBottom(BufferedImage img, Graphics2D g, int x, int y, int rotation, int scale, boolean centerImage){
		drawRotating(img, g, x, y, 1, 1, rotation, scale, centerImage);
	}
	
	public static void drawWithRotationLeftBottom(BufferedImage img, Graphics2D g, int x, int y, int rotation, int scale, boolean centerImage){
		drawRotating(img, g, x, y, 0, 1, rotation, scale, centerImage);
	}
	
	public static void drawWithRotationRightTop(BufferedImage img, Graphics2D g, int x, int y, int rotation, int scale, boolean centerImage){
		drawRotating(img, g, x, y, 1, 0, rotation, scale, centerImage);
	}

	/**allows to choose one of the images angle to rotate around, 0,0 being top left, and 1,1 bottom right*/
	public static void drawRotating(BufferedImage img, Graphics2D g, int x, int y, double ux, double uy, int rotation, int scale, boolean centerImage){

		// Rotation information
		double rotationRequired = Math.toRadians (rotation);

		//get size of original image
		int imgSize = img.getWidth();

		//get scale ratio to scale canvas
		double scaleForImage = scale / (double)imgSize;

		//get size of diagonal from image, for canvas size, to be able to fit rotated image in.
		double diagonal = imgSize* Math.sqrt(2) * scaleForImage * 2;

		//new canvas that is big enough to fit in an image that is rotated 45°,
		//and any four translated possibilities
		BufferedImage canvas = new BufferedImage(
				(int)(diagonal ), 
				(int)(diagonal ), 
				
				img.getType());

		//get grapchics from the canvas
		Graphics2D g2d = (Graphics2D) canvas.getGraphics();

		//rotate canvas internally
		g2d.rotate(rotationRequired, 
				(int)(diagonal/2), 
				(int)(diagonal/2));

		//get offset for image inside of canvas.
		int offsetX = (int)(((double)imgSize*scaleForImage) * ux);
		int offsetY = (int)(((double)imgSize*scaleForImage) * uy);

		//finally, draw the image to the canvas
		g2d.drawImage(img, 
				(int)(diagonal/2) - offsetX, 
				(int)(diagonal/2) - offsetY, 
				scale, //no need for window.getgamescael(x) here, as the scale has been passed down from the kickable object
				scale,
				null);

		int centerX = (int) (centerImage ? diagonal/2 - offsetX : 0);
		int centerY = (int) (centerImage ? diagonal/2 - offsetY : 0);

		//paint the canvas on our screen
		g.drawImage(canvas,
				x - centerX, y-centerY, 
				null);
	}
}
