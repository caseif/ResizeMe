package net.amigocraft.resizeme;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public Thread game;

	public static boolean running = true;
	public static boolean inGame = false;

	public static boolean rectDefined = false;

	public static int fps = 1000;

	public static int points = 0;

	public static int speed = 2;
	public static int frame = 0;
	public static int time = 0;
	public static int limit = 1000;

	public static int xOffset = 16;
	public static int yOffset = -38;

	public static Rectangle currentRect = null;

	public static Color rectColor = null;

	Font font = new Font("Verdana", Font.BOLD, 15);
	Font largeFont = new Font("Verdana", Font.BOLD, 50);

	public GamePanel(GameFrame f){
		Color bgcolor = new Color(0xCCCCCC);
		setBackground(bgcolor);
		game = new Thread(this);
		game.start();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (inGame){
			if (currentRect == null){
				int width = 200 + (int)(Math.random() * ((Main.width - 250) + 1));
				int height = 200 + (int)(Math.random() * ((Main.height - 250) + 1));
				Random rand = new Random();
				float r = rand.nextFloat();
				float gr = rand.nextFloat();
				float b = rand.nextFloat();
				Color co = new Color(r, gr, b);
				co.brighter();
				rectColor = co;
				g.setColor(co);
				currentRect = new Rectangle(0, 0, width, height);
			}
		}
		else if (!inGame){
			g.setColor(Color.WHITE);
			((Graphics2D)g).setStroke(new BasicStroke(8));
			g.setFont(largeFont);
			g.drawString("Resize Me", centerText(g, "Resize Me"), Main.f.getHeight() / 2);
			g.drawLine(Main.f.getWidth() - 90, Main.f.getHeight() - 110, Main.f.getWidth() - 40, Main.f.getHeight() - 60);
			g.drawLine(Main.f.getWidth() - 65, Main.f.getHeight() - 58, Main.f.getWidth() - 38, Main.f.getHeight() - 58);
			g.drawLine(Main.f.getWidth() - 38, Main.f.getHeight() - 83, Main.f.getWidth() - 38, Main.f.getHeight() - 60);
			g.setFont(font);
			g.drawString("Drag this corner to start!", Main.f.getWidth() - 230, Main.f.getHeight() - 130);
		}
		if (rectColor != null)
			g.setColor(rectColor);
		if (inGame && currentRect != null){
			g.fillRect(currentRect.x, currentRect.y, currentRect.width, currentRect.height);
			g.setColor(Color.BLACK);
			((Graphics2D)g).setStroke(new BasicStroke(2));
			g.drawLine(0, 0, currentRect.width - 2, currentRect.height - 2);
			g.setColor(Color.RED);
			double ratio = time / (double)limit;
			int x = (int)(currentRect.width * ratio);
			int y = (int)(currentRect.height * ratio);
			g.drawLine(0, 0, x, y);
			g.setColor(new Color(0x666666));
			g.drawLine(0, 0, Main.f.getWidth() - 16, Main.f.getHeight() - 38);
		}
		if (inGame){
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(points), Main.f.getWidth() - 40, Main.f.getHeight() - 45);
		}
	}

	public int centerText(Graphics g, String text){
		int stringLen = (int)
				g.getFontMetrics().getStringBounds(text, g).getWidth();
		return Main.f.getWidth() / 2 - stringLen / 2;
	}

	public void run(){
		while (running){
			if (inGame){
				if (currentRect != null){
					for (int xOff = -5; xOff <= 5; xOff++){
						for (int yOff = -5; yOff <= 5; yOff++){
							if (Main.f.getWidth() + xOff == currentRect.width + 16 && Main.f.getHeight() + yOff == currentRect.height + 38){
								/*try {
									InputStream stream = this.getClass().getResourceAsStream("/sound/ding.wav");
									AudioInputStream aStream = AudioSystem.getAudioInputStream(stream);
									Clip clip = AudioSystem.getClip();
									clip.open(aStream);
									clip.start();
								}
								catch (Exception ex){ex.printStackTrace();*/
								points += 1;
								limit -= 10;
								currentRect = null;
								time = 0;
								break;
							}
						}
						if (currentRect == null)
							break;
					}
					if (currentRect != null){
						if (frame >= speed){
							time += 1;
							frame = 0;
						}
						else
							frame += 1;
					}
				}
				if (time >= limit){
					time = 0;
					points = 0;
					currentRect = null;
					inGame = false;
				}
			}
			repaint();
			fpsSetter();
		}
	}

	public void fpsSetter(){
		try {
			Thread.sleep(fps / 1000);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
