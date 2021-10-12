package us.gentasaur.leveleditor;
import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LevelEditorFrame extends JFrame {
	
	private ArrayList<Byte[]> level;
	private int levelX, levelY, levelW, levelH;
	
	public LevelEditorFrame() {
		super("LevelEditor");
		level = null;
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(new LevelEditorMenuBar(this));
		
		this.setLayout(null);
		
		this.setVisible(true);
	}
	
	public void initLevel(int x, int y, int w, int h) {
		levelX = x;
		levelY = y;
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
}
