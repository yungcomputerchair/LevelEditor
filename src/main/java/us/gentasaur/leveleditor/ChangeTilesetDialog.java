package us.gentasaur.leveleditor;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ChangeTilesetDialog extends JDialog {
	public ChangeTilesetDialog(JFrame parent) {
		super(parent, true);
		
		this.setTitle("Change Tileset");
		this.setSize(400, 150);
		this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}
}
