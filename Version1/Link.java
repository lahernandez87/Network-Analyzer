public class Link {

	// Set to public so getters & setters aren't needed
	
	public String actName;
	public int actDuration;
	public char deplist;
	
	// Reference to next link made in the LinkList
	// Holds the reference to the Link that was created before it
	// Set to null until it is connected to other links
	
	public Link next; 
	
	public Link(String actName, int actDuration, char deplist){
		
		this.actName = actName;
		this.actDuration = actDuration;
		this.deplist=deplist;
	}
	
	public void display(){
		
		System.out.println("ACTIVITY:" +actName );
		System.out.println("ACTIVITY DURATION:" +actDuration);
		System.out.println("ACTIVITY DEPENDENCIES:" +deplist );
	}
	
	public String toString(){
		
		return actName;
		
	}

	
}
