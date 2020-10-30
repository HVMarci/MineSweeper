package Minesweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Szoveg extends Text {
	public Szoveg(double x, double y) {
		// biztos ami biztos
		super();
		
		// alapcuccok
		this.setX(x*40+6+10);
		this.setY(y*40+40-6+30);
		this.setFill(Color.BLACK);
		this.setFont(new Font(38));
		this.setTextAlignment(TextAlignment.CENTER);
	}
}
