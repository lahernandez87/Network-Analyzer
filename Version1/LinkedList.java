class LinkedList{
	
	// Reference to first Link in list
	// The last Link added to the LinkedList
	
	public Link firstLink; 
	
	LinkedList(){
		
		// first Link always starts as null
		
		firstLink = null;
		
	}
	
	// Returns true if LinkList is empty
	
	public boolean isEmpty(){
		
		return(firstLink == null);
		
	}
	
	public void insertLink(String actName, int actDuration, char actDepend){
		
				
		Link newLink = new Link(actName, actDuration, actDepend);
		
		// Connects the firstLink field to the new Link 
		
		newLink.next = firstLink;
		
		firstLink = newLink;
		
	}
	
	public Link removeFirst(){
		
		Link linkReference = firstLink;
		
		if(!isEmpty()){
		
			// Removes the Link from the List
		
			firstLink = firstLink.next;
		
		} else {
			
			System.out.println("Empty LinkedList");
			
		}
		
		return linkReference;
		
	}
	
	public void display(){
		int count=0;
		Link theLink = firstLink;
		
	
		
		while(theLink != null){
			
			theLink.display();
			
			System.out.println("Next Link: " + theLink.next);
			
			theLink = theLink.next;
			
			System.out.println(count++);
			
		}
		
	}
	
	
	
	
}
