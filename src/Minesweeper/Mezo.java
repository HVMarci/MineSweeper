package Minesweeper;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Mezo extends Rectangle {

	public boolean isMine;
	public int index;
	public Jeloles jeloles;

	public Mezo(double x, double y) {
		// legenerálunk egy Rectangle-t
		super();

		// alap beállítások
		this.setFill(Color.WHITE);
		this.setStroke(Color.BLACK);
		this.setWidth(40);
		this.setHeight(40);
		this.setX(x * 40 + 10);
		this.setY(y * 40 + 30);
		// nem akna
		this.isMine = false;
		// nincs bejelölve és felfedve
		this.jeloles = Jeloles.NEMJELOLT;
	}

	public Mezo getSzomszed(Mezo[] rect, int in, Szomszed oldal, int meret) {

		int negyzetMeret = meret * meret - 1;

		switch (oldal) {
		case BALFELSO:
			if (in - meret - 1 < 0 || in % meret == 0)
				return null;
			return rect[in - meret - 1];

		case FELSO:
			if (in - meret < 0)
				return null;
			return rect[in - meret];

		case JOBBFELSO:
			if (in - meret + 1 < 0 || in % meret == meret - 1)
				return null;
			return rect[in - meret + 1];

		case JOBB:
			if (in + 1 > negyzetMeret || in % meret == meret - 1)
				return null;
			return rect[in + 1];

		case JOBBALSO:
			if (in + meret + 1 > negyzetMeret || in % meret == meret - 1)
				return null;
			return rect[in + meret + 1];

		case ALSO:
			if (in + meret > negyzetMeret)
				return null;
			return rect[in + meret];

		case BALALSO:
//			System.out.println(
//					in + ", " + meret + ", " + (in + meret - 1) + ", " + (in % meret) + ", " + (in % meret == 0));
			if (in + meret - 1 > negyzetMeret || in % meret == 0)
				return null;
			return rect[in + meret - 1];

		case BAL:
			if (in - 1 < 0 || in % meret == 0)
				return null;
			return rect[in - 1];
		default:
			return null;
		}
	}

	public int szomszedBombak(Mezo[] rect, int index, int meret) {
		// végig forolom a szomszédokat (sarkok is), ha egyik sem bomba, akkor az
		// összessel végrehajtom ezt a metódust
		Szomszed[] szomszedok = Szomszed.values();
		int bombakSzama = 0;
		for (int i = 0; i < 8; i++) {
			Mezo szomszed = getSzomszed(rect, index, szomszedok[i], meret);
			if (szomszed == null) {
			} else if (szomszed.isMine)
				bombakSzama++;
		}

		if (bombakSzama == 0) {
			for (int i = 0; i < 8; i++) {
				Mezo szomszed = getSzomszed(rect, index, szomszedok[i], meret);
				if (szomszed != null && szomszed.jeloles != Jeloles.BIZTOS) {
					/*
					 * try { int originalX = 50; int originalY = 50;
					 * 
					 * Robot robot = new Robot();
					 * 
					 * robot.mouseMove((int)szomszed.getX()+10+Main.stageX,
					 * (int)szomszed.getY()+10+Main.stageY); robot.mousePress(16);
					 * robot.mouseRelease(16); robot.mouseMove(originalX, originalY); } catch
					 * (AWTException e) { e.printStackTrace(); }
					 */
					try {
						//System.out.print(szomszed.getX()+"; "+szomszed.getY());
						
						szomszed.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, szomszed.getX() + 10,
								szomszed.getY() + 10, 0,
								0, MouseButton.PRIMARY, 1, false, false, false, false,
								true, false, false, true, true, true, null));
					} catch (Exception e) {
						Main.text[0].setText(e.getClass().getSimpleName());
					}

				}
				
			}
			//System.out.println();
		}

		return bombakSzama;
	}
}
