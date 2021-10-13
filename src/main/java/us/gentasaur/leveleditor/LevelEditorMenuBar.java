package us.gentasaur.leveleditor;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class LevelEditorMenuBar extends JMenuBar {
	
	private LevelEditorFrame parent;
	
	public LevelEditorMenuBar(LevelEditorFrame frame) {
		super();
		parent = frame;
		
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(new FileNewAction()));
		file.add(new JMenuItem(new FileOpenAction()));
		
		JMenu tools = new JMenu("Tools");
		tools.add(new JMenuItem(new ToolsChangeTilesetAction()));
		
		JMenu help = new JMenu("Help");
		
		this.add(file);
		this.add(tools);
		this.add(help);
	}
	
	private class FileNewAction extends AbstractAction {
		public FileNewAction() {
			super("New...");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("FILE->NEW");
		}
	}
	
	private class FileOpenAction extends AbstractAction {
		public FileOpenAction() {
			super("Open Level...");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			if(fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION)
				return;
			File file = fc.getSelectedFile();
			System.out.println(file.getAbsolutePath());
		}
	}
	
	private class ToolsChangeTilesetAction extends AbstractAction {
		public ToolsChangeTilesetAction() {
			super("Change Tileset...");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new ChangeTilesetDialog(parent);
		}
	}
}
