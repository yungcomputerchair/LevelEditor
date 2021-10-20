package us.gentasaur.leveleditor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NewLevelDialog extends JDialog {
	
	JDialog dialog = this;
	LevelEditorFrame lef;
	JTextField xSize;
	JTextField ySize;
	
	public NewLevelDialog(LevelEditorFrame parent) {
		super(parent, true);
		lef = parent;
		
		this.setTitle("New Level");
		this.setPreferredSize(new Dimension(400, 150));
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		JComponent xSizePanel = new JPanel();
		JLabel xSizeLabel = new JLabel("Level width: ");
		xSize = new JTextField("32", 3);
		xSizePanel.add(xSizeLabel);
		xSizePanel.add(xSize);
		xSizePanel.setAlignmentX(Container.CENTER_ALIGNMENT);
		
		JComponent ySizePanel = new JPanel();
		JLabel ySizeLabel = new JLabel("Level height: ");
		ySize = new JTextField("22", 3);
		ySizePanel.add(ySizeLabel);
		ySizePanel.add(ySize);
		ySizePanel.setAlignmentX(Container.CENTER_ALIGNMENT);
		
		JComponent buttons = new JPanel();
		buttons.add(new JButton(new CreateAction()));
		buttons.add(new JButton(new CancelAction()));
		buttons.setAlignmentX(Container.CENTER_ALIGNMENT);
		
		this.add(xSizePanel);
		this.add(ySizePanel);
		this.add(buttons);
		this.pack();
		this.setVisible(true);
	}
	
	private class CreateAction extends AbstractAction {
		
		public CreateAction() {
			super("Create");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int w, h = -1;
			w = Integer.parseInt(xSize.getText());
			h = Integer.parseInt(ySize.getText());
			if(w > 0 && h > 0) {
				lef.initLevel(w, h);
				dialog.dispose();
			}
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
