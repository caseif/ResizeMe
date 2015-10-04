/** WINDOW SETUP **/
package net.amigocraft.resizeme;

import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Main {

	public static GameFrame f;
	public static int width = 800;
	public static int height = 600;
	public static int resize = 0;

	public static void main(String[] args){
		f = new GameFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true);
		f.setSize(width, height);
		f.setTitle("ResizeMe");
		f.setLocationRelativeTo(null);
		f.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				if (resize >= 2){
					if (!GamePanel.inGame){
						GamePanel.inGame = true;
					}
				}
				else
					resize += 1;
			}
		});
	}

	public static void pullThePlug(){
		WindowEvent wev = new WindowEvent(f, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		f.setVisible(false);
		f.dispose();
		System.exit(0); 
	}
}
