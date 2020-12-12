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
				// beállítom a soron lévõ mezõt
				mezo[i * size + j] = new Mezo(j, i);
				mezo[i * size + j].index = i * size + j;
				mezo[i * size + j].setOnMouseClicked(new KlikkListener());

				// beállítom hozzá a szöveget
				text[i * size + j] = new Szoveg(j, i);
			}
		}

		return mezo;
	}

	public static Mezo[] placeMine(Mezo[] mezo, int c, int index) {
		// (még) nem bombák
		List<Integer> indexes = new ArrayList<Integer>();
		// bombák
		int[] mines = new int[c];
		// szomszédok
		Szomszed[] szomszedok = Szomszed.values();

		// indexes generálása
		for (int i = 0; i < mezo.length; i++) {
			indexes.add(i);
		}

		// kiszedem az elsõ kattintott mezõt, és szomszédait a listából
		indexes.remove(new Integer(index));
		for (int i = 0; i < 8; i++) {
			Mezo szomszed = mezo[index].getSzomszed(mezo, index, szomszedok[i], meret);
			if (szomszed != null) {
				ArrayHelper ah = new ArrayHelper();
				indexes.remove(new Integer(ah.getIndexInArray(mezo, szomszed)));
			}
		}

		// bombák sorsolása
		for (int i = 0; i < c; i++) {
			// random objektum
			Random rand = new Random();
			// megszerzünk az indexesbõl egy random számot
			int randInt = indexes.get(rand.nextInt(indexes.size()));
			// beállítom a mines tömb elemét (késõbb nem használom)
			mines[i] = randInt;
			// kitörlöm a számot a lehetõségek közül
			indexes.remove(new Integer(randInt));
			// beállítom a mezõ isMine propertyét
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
		// konzolos pályalekérés
		// Scanner scanner = new Scanner(System.in);
		// do {
		/*
		 * System.out.print("Kérem a pálya méretét: "); meret =
		 * Integer.parseInt(scanner.next());
		 */
		// popup
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(stage);
		// olyan mint a rootom
		VBox dialogVbox = new VBox(20);

		// szöveg
		Text txt = new Text("Pálya mérete:");
		txt.setFont(new Font(20));
		dialogVbox.getChildren().add(txt);

		// ide lehet írni
		TextField txtfld = new TextField();
		txtfld.setTranslateY(-15);
		txtfld.setMinWidth(10);
		txtfld.setMaxWidth(50);

		// enter ütés
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

		// OK gomb megnyomása
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				start(txtfld, dialog, stage);
			}
		});
		dialogVbox.getChildren().add(button);

		// súgó
		dialogVbox.setOnKeyPressed(new Sugo(dialog));

		// megjelenítés
		Scene dialogScene = new Scene(dialogVbox, 200, 100);
		dialog.setScene(dialogScene);
		dialog.show();

		// súgó megjelenítése, ha elsõ játék
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

		// beállítom a bombák számát
		bombakSzama = (int) (meret * meret / 6);

		// kiírom a bombák számát, majd üres sor a konzolba, hogy lejjebb jöjjön a többi
		// üzenet
		bombakKijelzo.changeValue(0, bombakSzama);
		System.out.println("Bombák száma: " + bombakSzama);
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
		
		// súgó
		scene.setOnKeyPressed(new Sugo(stage));

		stage.setTitle("Aknakeresõ");
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
