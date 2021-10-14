package us.gentasaur.leveleditor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class LevelEditorFrame extends JFrame {
	
	private static final int SCALED_TILE_SIZE = 32;
	
	private ArrayList<Byte[]> level;
	private int levelW, levelH;
	private Tileset tileset;
	
	private LevelPanel levelPanel;
	private JSlider floorSlider;
	
	public LevelEditorFrame() {
		super("LevelEditor");
		level = null;
		levelW = 0;
		levelH = 0;
		tileset = null;
		
		this.setSize(400, 300);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setJMenuBar(new LevelEditorMenuBar(this));
		this.setLayout(null);//new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		levelPanel = new LevelPanel();
		
		floorSlider = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
		floorSlider.setMajorTickSpacing(1);
		floorSlider.setPaintTicks(true);
		floorSlider.setPaintLabels(true);
		
		this.add(levelPanel);
		this.add(floorSlider);
		//this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.updateWindow();
	}
	
	public void initLevel(int w, int h) {
		levelW = w;
		levelH = h;
		
		level = new ArrayList<Byte[]>();
		clearLevel();
	}
	
	public void clearLevel() {
		level.clear();
		level.add(LevelEditorFrame.getEmptyFloor(levelW, levelH));
	}
	
	public static Byte[] getEmptyFloor(int w, int h) {
		Byte[] bytes = new Byte[w * h];
		byte empty = (byte) 0x00;
		for(int i = 0; i < w * h; i++) {
			bytes[i] = empty;
		}
		return bytes;
	}
	
	public void loadLevel(File file) {
	    byte[] dat = new byte[(int) file.length()];
	    DataInputStream dis;
		try {
			dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(dat);
		    dis.close();
		    
		    initLevel(dat[0], dat[1]);
		    level.clear();
		    int layers = dat[2];
		    
		    for(int l = 0; l < layers; l++) {
		    	Byte[] tiles = new Byte[levelW * levelH];
		    	for(int y = 0; y < levelH; y++)
		    		for(int x = 0; x < levelW; x++)
		    			tiles[y * levelW + x] = dat[l * levelW * levelH + y * levelW + x];
		    	level.add(tiles);
		    }
		    
		    updateWindow();
		} catch (IOException e) {
			// TODO add error dialog?
			e.printStackTrace();
		}
	}
	
	public void loadTileset(String filename, int tileSize) {
		try {
			BufferedImage image = ImageIO.read(new File(filename));
			tileset = new Tileset(image, tileSize);
			levelPanel.repaint();
		} catch(Exception e) {
			// TODO add error dialog?
			e.printStackTrace();
		}
	}
	
	private void updateWindow() {
		if(level != null) {
			floorSlider.setMaximum(level.size() - 1);
			this.setSize(levelW * SCALED_TILE_SIZE + 16, levelH * SCALED_TILE_SIZE + 130);
		}
		
		floorSlider.setBounds((int)(this.getWidth() * .025), this.getHeight() - 120, (int)(this.getWidth() * .925), 50);
		levelPanel.setBounds(0, 0, levelW * SCALED_TILE_SIZE, levelH * SCALED_TILE_SIZE);
		this.setLocationRelativeTo(null);
		levelPanel.repaint();
	}
	
	private class LevelPanel extends JPanel {
		
		public LevelPanel() {
			super();
			this.setBackground(Color.PINK);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			if(level == null || tileset == null)
				return;
			
			Graphics2D gg = (Graphics2D)g;
			int floor = floorSlider.getValue();
			for(int y = 0; y < levelH; y++) {
				for(int x = 0; x < levelW; x++) {
					byte tile = level.get(floor)[y * levelW + x];
					gg.drawImage(tileset.getTile(tile), x * SCALED_TILE_SIZE, y * SCALED_TILE_SIZE, SCALED_TILE_SIZE, SCALED_TILE_SIZE, null);
				}
			}
		}
	}
}
