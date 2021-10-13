package us.gentasaur.leveleditor;
import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LevelEditorFrame extends JFrame {
	
	private ArrayList<Byte[]> level;
	private int levelW, levelH;
	private Tileset tileset;
	
	public LevelEditorFrame() {
		super("LevelEditor");
		level = null;
		levelW = 0;
		levelH = 0;
		tileset = null;
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(new LevelEditorMenuBar(this));
		
		this.setLayout(null);
		
		this.setVisible(true);
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
	
	public void changeTileset(Tileset ts) {
		tileset = ts;
	}
}
