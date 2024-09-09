package pack;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Metronome extends JFrame{
	
	ScreenMode screenMode = ScreenMode.MAIN;
	
	final static int HEIGHT = 800;
	final static int WIDTH = 800;
	
	CardLayout layout  =new CardLayout();
	
	MetronomePanel metronomePanel;
	FilePanel filePanel;
	static PlayPanel playPanel;
	
	Metronome(){
		//タイトルとアイコン
		this.setTitle("メトロノーム");
		//ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("ファイル名.拡張子"));
		//this.setIconImage(icon.getImage());
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false); //画面サイズ変更可能か
		this.getContentPane().setBackground(Color.white); //背景の色 詳しくするにはnew Color(0xFFFFFF)
		
		this.setLayout(layout); //紙芝居のように設定
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); //サイズ設定
		this.pack(); //自動サイズ設定
		this.setLocationRelativeTo(null); //起動時のスクリーン位置 詳しくするにはthis.setLocation(450, 50)
	}
	
	public void preparePanels() {
		//パネルの準備
		metronomePanel = new MetronomePanel();
		filePanel = new FilePanel();
		playPanel = new PlayPanel();
		this.add(metronomePanel, "メトロノーム画面");
		this.add(filePanel, "ファイル画面");
		this.add(playPanel, "再生画面");
		this.pack();
	}
	
	public void prepareComponents() {
		metronomePanel.prepareComponents();
		filePanel.prepareComponents();
		playPanel.prepareComponents();
	}
	
	public void setFrontScreenAndFocus(ScreenMode s) {
		screenMode = s;
		switch(screenMode) {
		case MAIN:
			layout.show(this.getContentPane(), "メトロノーム画面");
			metronomePanel.requestFocus();
			break;
		case FRFI:
			layout.show(this.getContentPane(), "ファイル画面");
			filePanel.requestFocus();
			break;
		case PLAY:
			layout.show(this.getContentPane(), "再生画面");
			playPanel.requestFocus();
			break;
		}
		
	}
}



