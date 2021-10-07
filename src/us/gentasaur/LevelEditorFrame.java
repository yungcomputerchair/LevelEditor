package us.gentasaur;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LevelEditorFrame extends JFrame {
	public LevelEditorFrame() {
		super("LevelEditor");
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(new LevelEditorMenuBar(this));
		
		this.setLayout(null);
		
		this.setVisible(true);
	}
}
