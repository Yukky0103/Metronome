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
	int IconWidth = ResourceManager.iconstart.getIconWidth();
	int IconHeight = ResourceManager.iconstart.getIconHeight();
	//動的な配列領域の確保(テンポ用)
	static List<Float> FullTempoList = new ArrayList<>(); //一拍ごとのテンポの値
	
	//動的な配列領域の確保(拍子用)
	static List<Integer> FullSoundList = new ArrayList<>(); //一拍における拍子の種類
	
	public PlayPanel() {
		this.setLayout(null);
		this.setBackground(Color.white);
	}
	
	public void prepareComponents() {
		NowTempo = new JLabel();
		StartStopButton = new JButton();
		
		NowTempo.setBounds(x_pos(Metronome.WIDTH, 150), 150, 150, 150);
		NowTempo.setBorder(border);
		
		StartStopButton.setIcon(ResourceManager.iconstart);
		StartStopButton.setContentAreaFilled(false);
		StartStopButton.setBorderPainted(false);
		StartStopButton.setFocusPainted(false);
		StartStopButton.setBounds(x_pos(Metronome.WIDTH, IconWidth), 350, IconWidth, IconHeight);
		
		this.add(NowTempo);
		this.add(StartStopButton);
		
		StartStopButton.addActionListener(new PlayStartStopButtonListener());
	}
	
	public void TimerController() {
		//タイマーの初期化
				timer = new Timer((int)t(FullTempoList.get(currentIndex)), new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(isRunning) {
								//現在のテンポに基づいて処理を実行
								NowTempo.setText("Tempo:" + FullTempoList.get(currentIndex));
								
								if(FullSoundList.get(currentIndex) == 0) {
									PlayClickSound(ResourceManager.NormalSoundFile);
								}else if(FullSoundList.get(currentIndex) == 1) {
									PlayClickSound(ResourceManager.BeatSoundFile);
								}else {
									PlayClickSound(ResourceManager.SectionSoundFile);
								}
								
								//次のテンポに移動
								currentIndex++;
								//最後に行った時の処理
								if(currentIndex >= FullTempoList.size()) {
									isRunning = !isRunning;
									currentIndex = 0;
									timer.stop();
									StartStopButton.setIcon(ResourceManager.iconstart);
									StartStopButton.setBounds(x_pos(Metronome.WIDTH, IconWidth), 350, IconWidth, IconHeight);
								}
								//タイマーのディレイを更新
								timer.setDelay((int)t(FullTempoList.get(currentIndex)));
								System.out.println((int)t(FullTempoList.get(currentIndex)));
							}
						}
				});
	}
	
	public void SetForPlay() {
		NowTempo.setText("Tempo:" + FilePanel.TempoList[0]); 
		ListMake();
		TimerController();
	}
	
	public void ListMake() {
		for(int i=0; i < FilePanel.BeatList.length; i++) { //拍子リストの長さ分=テンポの数
			for(int j=0; j < FilePanel.BeatList[i]; j++) { //繰り返す回数
				FullTempoList.add(FilePanel.TempoList[i]);
				FullSoundList.add(FilePanel.SoundList[i]);
			}
		}
	}
	
	private class PlayStartStopButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			isRunning = !isRunning;
			if(isRunning) {
				timer.start();
				StartStopButton.setIcon(ResourceManager.iconstop);
				StartStopButton.setBounds(x_pos(Metronome.WIDTH, IconWidth), 350, IconWidth, IconHeight);
			}else {
				timer.stop();
				StartStopButton.setIcon(ResourceManager.iconstart);
				StartStopButton.setBounds(x_pos(Metronome.WIDTH, IconWidth), 350, IconWidth, IconHeight);
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
	
	private float t(float tempo) {
		return 60000 / tempo;
	}
}
