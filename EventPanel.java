//GUI class

//plan on re-numbering and then shuffling in application class.

package tripPackage;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
/**
 * 
 * @author Lavoy
 *Event Panel displays the main application frame for TripV2.
 *It Randomly generates squares in the background as a paint function and calls
 *supporting classes test methods when the UI is interacte with
 */
public class EventPanel extends JPanel{
	JLabel [] names = new JLabel[TripV2.numEvents];
	private JLabel checker= new JLabel("");
	private JPanel eventPanel = new JPanel();
	private JPanel cluePanel = new JPanel();
	private JPanel instructionPanel = new JPanel();
	private JPanel buttonPanel= new JPanel();
	private JLabel scoreLabel = new JLabel("Score:");
	private JLabel scoreShow = new JLabel();
	private JLabel instruc1 = new JLabel("Select the order in which you think events happened. If you don't think an event happened, select zero.");
	private JLabel instruc2 = new JLabel("An incorrect guess will subtract one from your score. Requesting a clue will subtract 3.");
	private JLabel instruc3 = new JLabel("If your score reaches zero, you lose the game.");
	private Each[] each;
	private JLabel[] clueLabels = new JLabel[(TripV2.total-1)*2];
	private JButton check = new JButton();
	private JButton clue = new JButton();
	private JButton quit = new JButton ();
	private Timer timer;
	private int countdown,countdown1 =0;
	private int[] backgroundColor;
	private Random rand = new Random();
	private BufferedImage[] images = new BufferedImage[3];
	private BufferedImage bf;
	private Color bgColor;
	private int circles;
	Event[] events;
	String[] clues;//=new String[(TripV2.total-1)*2];
	private int score= -5+((TripV2.total-1)*6);
	private int clueCount =0;
	public EventPanel(Event[] events, String[] clues){
		circles=0;
		bgColor=new Color(0,0,0);
		//setLayout(new BorderLayout());
		for(int i=0; i<clueLabels.length; i++){
			clueLabels[i]= new JLabel(""+(i+1)+")");
		}//loop initializing clueLabels
		this.events= new Event[events.length+1];
		for(int i=0; i<events.length; i++){
			this.events[i]= events[i];
		}
		this.events[events.length]=new Event(0,5,0);
		for(int i=0; i<this.events.length; i++){
		}
		each = new Each[this.events.length-1];
		//Populate 'each'
		for(int p =0;p<each.length;p++){
			each[p] = new Each(this.events,p);
			each[p].setPreferredSize(new Dimension(250,66));
			each[p].setLayout(new BorderLayout());
		}
		this.clues=clues;
		ButtonListener b1= new ButtonListener(this);
		cluePanel.setLayout(new GridLayout(((TripV2.total-1)*2),1));
		//end of for loop initialising JTextFields and adding to event panel
		for(int p =0;p<each.length;p++){
			eventPanel.add(each[p]);
		}
		for(JLabel j: clueLabels){
			cluePanel.add(j);
		}//end of loop adding clueLabels to cluePanel
		scoreShow.setText(""+score);
		check.addActionListener(b1);
		clue.addActionListener(b1);
		instructionPanel.add(instruc1);
		instructionPanel.add(instruc2);
		instructionPanel.add(instruc3);
		eventPanel.setPreferredSize(new Dimension(450,1200));
		cluePanel.setPreferredSize(new Dimension(450,450));
		instructionPanel.setPreferredSize(new Dimension(1100,100));
		buttonPanel.setPreferredSize(new Dimension(150,450));
		setPreferredSize(new Dimension(1300,1200));
		//displaying first 3 clues:
		for(clueCount=0;clueCount<3;clueCount++){
			clueLabels[clueCount].setText((clueCount+1)+") "+this.clues[clueCount]);
		}//end of loop displaying first clues 

		timer= new Timer(666,b1);
		timer.start();
		this.backgroundColor = new int[]{rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)};
		playAudio("./tripPackage/music.wav");
		//Import files to use as quit/clue/check buttons
		try {
			images[0]=ImageIO.read(new File("tripPackage/Images/Cluebut.png"));
			images[1]=ImageIO.read(new File("tripPackage/Images/Checkbut.png"));
			images[2]=ImageIO.read(new File("tripPackage/Images/Quitbut.png"));
			clue.setIcon(new ImageIcon(images[0]));
			clue.setBorderPainted(false);
			check.setIcon(new ImageIcon(images[1]));
			check.setBorderPainted(false);
			quit.setIcon(new ImageIcon(images[2]));
			quit.setBorderPainted(false);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		buttonPanel.add(scoreLabel);
		buttonPanel.add(scoreShow);
		buttonPanel.add(check);
		buttonPanel.add(clue);
		buttonPanel.add(quit);
		buttonPanel.add(checker);
		quit.addActionListener(b1);
		checker.setOpaque(false);
		eventPanel.setOpaque(false);
		cluePanel.setOpaque(false);
		instructionPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		add(instructionPanel);
		add(eventPanel);
		add(cluePanel);
		add(buttonPanel);

	}//end of constructor
	/**
	 * playAudio file will play a given string URL to a .WAV file
	 * @author StarshipaldDev
	 * @param soundFile- The string of the .Wav location
	 */
	public static void playAudio(String soundFile) {
		AudioInputStream audioIn = null;
		File f = new File(soundFile);
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	public Image addSquare(BufferedImage b,int i) {
		if(i%5==0) {
			Graphics gb= b.getGraphics();
			Random rand=new Random();
			gb.setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
			int x= rand.nextInt(b.getWidth());
			int y= rand.nextInt(b.getHeight());
			gb.fillRect(rand.nextInt(b.getWidth()),rand.nextInt(b.getHeight()),x/10,x/10);

			gb.dispose();
			
		}
		return b;
		
	}
	public void paint(Graphics g) {

		if(bf==null) {
			bf = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics gb=bf.getGraphics();
			Random rand=new Random();
			Color c=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
			gb.setColor(c);
			gb.fillRect(0,0,bf.getWidth(),bf.getHeight());
			gb.dispose();
			bgColor=c;
		}
		Image f=addSquare(bf,circles);
		g.drawImage(f,0,0,getWidth(),getHeight(),0,0,f.getWidth(null),f.getHeight(null),null);
		BufferedImage bfi=new BufferedImage(f.getWidth(null),f.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bfi.createGraphics();
		bGr.drawImage(f, 0, 0, null);
		bGr.dispose();
		bf=bfi;
		/*
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(bfi, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		super.paint(g);
	}
	@Override
	public void paintComponent(Graphics g){
		if(bf==null) {
			bf = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics gb=bf.getGraphics();
			Random rand=new Random();
			Color c=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
			gb.setColor(c);
			gb.fillRect(0,0,bf.getWidth(),bf.getHeight());
			gb.dispose();
			bgColor=c;
		}
		Image f=addSquare(bf,circles);
		g.drawImage(f,0,0,getWidth(),getHeight(),0,0,f.getWidth(null),f.getHeight(null),null);
		BufferedImage bfi=new BufferedImage(f.getWidth(null),f.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bfi.createGraphics();
		bGr.drawImage(f, 0, 0, null);
		bGr.dispose();
		bf=bfi;
		/*
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(bfi, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		super.paintChildren(g);

	}
	/**
	 * setPanelColors is used to mass-repaint a component, and all its children's backgrounds
	 * 
	 * @author StarshipLadDev
	 * @param parent - A Component that will have itself and all child components have their backgrounds
	 * repainted.
	 */
	public void setPanelColor(Container parent)
	{
		for(Component c : parent.getComponents())
		{
			if(c instanceof Container)
			{
				if(c instanceof JPanel || c instanceof JButton)
				{
					c.setBackground(bgColor);
				}

				setPanelColor((Container)c);
			}
			c.setForeground(Color.black);
		}
	}
	private class ButtonListener implements ActionListener{
		EventPanel j=null;
		public ButtonListener(EventPanel j) {
			this.j=j;
		}
		public void actionPerformed(ActionEvent e){
			if (score<=0){
				score=0;
				scoreShow.setText(""+score);
				checker.setText("Game Over");
			}//end of score check
			else if(e.getSource()==check){
				int c = 0;
				int i=0;
				while(i<each.length){
					if(each[i].getEvent()==j.events.length-1) {
					}
					if (each[i].getPlace() != j.events[each[i].getEvent()].getPosition()){
						c++;
					}
					i++;
				}
				if (c==0){
					checker.setText("Correct! Well Done!");
					scoreLabel.setText("Final Score:");
				} else{
					checker.setText("Incorrect, try again");
					score-=1;
					scoreShow.setText(""+score);
				}
			}//end of check event
			else if(e.getSource()==clue){
				if (clueCount<clues.length){
					clueLabels[clueCount].setText((clueCount+1)+") "+clues[clueCount]);
					score-=3;
					scoreShow.setText(""+score);
					clueCount+=1;
				}
			}//end of clue event
			if(e.getSource()==quit){
				System.exit(0);
			}
			if (score<=0){
				score=0;
				scoreShow.setText(""+score);
				checker.setText("Game Over");
			}//end of score check
			//STARSHIPLADs code
			if(e.getSource()==timer) {
				int i=0;

				while(i<each.length) {
					each[i].repaint();
					i++;
				}

				if(countdown>0) {
					countdown--;
					countdown1--;
				}
				else{
					int o=0;
					while(o<backgroundColor.length) {
						if(rand.nextInt(200)%2==0) {
							backgroundColor[o] += 5;
							if(backgroundColor[o]>=255) {
								backgroundColor[o]=255;
							}
						}
						else {
							backgroundColor[o] -= 5;
							if(backgroundColor[o]<=0) {
								backgroundColor[o]=0;
							}
						}
						o++;
					}
					//bgColor=new Color(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
					setPanelColor(j);
					countdown=1;
				}
				if(countdown1<=0) {
					//backgroundColor = new int[]{rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)};
					countdown1=100;
				}
				circles++;
				System.out.println(circles);
				repaint();
			}
			//End of STARSHIPLADs code
		}//end of method action performed
	}//end of inner class ButtonListener

}//end of class