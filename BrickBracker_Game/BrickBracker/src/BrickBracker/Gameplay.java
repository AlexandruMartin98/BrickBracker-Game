package BrickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Gameplay extends JPanel implements KeyListener, ActionListener{
	// atunci cand rulam jocul sa nu porneasca de unul singur
	private boolean play = false; 
	private int score = 0; //scorul de inceput
	private ArrayList<Point> ar = new ArrayList<>();
	private int totalBricks = 21; // cate caramizi sunt de spart
	
	private Timer timer;// timpul bilei, cat de repede se misca
	private int delay = 8; // viteza bilei
	
	private int playerX = 410;// pozitia de pornire a cursorului
	
	private int playerWidth = 100;
	private int playerHeight = 8;
	
	private ArrayList<Ball> ballList = new ArrayList<Ball>();
	
	
	
	//obiect pentru generarea mapei ( I )
	private MapGenerator map;
			
			
	//constructor
	public Gameplay() {
		//obiect pentru generarea mapei ( II )
		map = new MapGenerator(3, 7);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
		ballList.add(new Ball());
	}
	
	// aici desenez tot ce e de desenat
	public void paint(Graphics g){
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		g.fillRect(0, 562, 692, 3);
		
		
		//scorul
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Score: "+score, 600, 30);
		
		// paddle = paleta
		g.setColor(Color.green);
		g.fillRect(playerX, 550, playerWidth, playerHeight);
		
		//desenez caramizile
		// daca pun linia asta de cod mingea trece pe sub caramizi
		// asa trece pe deasupra
		map.draw((Graphics2D)g);
		
		for(int i = 0; i < ballList.size(); i++) {
			ballList.get(i).Draw(g);
		}
		
		
		if(totalBricks <= 0) {
			play = false;
			for(int i = 0; i < ballList.size(); i++) {
				ballList.get(i).setBallXdir(0);
				ballList.get(i).setBallYdir(0);
				
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("You WON ", 200, 300);
			g.drawString("Score: " + score, 200, 320);
			
			g.setFont(new Font("Franklin Gothic", Font.BOLD, 20));
			g.drawString("Press ENTER to restart", 260, 350);
		}
		
		
		for(int i = 0; i < ballList.size(); i++) {
		if(ballList.get(i).getBallposY()> 570) {
			play = false;
			ballList.get(i).setBallXdir(0);
			ballList.get(i).setBallYdir(0);
			g.setColor(Color.red);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("You LOSE", 200, 300);
			g.drawString("Score: " + score, 200, 320);
			
			g.setFont(new Font("Franklin Gothic", Font.BOLD, 20));
			g.drawString("Press ENTER to restart", 230, 350);
		}
		}
		g.dispose();
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		timer.start();
		Rectangle c = new Rectangle(playerX, 550, playerWidth, 8);
	   ArrayList<Rectangle> ballRects = new ArrayList<Rectangle>();
	   for(int i=0;i<ballList.size();i++) {
		   ballRects.add(new Rectangle(ballList.get(i).getBallposX(), ballList.get(i).getBallposY(), 20, 20));
	   }
		
		// intersectia bilei cu paleta de joc
		if( play ) {
			//de aici modific paleta adica obiectul in sine 
			for(int i=0;i<ballRects.size();i++)
				if( ballRects.get(i).intersects(c) ) {
					ballList.get(i).setBallYdir(-ballList.get(i).getBallYdir());
					ballList.get(i).setBallposY(ballList.get(i).getBallposY()-5);
				}
			// intersectia bilei cu marginile 
			//primul map e variabila din Gameplay.java cred ca obiectul
			//al doilea map e din MapGenerator.java, matricea
			A:for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int  brickX = j*map.brickwidth + 80;
						int  brickY = i*map.brickheight + 50;
						int  brickwidth = map.brickwidth;
						int  brickheight = map.brickheight;
						
						//creem un rectangle pentru delimitarea caramizii 
						Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
						//creem un rectangle pentru delimitarea mingii 
						
						
						Rectangle brickRect = rect;
						for(int k=0;k<ballRects.size();k++) {
							if(ballRects.get(k).intersects(brickRect)) {
								map.SetBrickValue(0, i, j);
								totalBricks--;
								score += 5;
								System.out.println(totalBricks);
								if(ballList.get(k).getBallposX() + 19 <= brickRect.x || ballList.get(k).getBallposX() + 1 >= brickRect.x + brickRect.width) {
									ballList.get(k).setBallXdir(-ballList.get(k).getBallXdir());
								} else {
									ballList.get(k).setBallYdir(-ballList.get(k).getBallYdir());
								}
								
								break;
								
							}
						}
					}
				}
			}
				 for(int i=0;i<ballList.size();i++) {
					  Ball current = ballList.get(i);
					   current.setBallposX(current.getBallposX()+current.getBallXdir());
					   current.setBallposY(current.getBallposY()+current.getBallYdir());
					   if(current.getBallposX() < 0 ) {
						   
						   current.setBallXdir(-current.getBallXdir());
					   }
					   
					   if(current.getBallposY()< 0 ) {
						   current.setBallYdir(-current.getBallYdir());
					   }
					   
					   if(current.getBallposX() > 670) {
						   
						   current.setBallXdir(-current.getBallXdir());
					   }
					   
				   }	
		}
		repaint();
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(playerX >= 590) {
				playerX = 590;
			}else {
				moveRight();
			}	
		}
		
		if(e.getKeyChar() == KeyEvent.VK_1 ) {
			expand();
		}
		
		if(e.getKeyChar() == KeyEvent.VK_3) {
			contract();
		}
		if(e.getKeyChar() == KeyEvent.VK_5) {
			ballList.add(new Ball());
		}
		
		if(e.getKeyChar() == KeyEvent.VK_6) {
			ballList.clear();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(playerX < 10) {
				playerX = 4;
			}else {
				moveLeft();
			}	
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				for(int i = 0;i < ballList.size(); i++) {
				ballList.get(i).setBallposX( (int) (Math.random()*700) );
				ballList.get(i).setBallposY(350);
				ballList.get(i).setBallXdir(-1);
				ballList.get(i).setBallYdir(-2);
				}
				//toate astea se intampla cand apas ENTER
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
		
	}

	
	public  void expand() {
		playerX -= playerWidth*0.05/2; 
		playerWidth *= 1.05;
	}
	
	public void contract() {
		playerX += playerWidth*0.05/2; 
		playerWidth *= 0.95;
	}
	

	public void moveRight() {
		play = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	
	
	
}
  