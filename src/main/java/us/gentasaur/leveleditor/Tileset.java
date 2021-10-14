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
	
	public static Tileset generateDefaultTileset(int col1, int col2) {
		int size = 800;
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y < size; y++)
			for(int x = 0; x < size; x++)
				img.setRGB(x, y, x % 2 == 0 ? col1 : col2);
		return new Tileset(img, 1);
	}
}
