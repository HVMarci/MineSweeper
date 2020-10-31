package Minesweeper;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JatekVege {
	public static void win() {
		// nyert�l
		System.out.println("Nyert�l!");
		// kik�k�tem a bomb�kat
		Main.showMines(Color.BLUE);
		// popup
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Main.getStage());
		VBox dialogVbox = new VBox(20);
		Text txt = new Text(" Nyert�l!");
		txt.setFont(new Font(40));
		txt.setFill(Color.RED);
		dialogVbox.getChildren().add(txt);
		Scene dialogScene = new Scene(dialogVbox, 200, 60);
		dialog.setScene(dialogScene);
		dialog.setTitle("Nyert�l!");
		dialog.show();
		// id� le�ll�t�sa
		Main.timer.stop();
	}

	public static void lose() {
		// vesztett�l
		System.out.println("Vesztett�l!");
		// kipiros�tom a bomb�kat
		Main.showMines(Color.RED);
		// popup
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Main.getStage());
		VBox dialogVbox = new VBox(20);
		Text txt = new Text(" Vesztett�l!");
		txt.setFont(new Font(40));
		txt.setFill(Color.RED);
		dialogVbox.getChildren().add(txt);
		Scene dialogScene = new Scene(dialogVbox, 200, 60);
		dialog.setScene(dialogScene);
		dialog.setTitle("Vesztett�l!");
		dialog.show();
		// id� le�ll�t�sa
		Main.timer.stop();
	}

	public static boolean isWin() {
		if (biztosakSzama(Main.getMezok()) == Main.meret * Main.meret - Main.bombakSzama)
			return true;
		else if (joTippek(Main.getMezok()) == Main.bombakSzama && rosszTippek(Main.getMezok()) == 0)
			return true;
		else
			return false;
	}

	public static int biztosakSzama(Mezo[] mezok) {
		int c = 0;

		for (Mezo mezo : mezok) {
			if (mezo.jeloles == Jeloles.BIZTOS) {
				c++;
			}
		}

		return c;
	}
	
	public static int joTippek(Mezo[] mezok) {
		int c = 0;
		
		for (Mezo mezo : mezok) {
			if (mezo.jeloles == Jeloles.JELOLT && mezo.isMine) 
				c++;
		}
		
		return c;
	}
	
	public static int rosszTippek(Mezo[] mezok) {
		int c = 0;
		
		for (Mezo mezo : mezok) {
			if (mezo.jeloles == Jeloles.JELOLT && !mezo.isMine)
				c++;
		}
		
		return c;
	}
}
