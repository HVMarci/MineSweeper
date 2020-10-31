package Minesweeper;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class KlikkListener implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent e) {
		Mezo rect = (Mezo) e.getSource();
		int index = rect.index;

		// balgomb
		if (e.getButton() == MouseButton.PRIMARY) {
			// csak a jel�letlen �s nem biztos mez�ket kattinthatjuk meg
			if (rect.jeloles == Jeloles.NEMJELOLT) {
				// ha ez az els� klikk
				if (Main.elsoKlikk) {
					Main.elsoKlikk = false;
					Main.placeMine(Main.getMezok(), Main.bombakSzama, index);
					// id� ind�t�sa
					Main.timer.noveles();
				}

				// ne tegy�k h�tr�bb, mert v�gtelen loopba ker�l, �s kapunk egy ~v�gtelen
				// exceptiont!
				rect.jeloles = Jeloles.BIZTOS;
				if (rect.isMine) {
					JatekVege.lose();
				} else {
					// nem akna
					rect.setFill(Color.ANTIQUEWHITE);
					int text = rect.szomszedBombak(Main.getMezok(), index, Main.meret);
					if (text == 0) {
						Main.text[index].setText("");
					} else {
						Main.text[index].setText("" + text);
					}
					// ha win, akkor win
					if (JatekVege.isWin()) {
						JatekVege.win();
					}
				}
			}
		// jobb gomb
		} else if (e.getButton() == MouseButton.SECONDARY) {
			if (rect.jeloles == Jeloles.NEMJELOLT) {
				// bejel�lj�k ha m�g nincsen bejel�lve
				rect.setFill(Color.BLUE);
				rect.jeloles = Jeloles.JELOLT;
				// n�velj�k a jel�l�sek sz�m�t
				Main.jelolesekSzama++;
				// ki�rjuk a jel�l�sek sz�m�t
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejel�lt bomb�k: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
			} else if (rect.jeloles == Jeloles.JELOLT) {
				// bizonytalann� tessz�k a jel�l�st
				rect.setFill(Color.YELLOW);
				rect.jeloles = Jeloles.BIZONYTALAN;
				// cs�kkentj�k a jel�l�sek sz�m�t
				Main.jelolesekSzama--;
				// ki�rjuk a jel�l�sek sz�m�t
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejel�lt bomb�k: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
			} else if (rect.jeloles == Jeloles.BIZONYTALAN) {
				// kikapcsoljuk a jel�l�st
				rect.setFill(Color.WHITE);
				rect.jeloles = Jeloles.NEMJELOLT;
				// ki�rjuk a jel�l�sek sz�m�t
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejel�lt bomb�k: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
			}
			// ha win, akkor win
			if (JatekVege.isWin()) {
				JatekVege.win();
			}
		}
	}

}
