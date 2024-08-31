package pack;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;


public class FilePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JButton FileInput;
	JButton changeButton;
	
	static int[] BeatList;
	static int[] TempoList;
	
	FilePanel(){
		this.setLayout(null);
		this.setBackground(Color.white);
	}
	
	public void prepareComponents() {
		FileInput = new JButton();
		changeButton = new JButton();
		
		FileInput.setText("ファイルを選択");
		FileInput.setBackground(Color.green);
		FileInput.setOpaque(true);
		FileInput.setBounds(x_pos(Metronome.WIDTH, 400), 400, 400, 50);
		
		changeButton.setText("変更");
		changeButton.setBackground(Color.yellow);
		changeButton.setOpaque(true);
		changeButton.setBounds(x_pos(Metronome.WIDTH, 50)-350, 50, 50, 50);
		
		this.add(FileInput);
		this.add(changeButton);
		
		FileInput.addActionListener(new FileInputListener());
		changeButton.addActionListener(new ChangeButtonListener());
	}
	
	private class FileInputListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// FileDialogのインスタンスを作成
			FileDialog fileDialog = new FileDialog((Frame) null, "ファイルを選択", FileDialog.LOAD);
			// ダイアログを表示
			fileDialog.setVisible(true);
			
			//ユーザがファイルを選択した場合、選択されたファイルのパスを取得
			String selectedFile = fileDialog.getFile();
			String fullPath = null;
			if(selectedFile != null) {
				String directory = fileDialog.getDirectory();
				fullPath = directory + selectedFile;
				
				//ファイル内容読み込み
				try(BufferedReader br = new BufferedReader(new FileReader(fullPath))){
					int linenum = 1;;
					//テンポ羅列の処理
					String line = br.readLine();
					while(line != null) {
						//カンマ区切りの文字列を配列に分割
						//拍子を配列に格納
						if(linenum == 1) {
							String[] beatStrings = line.split(",");
							BeatList = new int[beatStrings.length];
							for(int j = 0; j < BeatList.length; j++) {
								BeatList[j] = Integer.parseInt(beatStrings[j].trim());
							}
						}
						line = br.readLine();
						linenum++;
						//テンポを配列に格納
						if(linenum == 2) {
							String[] numberStrings = line.split(",");
							TempoList = new int[numberStrings.length];
							//文字列を数値に変換して格納
							for(int i=0; i < TempoList.length; i++) {
								TempoList[i] = Integer.parseInt(numberStrings[i].trim());
							}
						}
					}
					Metronome.playPanel.prepareComponents();
					Main.metronome.setFrontScreenAndFocus(ScreenMode.PLAY);
				}catch(IOException | NumberFormatException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	
	public class ChangeButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.metronome.setFrontScreenAndFocus(ScreenMode.MAIN);
		}
	}
	
	private static int x_pos(int windowwidth, int componentswidth) {
		return ((windowwidth - componentswidth) / 2) ;
	}
}
