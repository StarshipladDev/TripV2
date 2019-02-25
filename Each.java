package tripPackage;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
public class Each extends JPanel{
	int frame,event;
	BufferedImage image=null;
	JLabel label=null;
	JButton button=null;
	Event[] events=null;
	public Each(Event[] events,int event) {
		// TODO Auto-generated constructor stub
		this.events=events;
		this.label=new JLabel();
		this.button=new JButton();
		this.setEvent(event);
		add(label);
		add(button);
	}
	public void setEvent(int i) {
		this.event=i;
		File f = new File("images/"+i+".png");
		try {
			this.image=ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label.setText(this.event+") "+events[this.event].getName());
	}
	public int getEvent() {
		return event;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
	}

}
