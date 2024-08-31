package pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class PlayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel NowTempo;
	JButton StartStopButton;
	Border border = BorderFactory.createLineBorder(Color.black, 2);
	Timer timer;
	boolean isRunning = false;
	int currentIndex = 0;
	
	//動的な配列領域の確保
	static List<Integer> FullTempoList = new ArrayList<>();
	
	ImageIcon iconstart = new ImageIcon(getClass().getClassLoader().getResource("再生.png"));
	ImageIcon iconstop = new ImageIcon(getClass().getClassLoader().getResource("停止.png"));
	
	File soundFile = new File("sound/clicksound.wav");
	
	public PlayPanel() {
		this.setLayout(null);
		this.setBackground(Color.white);
	}
	
	public void prepareComponents() {
		ListMake();
		
		NowTempo = new JLabel();
		StartStopButton = new JButton();
		
		NowTempo.setText("Tempo:" + FilePanel.TempoList[0]);
		NowTempo.setBounds(x_pos(Metronome.WIDTH, 150), 150, 150, 150);
		NowTempo.setBorder(border);
		
		StartStopButton.setIcon(iconstart);
		StartStopButton.setContentAreaFilled(false);
		StartStopButton.setBorderPainted(false);
		StartStopButton.setFocusPainted(false);
		StartStopButton.setBounds(x_pos(Metronome.WIDTH, iconstart.getIconWidth()), 350, iconstart.getIconWidth(), iconstart.getIconHeight());
		
		this.add(NowTempo);
		this.add(StartStopButton);
		
		StartStopButton.addActionListener(new PlayStartStopButtonListener());
		
		//タイマーの初期化
		timer = new Timer(t(FullTempoList.get(currentIndex)), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isRunning) {
						//現在のテンポに基づいて処理を実行
						NowTempo.setText("Tempo:" + FullTempoList.get(currentIndex));
						PlayClickSound(soundFile);
						//次のテンポに移動
						currentIndex++;
						//最後に行った時の処理
						if(currentIndex >= FullTempoList.size()) {
							isRunning = !isRunning;
							currentIndex = 0;
							timer.stop();
							StartStopButton.setIcon(iconstart);
							StartStopButton.setBounds(x_pos(Metronome.WIDTH, iconstart.getIconWidth()), 350, iconstart.getIconWidth(), iconstart.getIconHeight());
						}
						//タイマーのディレイを更新
						timer.setDelay(t(FullTempoList.get(currentIndex)));
					}
				}
		});
	}
	
	public static void ListMake() {
		for(int i=0; i < FilePanel.BeatList.length; i++) { //拍子リストの長さ分=テンポの数
			for(int j=0; j < FilePanel.BeatList[i]; j++) { //繰り返す回数
				FullTempoList.add(FilePanel.TempoList[i]);
			}
		}
	}
	
	private class PlayStartStopButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			isRunning = !isRunning;
			if(isRunning) {
				timer.start();
				StartStopButton.setIcon(iconstop);
				StartStopButton.setBounds(x_pos(Metronome.WIDTH, iconstart.getIconWidth()), 350, iconstop.getIconWidth(), iconstop.getIconHeight());
			}else {
				timer.stop();
				StartStopButton.setIcon(iconstart);
				StartStopButton.setBounds(x_pos(Metronome.WIDTH, iconstart.getIconWidth()), 350, iconstart.getIconWidth(), iconstart.getIconHeight());
			}
		}
	}
	
	private void PlayClickSound(File sf) {
		try {
			AudioInputStream audioin = AudioSystem.getAudioInputStream(sf);
			Clip clip = AudioSystem.getClip();
			clip.open(audioin);
			clip.start();
		}catch(UnsupportedAudioFileException | IOException | LineUnavailableException uile) {
			uile.printStackTrace();
		}
	}
	
	private int x_pos(int Mw, int Cw) {
		return ((Mw - Cw) / 2);
	}
	
	private int t(int tempo) {
		return 60000 / tempo;
	}
}
