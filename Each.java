package tripPackage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * An 'Each' is the Panel used to display each available 'slot' in TripV2 that can be given
* a event in order. Each deals with the setting of the event, the drawing of the event's
* animation and the loading of the relevant animation file
 * @author StarshipladDev
 *
 */
public class Each extends JPanel{
	int frame,event,id,place,nullBut=0;
	BufferedImage image=null;
	BufferedImage edit=null;
	JButton button=null;
	Event[] events=null;
	/**
	 * An 'Each' is the Panel used to display each available 'slot' in TripV2 that can be given
	 * a event in order. Each deals with the setting of the event, the drawing of the event's
	 * animation and the loading of the relevant animation file
	 * 
	 * 
	 * @param events - The list of events from an 'EventPanel' that will be shared by all 'Events' 
	 * in that panel.
	 * @param event- Which event in the above array this specific event should start as 
	 */
	public Each(Event[] events,int event) {
		// TODO Auto-generated constructor stub
		this.events=events;
		this.place=event;
		this.button=new JButton();
		this.event=event;
		button.setSize(66,66);
		this.setEvent(event);
		ButtonListener b = new ButtonListener(event); 
		button.addActionListener(b);

		add(button);
		System.out.println("Made event "+events[event].getName());
	}
	public void setEvent(int i) {
		this.event=i;
		this.id=this.events[event].getId();
		File f = new File("tripPackage/Images/"+id+".png");
		try {
			image=ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Couldn't locate "+f.getPath());
			e.printStackTrace();
		}
		
	}
	/*TODO Make getEvent() return Event object rather than int
	 * 
	 */
	/**
	 * getEvent returns which event in the events array the each object is set to
	 * @return a int stating which event in the 'events' array this
	 * each object is set as. As the parent Drawpanel will have the same array
	 * the event can be found from that
	 * 
	 */
	public int getEvent() {
		return event;
	}
	/**
	 * Returns which 'Each' panel this is (Starting at 1)
	 * @return the 'Each's 'place' value +1, or 0 if the event is
	 * 'null'
	 */
	public int getPlace() {
		if((events.length-1)==event) {
			return 0;
		}
		return place+1;
	}
	public void paint(Graphics g) {
		edit=image.getSubimage(frame*66,0,66,66);
		button.setIcon(new ImageIcon(edit));
		button.setBackground(this.getBackground());
		button.setBorderPainted(false);
		frame++;
		if(frame==3) {
			frame=0;
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	private class ButtonListener implements ActionListener{
		int event;
		public ButtonListener(int event) {
			this.event=event;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			event++;

			if(events.length==event ) {
				event=0;
			}
			setEvent(event);
			repaint();
		}
		
	}

}
