package Minesweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BombakKijelzo extends Text {
	
	private final String preText = "Bejelölt bombák: ";
	
	public BombakKijelzo() {
		super();
		
		this.setX(10);
		this.setY(20);
		this.setFill(Color.BLACK);
		this.setFont(new Font(20));
		
		this.setText(preText);
	}
	
	public void changeValue(int a, int b) {
		this.setText(preText + a + " / " + b);
	}
}
