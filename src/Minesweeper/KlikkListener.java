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
			// csak a jelöletlen és nem biztos mezõket kattinthatjuk meg
			if (rect.jeloles == Jeloles.NEMJELOLT) {
				// ha ez az elsõ klikk
				if (Main.elsoKlikk) {
					Main.elsoKlikk = false;
					Main.placeMine(Main.getMezok(), Main.bombakSzama, index);
					// idõ indítása
					Main.timer.noveles();
				}

				// ne tegyÃ¼k hátrébb, mert végtelen loopba kerÃ¼l, és kapunk egy ~végtelen
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
				// bejelöljÃ¼k ha még nincsen bejelölve
				rect.setFill(Color.BLUE);
				rect.jeloles = Jeloles.JELOLT;
				// növeljÃ¼k a jelölések számát
				Main.jelolesekSzama++;
				// kiírjuk a jelölések számát
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejelölt bombák: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// zászlót rajzolunk
//				Main.text[index].setText("ðŸš©");
			} else if (rect.jeloles == Jeloles.JELOLT) {
				// kikapcsoljuk a jelölést
				rect.setFill(Color.WHITE);
				rect.jeloles = Jeloles.NEMJELOLT;
				// csökkentjÃ¼k a jelölések számát
				Main.jelolesekSzama--;
				// kiírjuk a jelölések számát
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejelölt bombák: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// szöveget Ã¼rítjÃ¼k
//				Main.text[index].setText("");
			}
			// ha win, akkor win
			if (JatekVege.isWin()) {
				JatekVege.win();
			}
			// görgõ
		} else if (e.getButton() == MouseButton.MIDDLE) {
			if (rect.jeloles == Jeloles.NEMJELOLT) {
				// bizonytalanná tesszÃ¼k a jelölést
				rect.setFill(Color.YELLOW);
				rect.jeloles = Jeloles.BIZONYTALAN;
				// kiírjuk a jelölések számát
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejelölt bombák: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// kérdõjel
//				Main.text[index].setText("?");
			} else if (rect.jeloles == Jeloles.BIZONYTALAN) {
				// kikapcsoljuk a jelölést
				rect.setFill(Color.WHITE);
				rect.jeloles = Jeloles.NEMJELOLT;
				// kiírjuk a jelölések számát
				Main.bombakKijelzo.changeValue(Main.jelolesekSzama, Main.bombakSzama);
				System.out.println("Bejelölt bombák: " + Main.jelolesekSzama + " / " + Main.bombakSzama);
				// szöveget Ã¼rítjÃ¼k
//				Main.text[index].setText("");
			}
			
		}
	}

}
