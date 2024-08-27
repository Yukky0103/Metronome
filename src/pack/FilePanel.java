package pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;


public class FilePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	File InputFile;
	
	JButton FileInput;
	
	FilePanel(){
		this.setLayout(null);
		this.setBackground(Color.green);
	}
	
	public void prepareComponents() {
		FileInput = new JButton();
		
		FileInput.setText("Input");
		FileInput.setBackground(Color.red);
		FileInput.setOpaque(true);
		FileInput.setBounds(x_pos(Metronome.WIDTH, 50), 400, 100, 100);
		
		this.add(FileInput);
		
		FileInput.addActionListener(new FileInputListener());
	}
	
	private class FileInputListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	private static int x_pos(int windowwidth, int componentswidth) {
		return ((windowwidth - componentswidth) / 2) ;
	}
}
