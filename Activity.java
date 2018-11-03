package GUI;
 public class Activity{
 	
	private String name;
	private int duration; 
	private String dependencies;
	
	public Activity()
	{
		name="";
		duration= 0;
		dependencies="";
	}
	
	public Activity(String name, int duration, String dependencies)
	{
		this.name=name;
		this.duration=duration;
		this.dependencies=dependencies;
	}
	
	public String getName() 
	{	
		return name;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public String getDependencies() 
	{
		return dependencies;
	}
	
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public void setDuration(int duration)
	{
		this.duration=duration;
	}
	
	public void setDependencies(String dependencies) 
	{		
		this.dependencies=dependencies;	
	}
	
	@Override
	public String toString ()
	{
		return name + "\t" + duration + "\t" + dependencies;
	}
	public String activity() {
		
		return name +"\t" +duration;
	}
	
}