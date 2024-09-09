package pack;

import java.io.File;

import javax.swing.ImageIcon;

public class ResourceManager {
	static ImageIcon iconstart = new ImageIcon(ResourceManager.class.getClassLoader().getResource("再生.png"));
	static ImageIcon iconstop = new ImageIcon(ResourceManager.class.getClassLoader().getResource("停止.png"));
	
	static File NormalSoundFile = new File("sound/clicksound.wav");
	static File SectionSoundFile = new File("sound/First_S_clicksound.wav");
	static File BeatSoundFile = new File("sound/First_clicksound.wav");
	static File SilentSoundFile = new File("sound/Silent.wav");
}
