package Minesweeper;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MyTimer extends Text {

	private Time time = new Time(0);
	private Timer timer = new Timer();

	public MyTimer() {
		super();

		this.setX(230);
		this.setY(40);
		this.setFill(Color.BLACK);
		this.setFont(new Font(20));

		this.update();
	}

	public void noveles() {

		time.setSeconds(time.getSeconds() + 1);
		this.update();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				noveles();
			}
		}, 1000);
	}

	private void update() {
		String sec;
		if (time.getSeconds() < 10) {
			sec = "0" + time.getSeconds();
			this.setText("Idõ: " + time.getMinutes() + ":" + sec);
		} else {
			sec = "" + time.getSeconds();
		}

		this.setText("Idõ: " + time.getMinutes() + ":" + sec);
	}
	
	public void stop() {
		timer.cancel();
	}
}
