/** JFRAME **/
package net.amigocraft.resizeme;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public GamePanel panel;
	
	public GameFrame(){
		panel = new GamePanel(this);
		setLayout(new GridLayout(1, 1, 0, 0));
		add(panel);
	}
}
