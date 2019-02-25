//GUI class

//plan on re-numbering and then shuffling in application class.

package tripPackage;

import java.awt.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

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
	private JPanel[] each = new JPanel[TripV2.numEvents];
	private JLabel[] clueLabels = new JLabel[(TripV2.total-1)*2];
	private JButton check = new JButton("Check Sequence");
	private JButton clue = new JButton("Clue");
	
	private JComboBox[] orderList = new JComboBox[TripV2.numEvents];
	/// STARSHIPLADS CODE
	private JButton quit = new JButton ("Quit");
	private Timer timer;
	private int countdown,countdown1 =0;
	private int[] backgroundColor;
	private Random rand = new Random();
	/// End of STARSHIPALDS CODE
	Event[] events;
	String[] clues;//=new String[(TripV2.total-1)*2];
	private int score= -5+((TripV2.total-1)*6);
	private int clueCount =0;
	public EventPanel(Event[] events, String[] clues){
		this.events= events;
		for(int i=0; i<clueLabels.length; i++){
			clueLabels[i]= new JLabel(""+(i+1)+")");
		}//loop initializing clueLabels
		this.clues=clues;
		ButtonListener b1= new ButtonListener(this);
		cluePanel.setLayout(new GridLayout(((TripV2.total-1)*2),1));
		for(int p =0;p<each.length;p++){
			each[p] = new JPanel();
			each[p].setPreferredSize(new Dimension(250,30));
			each[p].setLayout(new BorderLayout());
		}
		for(int j=0;j<names.length;j++){
			names[j]= new JLabel(events[j].getName());
			//add listener/comboBox for name
			each[j].add(names[j], BorderLayout.WEST);
		}//end of for loop initialising JTextFields and adding to event panel
		Integer[] possSeq=new Integer[TripV2.numEvents+1];
		for(int x=0;x<possSeq.length;x++){
			possSeq[x]=x;
		}
		for(int b=0;b<orderList.length;b++){
			orderList[b]=new JComboBox(possSeq);
			each[b].add(orderList[b],BorderLayout.EAST);
		}//end of for loop initialising JComboBoxes
		for(JPanel p:each){
			eventPanel.add(p);
		}
		for(JLabel j: clueLabels){
			cluePanel.add(j);
		}//end of loop adding clueLabels to cluePanel
		scoreShow.setText(""+score);
		check.addActionListener(b1);
		clue.addActionListener(b1);
		buttonPanel.add(scoreLabel);
		buttonPanel.add(scoreShow);
		buttonPanel.add(check);
		buttonPanel.add(checker);
		buttonPanel.add(clue);
		instructionPanel.add(instruc1);
		instructionPanel.add(instruc2);
		instructionPanel.add(instruc3);
		eventPanel.setPreferredSize(new Dimension(250,450));
		cluePanel.setPreferredSize(new Dimension(850,450));
		instructionPanel.setPreferredSize(new Dimension(1350,100));
		buttonPanel.setPreferredSize(new Dimension(150,450));
		
		setPreferredSize(new Dimension(1350,600));
		//displaying first 3 clues:
		for(clueCount=0;clueCount<3;clueCount++){
			clueLabels[clueCount].setText((clueCount+1)+") "+this.clues[clueCount]);
		}//end of loop displaying first clues 
		/// STARSHIPLADS CODE
		timer= new Timer(666,b1);
		timer.start();
		this.backgroundColor = new int[]{rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)};
		playAudio("./tripPackage/music.wav");
		buttonPanel.add(quit);
		quit.addActionListener(b1);
		///End of STARSHIPLADS CODE
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
				if(c instanceof JPanel)
				{
					c.setBackground(new Color(backgroundColor[0],backgroundColor[1],backgroundColor[2]));
				}

				setPanelColor((Container)c);
			}
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
				for(int i=0;i<each.length;i++){
					if (orderList[i].getSelectedIndex()!= events[i].getPosition()){
						c++;
					}}
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
			else if(e.getSource()==quit){
				System.exit(0);
			}
			if (score<=0){
				score=0;
				scoreShow.setText(""+score);
				checker.setText("Game Over");
			}//end of score check
			//STARSHIPLADs code
			if(e.getSource()==timer) {
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
							System.out.println(""+ backgroundColor[0] + " " + backgroundColor[1] + " " + backgroundColor[2]);
							if(backgroundColor[o]<=0) {
								backgroundColor[o]=0;
							}
						}
						o++;
					}
					setBackground(new Color(backgroundColor[0],backgroundColor[1],backgroundColor[2]));
					setPanelColor(j);
					countdown=1;
				}
				if(countdown1<=0) {
					System.out.println("!!!!!!\t JOSIE ITS 100 \t!!!!");
					backgroundColor = new int[]{rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)};
					countdown1=100;
				}
			}
			//End of STARSHIPLADs code
		}//end of method action performed
	}//end of inner class ButtonListener

}//end of class