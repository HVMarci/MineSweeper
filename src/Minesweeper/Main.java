package Minesweeper;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
//import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	public static int meret, jelolesekSzama = 0, bombakSzama;
	public static boolean elsoKlikk = true;
	private static Mezo[] mezo;
	public static Text[] text;
	public static BombakKijelzo bombakKijelzo = new BombakKijelzo();
	public static MyTimer timer;
	private static Stage s;

	public static void showMines(Color c) {
		for (Mezo i : mezo) {
			if (i.isMine) {
				i.setFill(c);
			}
		}
	}

	private Mezo[] tablaGen(int size) {
		Mezo[] mezo = new Mezo[size * size];
		text = new Text[size * size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// be�ll�tom a soron l�v� mez�t
				mezo[i * size + j] = new Mezo(j, i);
				mezo[i * size + j].index = i * size + j;
				mezo[i * size + j].setOnMouseClicked(new KlikkListener());

				// be�ll�tom hozz� a sz�veget
				text[i * size + j] = new Szoveg(j, i);
			}
		}

		return mezo;
	}

	public static Mezo[] placeMine(Mezo[] mezo, int c, int index) {
		// (m�g) nem bomb�k
		List<Integer> indexes = new ArrayList<Integer>();
		// bomb�k
		int[] mines = new int[c];
		// szomsz�dok
		Szomszed[] szomszedok = Szomszed.values();

		// indexes gener�l�sa
		for (int i = 0; i < mezo.length; i++) {
			indexes.add(i);
		}

		// kiszedem az els� kattintott mez�t, �s szomsz�dait a list�b�l
		indexes.remove(new Integer(index));
		for (int i = 0; i < 8; i++) {
			Mezo szomszed = mezo[index].getSzomszed(mezo, index, szomszedok[i], meret);
			if (szomszed != null) {
				ArrayHelper ah = new ArrayHelper();
				indexes.remove(new Integer(ah.getIndexInArray(mezo, szomszed)));
			}
		}

		// bomb�k sorsol�sa
		for (int i = 0; i < c; i++) {
			// random objektum
			Random rand = new Random();
			// megszerz�nk az indexesb�l egy random sz�mot
			int randInt = indexes.get(rand.nextInt(indexes.size()));
			// be�ll�tom a mines t�mb elem�t (k�s�bb nem haszn�lom)
			mines[i] = randInt;
			// kit�rl�m a sz�mot a lehet�s�gek k�z�l
			indexes.remove(new Integer(randInt));
			// be�ll�tom a mez� isMine property�t
			mezo[randInt].isMine = true;
		}

		Arrays.sort(mines);

		return mezo;
	}

	public static Mezo[] getMezok() {
		return mezo;
	}

	@Override
	public void start(Stage stage) {

		s = stage;
		// konzolos p�lyalek�r�s
		// Scanner scanner = new Scanner(System.in);
		// do {
		/*
		 * System.out.print("K�rem a p�lya m�ret�t: "); meret =
		 * Integer.parseInt(scanner.next());
		 */
		// popup
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(stage);
		// olyan mint a rootom
		VBox dialogVbox = new VBox(20);

		// sz�veg
		Text txt = new Text("P�lya m�rete:");
		txt.setFont(new Font(20));
		dialogVbox.getChildren().add(txt);

		// ide lehet �rni
		TextField txtfld = new TextField();
		txtfld.setTranslateY(-15);
		txtfld.setMinWidth(10);
		txtfld.setMaxWidth(50);

		// enter �t�s
		txtfld.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER)
					start(txtfld, dialog, stage);
			}
		});

		dialogVbox.getChildren().add(txtfld);

		// OK gomb
		Button button = new Button();
		button.setText("OK");
		button.setTranslateY(-30);

		// OK gomb megnyom�sa
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				start(txtfld, dialog, stage);
			}
		});
		dialogVbox.getChildren().add(button);

		// s�g�
		dialogVbox.setOnKeyPressed(new Sugo(dialog));

		// megjelen�t�s
		Scene dialogScene = new Scene(dialogVbox, 200, 100);
		dialog.setScene(dialogScene);
		dialog.show();

		// s�g� megjelen�t�se, ha els� j�t�k
		/*File file = new File("./resources/elsojatek.txt");
		try (Scanner scanner = new Scanner(file)) {
			boolean bool = scanner.nextBoolean();
			if (bool) {
				Sugo.megjelenit();
				// Path path = Paths.get("./src/Minesweeper/resources/elsojatek.txt");

			}
			scanner.close();
		} catch (Exception e1) {

			File currentDirFile = new File(".");
			String helper = currentDirFile.getAbsolutePath();
			System.out.println(helper);
			System.out.println(e1);
		}
		try {

			Path path = Paths.get("./resources/elsojatek.txt");
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("true", "false");
			Files.write(path, content.getBytes(charset));
		} catch (Exception e) {
			System.out.println(e);
		}*/
		

	}

	private void start(TextField txtfld, Stage dialog, Stage stage) {
		try {
			meret = Integer.parseInt(txtfld.getText());
			dialog.close();
		} catch (Exception e) {
			return;
		}

		// be�ll�tom a bomb�k sz�m�t
		bombakSzama = (int) (meret * meret / 6);

		// ki�rom a bomb�k sz�m�t, majd �res sor a konzolba, hogy lejjebb j�jj�n a t�bbi
		// �zenet
		bombakKijelzo.changeValue(0, bombakSzama);
		System.out.println("Bomb�k sz�ma: " + bombakSzama);
		System.out.println();

		mezo = tablaGen(meret);

		// placeMine(mezo, bombakSzama);
		// timer
		timer = new MyTimer();

		Group root = new Group();
		root.getChildren().addAll(mezo);
		root.getChildren().addAll(text);
		root.getChildren().add(bombakKijelzo);
		root.getChildren().add(timer);
		// root.getChildren().add(btn);

		Scene scene = new Scene(root, meret * 40 + 20, meret * 40 + 40);
		
		// s�g�
		scene.setOnKeyPressed(new Sugo(stage));

		stage.setTitle("Aknakeres�");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return s;
	}

}
