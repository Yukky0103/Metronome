package pack;

public class Main {
	
	static Metronome metronome;
	public static void main(String[] args) {
		metronome = new Metronome(); //ウインドウを生成
		metronome.preparePanels(); // ペインに直接貼るパネルのみ生成
		metronome.prepareComponents(); //その他のコンポーネント作成
		metronome.setFrontScreenAndFocus(ScreenMode.MAIN); //最初の画面を表示
		metronome.setVisible(true); //ウインドウを可視化
    }

}
