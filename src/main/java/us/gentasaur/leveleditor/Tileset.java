package us.gentasaur.leveleditor;

import java.awt.image.BufferedImage;

public class Tileset {

	private BufferedImage image;
	private int tileSize;
	
	public Tileset(BufferedImage img, int ts) {
		image = img;
		tileSize = ts;
	}
	
	private int getSheetSize() {
		return image.getWidth() / tileSize;
	}
	
	public BufferedImage getTile(int n) {
		int x = n % getSheetSize();
		int y = n / getSheetSize();
		return image.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
	}
}
