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
		// nyertél
		System.out.println("Nyertél!");
		// kikékítem a bombákat
		Main.showMines(Color.BLUE);
		// popup
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Main.getStage());
		VBox dialogVbox = new VBox(20);
		Text txt = new Text(" Nyertél!");
		txt.setFont(new Font(40));
		txt.setFill(Color.RED);
		dialogVbox.getChildren().add(txt);
		Scene dialogScene = new Scene(dialogVbox, 200, 60);
		dialog.setScene(dialogScene);
		dialog.setTitle("Nyertél!");
		dialog.show();
		// idõ leállítása
		Main.timer.stop();
	}

	public static void lose() {
		// vesztettél
		System.out.println("Vesztettél!");
		// kipirosítom a bombákat
		Main.showMines(Color.RED);
		// popup
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Main.getStage());
		VBox dialogVbox = new VBox(20);
		Text txt = new Text(" Vesztettél!");
		txt.setFont(new Font(40));
		txt.setFill(Color.RED);
		dialogVbox.getChildren().add(txt);
		Scene dialogScene = new Scene(dialogVbox, 200, 60);
		dialog.setScene(dialogScene);
		dialog.setTitle("Vesztettél!");
		dialog.show();
		// idõ leállítása
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
