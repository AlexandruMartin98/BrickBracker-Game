package BrickBracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int brickwidth;
	public int brickheight;
	
	
	// asignez valoarea 1 fiecarei caramizi
	// asta inseamna ca exista
	public MapGenerator(int row, int col) {
		map = new int[row][col];
				     //3   //7
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		
		brickwidth = 540/col;
		brickheight = 150/row;
	}
	
	 
	//desenarea caramizilor
	public void draw(Graphics2D g) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j]  > 0) {
					g.setColor(Color.white);
					g.fillRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
					// pana in momentul de fata se genereaza un dreptunghi mare
					// dar caramizile nu sunt separate
					// asa ca am nevoie sa adaug borduri 
					// astfel incat sa pot sa diferentiez caramizile
					g.setStroke(new BasicStroke(3)); //ingrosare linie
					g.setColor(Color.black);
					g.drawRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
				}
			}
		}
	}
	
	public void SetBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}
}
