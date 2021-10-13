package us.gentasaur.leveleditor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

@SuppressWarnings("serial")
public class ChangeTilesetDialog extends JDialog {
	
	JDialog dialog = this;
	LevelEditorFrame lef;
	JTextField fileNameField;
	JTextField tileSizeField;
	
	public ChangeTilesetDialog(LevelEditorFrame parent) {
		super(parent, true);
		lef = parent;
		
		this.setTitle("Change Tileset");
		this.setPreferredSize(new Dimension(400, 150));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		JComponent chooser = new JPanel();
		fileNameField = new JTextField("", 20);
		JButton fileBrowseButton = new JButton(new BrowseAction());
		fileNameField.setPreferredSize(fileBrowseButton.getPreferredSize());
		chooser.add(fileNameField);
		chooser.add(fileBrowseButton);
		chooser.setAlignmentX(Container.CENTER_ALIGNMENT);
		
		JComponent sizeSetter = new JPanel();
		JLabel sizeLabel = new JLabel("Tile sprite size: ");
		tileSizeField = new JTextField("16", 3);
		sizeSetter.add(sizeLabel);
		sizeSetter.add(tileSizeField);
		sizeSetter.setAlignmentX(Container.CENTER_ALIGNMENT);
		
		JComponent buttons = new JPanel();
		buttons.add(new JButton(new ConfirmAction()));
		buttons.add(new JButton(new CancelAction()));
		buttons.setAlignmentX(Container.CENTER_ALIGNMENT);
		
		this.add(chooser);
		this.add(sizeSetter);
		this.add(buttons);
		this.pack();
		this.setVisible(true);
	}
	
	private class BrowseAction extends AbstractAction {
		
		public BrowseAction() {
			super("Browse...");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new ImageFileFilter());
			if(fc.showOpenDialog(dialog) != JFileChooser.APPROVE_OPTION)
				return;
			File file = fc.getSelectedFile();
			fileNameField.setText(file.getAbsolutePath());
		}
		
		private class ImageFileFilter extends FileFilter {

			@Override
			public boolean accept(File f) {
				String filename = f.getName().toLowerCase();
				if(f.isDirectory() || filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".bmp"))
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return "Image files (.png, .bmp, .jpeg, .jpg)";
			}
		}
	}
	
	private class ConfirmAction extends AbstractAction {
		
		public ConfirmAction() {
			super("Apply");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lef.loadTileset(fileNameField.getText(), Integer.parseInt(tileSizeField.getText()));
			dialog.dispose();
		}
		
	}
	
	private class CancelAction extends AbstractAction {
		
		public CancelAction() {
			super("Cancel");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
		
	}
}
