package BrickBracker;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {
	private int ballposX;
	private int ballposY;
	private int ballXdir;
	private int ballYdir;
	
	public Ball() {
		Random r = new Random();
		ballposX = r.nextInt(700);
		ballposY = 350;
		ballXdir= r.nextInt(100)%2==0 ? -1:1;
		ballYdir=-1;
	}
	
	public int getBallposX() {
		return ballposX;
	}

	public void setBallposX(int ballposX) {
		this.ballposX = ballposX;
	}

	public int getBallposY() {
		return ballposY;
	}

	public void setBallposY(int ballposY) {
		this.ballposY = ballposY;
	}

	public void Draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
	}

	public int getBallXdir() {
		return ballXdir;
	}

	public void setBallXdir(int ballXdir) {
		this.ballXdir = ballXdir;
	}

	public int getBallYdir() {
		return ballYdir;
	}

	public void setBallYdir(int ballYdir) {
		this.ballYdir = ballYdir;
	}
}
