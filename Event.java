//class representing an event.

package tripPackage;

public class Event{
  private int sequence;
  //private boolean happened;
  private String name="null";
  private int id=0;
  /**Default constructor, needed so array 'events' in main method can be initialised with null values*/
  public Event(){
  }//end of default constructor
  
  /** Main constructor. 
   Takes int s to set data field 'sequence'
   Takes int h to set data feild 'happened'(<5 sets to true, other sets to false)
   Takes int n to set name from a series of options
  */
  public Event(int s, int h, int n){
	id=n;
    boolean happened;
    if(h<4)
      happened=true;
    else
      happened=false;
    if(happened)
      sequence=s;
    else
      sequence=0;
    if(n==0)
        name="Null";
    if(n==1)
      name="Sex";
    else if(n==2)
      name="Green Grocers";
    else if(n==3)
      name="Pee";
    else if(n==4)
      name="Gifted colours by Bobbie";
    else if(n==5)
      name="Jacob shows up";
    else if(n==6)
      name="A friend shows up";
    else if(n==7)
      name="Throw thing at gf";
    else if(n==8)
      name="Worship mushroom God";
    else if(n==9)
      name="Fireplace tiles attack!";
    else if(n==10){
      name="Walk around the house";
      sequence=s;
      happened=true;
    }
    else if(n==11)
      name="Build shrine to toilet";
  }//end of constructor method
  
  /**Accessor method for name*/
  public String getName(){
    return name;
  }//end of accessor getName
  /**
   * 
   * Accessor method for id
   * */
  public int getId(){
	    return id;
	  }
  /**Accessor method for sequence*/
  public int getPosition(){
    return sequence;
  }//end of accessor getPosition
  
  /**Mutator for position*/
  public void setPosition(int x){
    sequence=x;
  }
  
  /**toString method*/
  public String toString(){
    String niceGap="";
    while(niceGap.length()<27-name.length()){
      niceGap+=" ";
    }//end of while loop
    return "Event: "+name+niceGap+"\t Sequence position: "+sequence+"\t Happened: "+!(sequence==0);
  }//end of toString method
  
}//end of class