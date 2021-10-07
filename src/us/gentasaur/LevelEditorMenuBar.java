package us.gentasaur;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class LevelEditorMenuBar extends JMenuBar {
	public LevelEditorMenuBar() {
		super();
		
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
		}
	}
}
