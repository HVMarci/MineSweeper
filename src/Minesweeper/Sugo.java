package Minesweeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Sugo implements EventHandler<KeyEvent> {
	
	private static Stage main;
	private final static int meret = 400;
	
	public Sugo(Stage s) {
		main = s;
	}
	
	@Override
	public void handle(KeyEvent e) {
		if (e.getCode() == KeyCode.F1)
			megjelenit();
	}
	
	public static void megjelenit() {
		final Stage help = new Stage();
		help.initOwner(main);
		help.initModality(Modality.APPLICATION_MODAL);
		
		VBox root = new VBox();
		final VBox vb = new VBox();
		ScrollPane sp = new ScrollPane();
		
		// 1. szöveg
		//File file = new File("./src/Minesweeper/resources/help.txt");
//		File file = new File("./resources/help.txt");
		Text[] szoveg;
		try (Scanner scanner = new Scanner(new URL("http://marci.hvj.hu/minesweeper_lib/help.txt").openStream())) {
			int k = 0;
			while (scanner.hasNextLine()) {
				scanner.nextLine();
				k++;
			}
			szoveg = new Text[k];

//			Path path = Paths.get("elsojatek.txt");
//			Charset charset = StandardCharsets.UTF_8;
//			String content = new String(Files.readAllBytes(path), charset);
//			szoveg.setText(content);
			scanner.close();
			Scanner scanner2 = new Scanner(new URL("http://marci.hvj.hu/minesweeper_lib/help.txt").openStream());
			
			int i = 0;
			do {
				
				String t = scanner2.nextLine();
				if (scanner2.hasNextLine())
					t += "\n\n";
				byte[] bt = t.getBytes();
				String t2 = new String(bt,StandardCharsets.UTF_8);
				szoveg[i] = new Text(t2);
				szoveg[i].setFont(new Font(15));
				szoveg[i].setWrappingWidth(meret-10);
				i++;
			} while (scanner2.hasNextLine());
		
			scanner2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		vb.getChildren().addAll(szoveg);
		sp.setContent(vb);
		sp.setPrefSize(meret, meret);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		
//		root.getChildren().addAll(szoveg);
		root.getChildren().add(sp);
		
		Scene scene = new Scene(root, meret, meret);
		
		help.setResizable(false);
		help.setScene(scene);
		help.setTitle("Súgó");
		help.show();
	}
}
