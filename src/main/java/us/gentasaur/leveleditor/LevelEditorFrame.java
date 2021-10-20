package us.gentasaur.leveleditor;

import static us.gentasaur.leveleditor.Constants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class LevelEditorFrame extends JFrame {
	
	private ArrayList<Integer[]> level;
	private int levelW, levelH;
	private Tileset tileset;
	
	private LevelPanel levelPanel;
	private JSlider floorSlider;
	
	public LevelEditorFrame() {
		super("LevelEditor");
		level = null;
		levelW = 0;
		levelH = 0;
		tileset = Tileset.generateDefaultTileset(Constants.NEON_PINK, 0);
		
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
		floorSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				levelPanel.repaint();
				levelPanel.grabFocus();
			}
		});
		
		this.add(levelPanel);
		this.add(floorSlider);
		//this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.updateWindow();
		
		new ChangeTilesetDialog(this);
	}
	
	public void initLevel(int w, int h) {
		levelW = w;
		levelH = h;
		
		level = new ArrayList<Integer[]>();
		clearLevel();
	}
	
	public void clearLevel() {
		level.clear();
		level.add(LevelEditorFrame.getEmptyFloor(levelW, levelH));
		updateWindow();
	}
	
	public static Integer[] getEmptyFloor(int w, int h) {
		Integer[] tiles = new Integer[w * h];
		int empty = 0;
		for(int i = 0; i < w * h; i++) {
			tiles[i] = empty;
		}
		return tiles;
	}
	
	public int loadLevel(File file) {
	    byte[] dat = new byte[(int) file.length()];
	    DataInputStream dis;
		try {
			dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(dat);
		    dis.close();
		    
		    initLevel((int)(dat[0]) & 0xff, (int)(dat[1]) & 0xff);
		    level.clear();
		    int layers = (int)(dat[2]) & 0xff;
		    
		    for(int l = 0; l < layers; l++) {
		    	Integer[] tiles = new Integer[levelW * levelH];
		    	for(int y = 0; y < levelH; y++)
		    		for(int x = 0; x < levelW; x++)
		    			tiles[y * levelW + x] = (int)(dat[3 + l * levelW * levelH + y * levelW + x]) & 0xff;
		    	level.add(tiles);
		    }
		    
		    updateWindow();
		    return 0;
		} catch (IOException e) {
			// TODO add error dialog?
			e.printStackTrace();
			return 1;
		}
	}
	
	public void saveLevel(File file) {
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(new FileOutputStream(file));
			int layers = level.size();
			
			// header
			dos.write(levelW);
			dos.write(levelH);
			dos.write(layers);
			
			// body
			for(int l = 0; l < layers; l++)
				for(int y = 0; y < levelH; y++)
					for(int x = 0; x < levelW; x++)
						dos.write(level.get(l)[y * levelW + x]);
			
			dos.close();
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
	
	public boolean isLoaded() {
		return level != null;
	}
	
	private class LevelPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
		
		private int selected;
		private int mouseX, mouseY;
		
		public LevelPanel() {
			super();
			this.setBackground(Color.PINK);
			selected = 0;
			mouseX = -1;
			mouseY = -1;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.addKeyListener(this);
			this.setFocusable(true);
			this.grabFocus();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			if(level == null || tileset == null)
				return;
			Graphics2D gg = (Graphics2D)g;
			
			// draw level
			int floor = floorSlider.getValue();
			for(int y = 0; y < levelH; y++) {
				for(int x = 0; x < levelW; x++) {
					int tile = level.get(floor)[y * levelW + x];
					gg.drawImage(tileset.getTile(tile), x * SCALED_TILE_SIZE, y * SCALED_TILE_SIZE, SCALED_TILE_SIZE, SCALED_TILE_SIZE, null);
				}
			}
			
			// draw cursor
			gg.drawImage(tileset.getTile(selected), mouseX * SCALED_TILE_SIZE, mouseY * SCALED_TILE_SIZE, SCALED_TILE_SIZE, SCALED_TILE_SIZE, null);
			gg.setColor(COLOR_NEON_CYAN);
			gg.drawRect(mouseX * SCALED_TILE_SIZE, mouseY * SCALED_TILE_SIZE, SCALED_TILE_SIZE, SCALED_TILE_SIZE);
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {
			mouseX = -1;
			mouseY = -1;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.placeTile();
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {
			updateCursorCoords(e.getX(), e.getY());
			placeTile();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			updateCursorCoords(e.getX(), e.getY());
			this.repaint(); // for cursor drawing
		}
		
		private void updateCursorCoords(int mx, int my) {
			mouseX = mx / SCALED_TILE_SIZE;
			mouseY = my / SCALED_TILE_SIZE;
		}
		
		private void placeTile() {
			if(mouseX < 0 || mouseY < 0 || mouseX >= levelW || mouseY >= levelW)
				return; // out of bounds
			
			level.get(floorSlider.getValue())[mouseY * levelW + mouseX] = selected;
			this.repaint();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode())
			{
			case KEY_LEFT_BRACE: // delete last floor
				if(level != null && floorSlider.getMaximum() > 0) {
					level.remove(level.size() - 1);
					floorSlider.setMaximum(floorSlider.getMaximum() - 1);
				}
				break;
			case KEY_RIGHT_BRACE:
				if(level != null) {
					level.add(LevelEditorFrame.getEmptyFloor(levelW, levelH));
					floorSlider.setMaximum(floorSlider.getMaximum() + 1);
				}
				break;
			case KEY_LEFT_ARROW:
				selected--;
				if(selected < 0) selected = 0;
				this.repaint();
				break;
			case KEY_RIGHT_ARROW:
				selected++;
				if(selected >= tileset.getTotalTiles()) selected = tileset.getTotalTiles() - 1;
				this.repaint();
				break;
			default:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {}
	}
}
