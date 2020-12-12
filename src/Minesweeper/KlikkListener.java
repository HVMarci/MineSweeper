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

				// ne tegyük h�tr�bb, mert v�gtelen loopba kerül, �s kapunk egy ~v�gtelen
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
				// bejel�ljük ha m�g nincsen bejel�lve
				rect.setFill(Color.BLUE);
				rect.jeloles = Jeloles.JELOLT;
				// n�veljük a jel�l�sek sz�m�t
				Main.jelolesekSzama++;
				// ki�rjuk a jel�l�sek sz�m�t
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejel�lt bomb�k: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// z�szl�t rajzolunk
//				Main.text[index].setText("🚩");
			} else if (rect.jeloles == Jeloles.JELOLT) {
				// kikapcsoljuk a jel�l�st
				rect.setFill(Color.WHITE);
				rect.jeloles = Jeloles.NEMJELOLT;
				// cs�kkentjük a jel�l�sek sz�m�t
				Main.jelolesekSzama--;
				// ki�rjuk a jel�l�sek sz�m�t
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejel�lt bomb�k: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// sz�veget ür�tjük
//				Main.text[index].setText("");
			}
			// ha win, akkor win
			if (JatekVege.isWin()) {
				JatekVege.win();
			}
			// g�rg�
		} else if (e.getButton() == MouseButton.MIDDLE) {
			if (rect.jeloles == Jeloles.NEMJELOLT) {
				// bizonytalann� tesszük a jel�l�st
				rect.setFill(Color.YELLOW);
				rect.jeloles = Jeloles.BIZONYTALAN;
				// ki�rjuk a jel�l�sek sz�m�t
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejel�lt bomb�k: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// k�rd�jel
//				Main.text[index].setText("?");
			} else if (rect.jeloles == Jeloles.BIZONYTALAN) {
				// kikapcsoljuk a jel�l�st
				rect.setFill(Color.WHITE);
				rect.jeloles = Jeloles.NEMJELOLT;
				// ki�rjuk a jel�l�sek sz�m�t
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejel�lt bomb�k: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// sz�veget ür�tjük
//				Main.text[index].setText("");
			}
			
		}
	}

}
