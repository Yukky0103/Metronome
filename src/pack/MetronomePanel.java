package pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MetronomePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	//定数フィールド
	Color backgroundColor = Color.white;
	
	JLabel tempolabel;
	JButton startstopButton;
	JSlider tempoSlider;
	JButton increaseTempoButton;
	JButton decreaseTempoButton;
	JButton increase5TempoButton;
	JButton decrease5TempoButton;
	JButton changeButton;
	boolean isRunning;
	int Tempo = 60;
	Timer timer;
	
	ImageIcon iconssbstart = new ImageIcon(getClass().getClassLoader().getResource("再生.png"));
	ImageIcon iconssbstop = new ImageIcon(getClass().getClassLoader().getResource("停止.png"));
	
	File soundFile = new File("sound/clicksound.wav");
	//コンポーネント
	Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	//リスナー
	
	MetronomePanel(){
		this.setLayout(null); //レイアウトの設定
		this.setBackground(backgroundColor); //背景の色
		playClickSounds();  //キャッシュが問題の可能性 別のデータに変更した際の影響は不明
	}
	
	public void prepareComponents() {
		tempolabel = new JLabel();
		startstopButton = new JButton();
		tempoSlider = new JSlider();
		increaseTempoButton = new JButton();
		decreaseTempoButton = new JButton();
		increase5TempoButton = new JButton();
		decrease5TempoButton = new JButton();
		changeButton = new JButton();
		
		tempolabel.setHorizontalAlignment(SwingConstants.CENTER);
		tempolabel.setVerticalAlignment(SwingConstants.CENTER);
		tempolabel.setText("Tempo:" + Tempo);
		tempolabel.setHorizontalTextPosition(JLabel.CENTER);
		tempolabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		tempolabel.setBounds(x_pos(Metronome.WIDTH, 150), 150, 150, 100);
		tempolabel.setBorder(border);
		
		startstopButton = new JButton();
		startstopButton.setIcon(iconssbstart);
		startstopButton.setContentAreaFilled(false); // ボタンのコンテンツ領域の塗りつぶしを無効にする
	    startstopButton.setBorderPainted(false); // ボタンの境界線を描画しない
	    startstopButton.setFocusPainted(false); // フォーカスが当たったときの枠線を描画しない
		startstopButton.setBounds(x_pos(Metronome.WIDTH, iconssbstart.getIconWidth()), 350, iconssbstart.getIconWidth(), iconssbstart.getIconHeight());
		
		increaseTempoButton = new JButton();
		increaseTempoButton.setBackground(Color.green);
		increaseTempoButton.setText("+1");
		increaseTempoButton.setOpaque(true);
		increaseTempoButton.setBounds(x_pos(Metronome.WIDTH, 50)-50, 550, 50, 50);
		
		decreaseTempoButton = new JButton();
		decreaseTempoButton.setBackground(Color.yellow);
		decreaseTempoButton.setText("-1");
		decreaseTempoButton.setOpaque(true);
		decreaseTempoButton.setBounds(x_pos(Metronome.WIDTH, 50)+50, 550, 50, 50);
		
		increase5TempoButton = new JButton();
		increase5TempoButton.setBackground(Color.green);
		increase5TempoButton.setText("+5");
		increase5TempoButton.setOpaque(true);
		increase5TempoButton.setBounds(x_pos(Metronome.WIDTH, 50)-100, 550, 50, 50);
		
		decrease5TempoButton = new JButton();
		decrease5TempoButton.setBackground(Color.yellow);
		decrease5TempoButton.setText("-5");
		decrease5TempoButton.setOpaque(true);
		decrease5TempoButton.setBounds(x_pos(Metronome.WIDTH, 50)+100, 550, 50, 50);
		
		tempoSlider = new JSlider();
		tempoSlider.setMaximum(240);
		tempoSlider.setMinimum(40);
		tempoSlider.setBackground(Color.cyan);
		tempoSlider.setOpaque(true);
		tempoSlider.setBounds(x_pos(Metronome.WIDTH, 600), 500, 600, 50);
		
		changeButton = new JButton();
		changeButton.setBackground(Color.green);
		changeButton.setText("変更");
		changeButton.setOpaque(true);
		changeButton.setBounds(x_pos(Metronome.WIDTH, 50)-250, 30, 50, 50);
		
		this.add(tempolabel);
		this.add(startstopButton);
		this.add(increaseTempoButton);
		this.add(decreaseTempoButton);
		this.add(tempoSlider);
		this.add(increase5TempoButton);
		this.add(decrease5TempoButton);
		this.add(changeButton);
		
		startstopButton.addActionListener(new StartStopButtonListener());
		increaseTempoButton.addActionListener(new IncreaseTempoButtonListener());
		decreaseTempoButton.addActionListener(new DecreaseTempoButtonListener());
		tempoSlider.addChangeListener(new TempoSliderListener());
		increase5TempoButton.addActionListener(new Increase5TempoButtonListener());
		decrease5TempoButton.addActionListener(new Decrease5TempoButtonListener());
		changeButton.addActionListener(new ChangeButtonListener());
		
		timer = new Timer(t(Tempo), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					playClickSounds();
				}
		});
	}
	
	private class StartStopButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			isRunning = !isRunning;
			if(isRunning) {
				timer.setDelay(t(Tempo));
				timer.start();
				startstopButton.setIcon(iconssbstop);
				startstopButton.setBounds(x_pos(Metronome.WIDTH, iconssbstart.getIconWidth()), 350, iconssbstop.getIconWidth(), iconssbstop.getIconHeight());
				//動作開始時の動作
			}else {
				timer.stop();
				startstopButton.setIcon(iconssbstart);
				startstopButton.setBounds(x_pos(Metronome.WIDTH, iconssbstart.getIconWidth()), 350, iconssbstart.getIconWidth(), iconssbstart.getIconHeight());
				//動作終了時の動作
			}
		}
	}
	
	private class IncreaseTempoButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Tempo < 240) {
				Tempo++;
				tempoSlider.setValue(Tempo);
				tempolabel.setText("Tempo:" + Tempo);
				timer.setDelay(t(Tempo));
				//メトロノームテンポ変更
			}
		}
	}
	
	private class DecreaseTempoButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Tempo > 40) {
				Tempo--;
				tempoSlider.setValue(Tempo);
				tempolabel.setText("Tempo:" + Tempo);
				timer.setDelay(t(Tempo));
				//メトロノームテンポ変更
			}
		}
	}
	
	private class TempoSliderListener implements ChangeListener{
		public void stateChanged(ChangeEvent e) {
			Tempo = tempoSlider.getValue();
			tempolabel.setText("Tempo:" + Tempo);
			timer.setDelay(t(Tempo));
		}
	}
	
	private class Increase5TempoButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Tempo < 236) {
				Tempo = Tempo + 5;
			}else if((Tempo > 235) & (Tempo <= 240)){
				Tempo = 240;
			}
			tempoSlider.setValue(Tempo);
			tempolabel.setText("Tempo:" + Tempo);
			timer.setDelay(t(Tempo));
		}
	}
	private class Decrease5TempoButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Tempo > 44) {
				Tempo = Tempo - 5;
			}else if((Tempo < 45) & (Tempo >= 40)){
				Tempo = 40;
			}
			tempoSlider.setValue(Tempo);
			tempolabel.setText("Tempo:" + Tempo);
			timer.setDelay(t(Tempo));
		}
	}
	private void playClickSounds() {
		try {
			AudioInputStream audioin = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioin);
			clip.start();
		}catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	private static int x_pos(int windowwidth, int componentswidth) {
		return ((windowwidth - componentswidth) / 2) ;
	}
	private static int t(int tempo) {
		return 60000 / tempo;
	}
	
	private class ChangeButtonListener implements ActionListener{
		public void  actionPerformed(ActionEvent e) {
			Main.metronome.setFrontScreenAndFocus(ScreenMode.FRFI);
		}
	}
}