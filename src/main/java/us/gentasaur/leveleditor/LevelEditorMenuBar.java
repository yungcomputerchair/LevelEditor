package us.gentasaur.leveleditor;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class LevelEditorMenuBar extends JMenuBar {
	
	private JFrame parent;
	
	public LevelEditorMenuBar(JFrame frame) {
		super();
		parent = frame;
		
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(new FileNewAction()));
		file.add(new JMenuItem(new FileOpenAction()));
		
		JMenu help = new JMenu("Help");
		
		this.add(file);
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
			super("Open...");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("FILE->OPEN");
			
			JFileChooser fc = new JFileChooser();
			if(fc.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION)
				return;
			File file = fc.getSelectedFile();
			System.out.println(file.getAbsolutePath());
		}
	}
}
