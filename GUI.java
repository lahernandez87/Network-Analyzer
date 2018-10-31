package phase;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import java.util.ArrayList;

//import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
//import java.util.Set;
import java.util.*;



public class GUI extends JFrame{

	// GUI componenets
	JTextArea	activityTextArea	=new JTextArea();
		
	JLabel 		nameLabel					=new JLabel		("Name: ");
	JTextField	nameTextField				=new JTextField (8);	
	JLabel 		durationLabel				=new JLabel		("Duration: ");
	JTextField	durationTextField			=new JTextField (3);
	JLabel 		dependenciesLabel			=new JLabel		("Dependencies: ");
	JTextField	dependenciesTextField		=new JTextField (8);
	JLabel 		cNameLabel					=new JLabel		("Change Existing Name: ");
	JTextField	cNameTextField				=new JTextField (8);	
	JLabel 		cDurationLabel				=new JLabel		("New Duration: ");
	JTextField	cDurationTextField			=new JTextField (3);

	
	JButton addButton			=new JButton("Add");
	JButton displayAllButton	=new JButton("Process");
	JButton criticalButton		=new JButton("Critical Path");
	JButton restartButton		=new JButton("Restart");
	JButton exitButton			=new JButton("Exit");
	JButton changeButton		=new JButton("Change");
	
	JLabel	exportLabel			=new JLabel("Text File Name:");
	JTextField exportTextField	=new JTextField(30);
	JButton exportTextButton	=new JButton("Create");
		
	//class Data
	boolean largeCycle=false;
	ArrayList<String> ar 						=new ArrayList<String>();
	ArrayList<Integer> pathDuration 			=new ArrayList<Integer>();
	private LinkedList<Activity> activityList	=new LinkedList();
	
	 Map<String, Integer> paths = new HashMap<String, Integer>();
	 String filename;

	
	/*
	 * Constructor
	 */
	public GUI()
	{
		JPanel flow1Panel=new JPanel(new FlowLayout (FlowLayout.CENTER));
		JPanel flow2Panel=new JPanel(new FlowLayout (FlowLayout.CENTER));
		JPanel flow3Panel=new JPanel(new FlowLayout (FlowLayout.CENTER));
		JPanel flow4Panel=new JPanel(new FlowLayout (FlowLayout.CENTER));
				
		JPanel gridPanel =new JPanel(new GridLayout(4,1));
		
		activityTextArea.setEditable(false);
		
		flow1Panel.add(nameLabel);
		flow1Panel.add(nameTextField);
		flow1Panel.add(durationLabel);
		flow1Panel.add(durationTextField);
		flow1Panel.add(dependenciesLabel);
		flow1Panel.add(dependenciesTextField);
		
		
		flow2Panel.add(addButton);
		flow2Panel.add(displayAllButton);
		flow2Panel.add(criticalButton);
		flow2Panel.add(exportTextButton);
		flow2Panel.add(restartButton);
		flow2Panel.add(exitButton);
		
		flow3Panel.add(cNameLabel);
		flow3Panel.add(cNameTextField);
		flow3Panel.add(cDurationLabel);
		flow3Panel.add(cDurationTextField);
		flow3Panel.add(changeButton);
			
		flow4Panel.add(exportLabel);
		flow4Panel.add(exportTextField);
		flow4Panel.add(exportTextButton);
		
		gridPanel.add(flow1Panel);
		gridPanel.add(flow2Panel);
		gridPanel.add(flow3Panel);
		gridPanel.add(flow4Panel);
		
		add (activityTextArea, BorderLayout.CENTER);
		add (gridPanel,BorderLayout.SOUTH);
		
		addButton.addActionListener			(event -> addActivity());
		displayAllButton.addActionListener	(event -> process());
		criticalButton.addActionListener	(event -> criticalPath());
		restartButton.addActionListener		(event -> restartActivity());
		exitButton.addActionListener		(event -> exitApp());
		changeButton.addActionListener		(event -> changeDuration());
		exportTextButton.addActionListener	(event -> {
			try {
				exportText();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
				
		JMenu menu1 = new JMenu("File");
		JMenuItem about = new JMenuItem("About");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem restart = new JMenuItem("Restart");
		JMenuItem exit = new JMenuItem("Exit");

		menu1.add(about);
		menu1.add(help);
		menu1.add(restart);
		menu1.add(exit);
		menuBar.add(menu1);	
		
		about.addActionListener(event -> aboutMenu());
		help.addActionListener(event -> helpMenu());
		restart.addActionListener(event ->restartActivity());
		exit.addActionListener(event -> exitApp());
				
		setJMenuBar(menuBar);		
	}
	
	/*
	 * addButton.addActionListener
	 */
	private void addActivity(){
		//clears the arrayList so you can add after you process the path
		while (!pathDuration.isEmpty()) {
			pathDuration.clear();
	    }
		while (!ar.isEmpty()) {
			ar.clear();
	    }
		largeCycle=false;	
		
		
		boolean isInt=false;
		if(!isInt) {
			try { 
		        Integer.parseInt(durationTextField.getText());
		        isInt=true;
		    } catch(NumberFormatException e) { 
				JOptionPane.showMessageDialog(null, "Error: Duration must be an Integer");
				return;
		    }
		}
		boolean isUnique=true;
		for(Activity act : activityList) {
			if(act.getName().compareToIgnoreCase(nameTextField.getText())==0)
				isUnique=false;
		}
		
		if(isUnique==false)
			JOptionPane.showMessageDialog(null, "Error: Activity already in database");
	
		else {		
			
			activityList.add(new Activity(nameTextField.getText(), Integer.parseInt(durationTextField.getText()), dependenciesTextField.getText()));
			nameTextField.setText("");
			durationTextField.setText("");
			dependenciesTextField.setText("");
		}
		displayAll();
	}
	/*
	 * Prints out the User input to the TextArea
	 */
	private void displayAll()
	{			
		activityTextArea.setText("Name\tDuration\tDependencies\n");
		for(Activity act : activityList) {
			activityTextArea.append(act + "\n");
		}
	}
	/*
	 * 
	 */
	private void process(){
		//refresh 
		displayAll();
		
		boolean connected=true;
		int count=0;
		for(Activity act : activityList) {
			if(act.getDependencies().length()!=0)
			{
				String[] currDependencies = act.getDependencies().split("\\s+");
				boolean match=false;
				for(Activity act2 : activityList) {
					for(int i=0;i<currDependencies.length;i++)
					{
						if(act2.getName().compareToIgnoreCase(currDependencies[i])==0 || currDependencies[i]=="")
						{
							match=true;
						}
					}		
				}
				if(!match)
					connected=false;
			}
			else if(act.getDependencies().length()==0) {
				count++;
			}
		}
		
		if(connected==false || count>1) 
		{	
			JOptionPane.showMessageDialog(null, "Error: Nodes are not connected, restarting the program");
			restartActivity();
			return;
		}
	
		for(Activity act1 : activityList){
			String[] act1Dependencies = act1.getDependencies().split("\\s+");
			boolean cycle=false;
			for(Activity act2 : activityList) {
				for(int i=0;i<act1Dependencies.length;i++) {
					if(act2.getName().equals(act1Dependencies[i]))
					{	
						String[] act2Dependencies = act2.getDependencies().split("\\s+");
						for(int x=0; x<act2Dependencies.length; x++)
						{
							if(act1.getName().equals(act2Dependencies[x]))
							{
								JOptionPane.showMessageDialog(null, "Error: Input contains a cycle , restarting the program");
								restartActivity();
								return;
							}					
						}
					}		
				}
			}
		}
	
		for(Activity act : activityList) {
			if(act.getDependencies().equals("")) 
			{
			export(act, act.getName(), act.getDuration());
			}
		}
		if(largeCycle){
			JOptionPane.showMessageDialog(null, "Error: Input contains a cycle , restarting the program");
			restartActivity();
			return;
		}
		
		String out="";
		for(int i=0; i<ar.size();i++) 
		{
		out+= "Path #" + (i+1) + ": " + ar.get(i) + "\t Duration: " + pathDuration.get(i)+"\n";	
	
		paths.put(ar.get(i), pathDuration.get(i));
	
		
				
		}
		activityTextArea.append("\nList of Paths: \n\n" + out);
	}
	//critical path
	private void criticalPath(){
		process();
		
		String critical = "";
		
		Entry<String,Integer> max = null;

		for(Entry<String,Integer> entry : paths.entrySet()) {
		    
			if (max == null || entry.getValue() > max.getValue()) {
		        max = entry;
		        critical = entry.toString().replaceAll("=", ": ");
			}else if(entry.getValue() == max.getValue()) {
				critical+= "\n" +entry.toString().replaceAll("=", ": ");
				
			}
		}
		
		displayAll();
	
		activityTextArea.append("\nCritical Paths is/are: \n\n"+critical);
	}	
	/*
	 * Text file creation
	 */
	private void exportText() throws IOException{
		TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
		filename = exportTextField.getText();
		
		try {
		Report data = new Report( filename+".txt" , false );
	
			if (!paths.isEmpty()) {
			
			String l;
			String s;
			l = paths.toString().substring(1);
			l = l.replaceAll("=", "\tDuration:");
			
			for(Activity act : activityList) {
				sorted.put(act.getName(), act.getDuration());
			}
			
			s = sorted.toString().replaceAll(",", "\n");
			s = s.replace('=', ':');
			data.Report(filename);
			
			Report add = new Report( filename+".txt" , true);
			add.Report(s.substring(1, sorted.toString().length()-1));
			add.Report(l.replaceAll(",", "\n"));
			
			}		
		}catch (Exception e) {
			System.out.println(e.getMessage() );
		}	
	}	
	/*
	 * change the Path Duration
	 */
	private void changeDuration(){
		//clear path duration
		while (!pathDuration.isEmpty()) {
			pathDuration.clear();
	    }
		while (!ar.isEmpty()) {
			ar.clear();
	    }
		largeCycle=false;	
		
		boolean isInt=false;
		if(!isInt) {
			try { 
		        Integer.parseInt(cDurationTextField.getText());
		        isInt=true;
		    } catch(NumberFormatException e) { 
				JOptionPane.showMessageDialog(null, "Error: Duration must be an Integer");
				return;
		    }
		}
		
		boolean isUnique=true;
		for(Activity act : activityList) {
			if(act.getName().compareToIgnoreCase(cNameTextField.getText())==0)
			{
				act.setDuration(Integer.parseInt(cDurationTextField.getText()));
				isUnique=false;			
			}
			
		}
		
		if(isUnique==false)
		{
			JOptionPane.showMessageDialog(null, "Duration has been modified, please Process or run Critical Path for updated information");
			cNameTextField.setText("");
			cDurationTextField.setText("");
		}
			
			
		else
			JOptionPane.showMessageDialog(null, "Error:  Path name does not exist");
					
		
		//refresh the Text Area	
		displayAll();
	}
	
	/*
	 * 
	 */
	private void export(Activity first, String currPath, int currDuration) {
		int count=0;	
		for(int i=0; i<activityList.size();i++) {
			String[] currDependencies = activityList.get(i).getDependencies().split("\\s+");
			for(int r=0;r<currDependencies.length;r++)
			{
				if(first.getName().equalsIgnoreCase(currDependencies[r]))
				{
					String[] splitPath=currPath.split("-->");
					for(int index=0; index<splitPath.length; index++) 
					{
						if(splitPath[index].equals(activityList.get(i).getName()))
						{
							largeCycle=true;
							return;
						}
							
					}
					count++;
					export(activityList.get(i), currPath+ "-->" +activityList.get(i).getName(), currDuration+activityList.get(i).getDuration());
				}
			}
		}

		if(count==0) 
		{
			boolean check= true;
			for(int x=0; x<ar.size();x++){
				if(ar.get(x).equalsIgnoreCase(currPath))
						check=false;
			}
			if(check)
			{
				if(pathDuration.size()>0) 
				{
					boolean added=false;
					for(int i=0; i<pathDuration.size();i++)
					{
						if(pathDuration.get(i)<=currDuration)
						{
							ar.add(i,currPath);
							pathDuration.add(i,currDuration);
							added=true;
							break;
						}
					}
					if(!added)
					{
						ar.add(currPath);
						pathDuration.add(currDuration);	
		
					}
				}
				else
				{
					ar.add(currPath);
					pathDuration.add(currDuration);	
				}
			}
		}
	}

	/*
	 * restartButton.addActionListener
	 */
	private void restartActivity()
	{	
		while (!activityList.isEmpty()) {
			activityList.removeFirst();
	    }
		while (!ar.isEmpty()) {
			ar.clear();
	    }
		while (!pathDuration.isEmpty()) {
			pathDuration.clear();
	    }
		while (!paths.isEmpty()) {
			paths.clear();
		}	
		
		nameTextField.setText("");
		durationTextField.setText("");
		dependenciesTextField.setText("");
		activityTextArea.setText("");
		largeCycle=false;	
	}
	
	/*
	 * aboutMenu.addActionListener
	 */
	private void aboutMenu()
	{
		JOptionPane.showMessageDialog(null, "The purpose of this program is to create a list of paths from the user input.  "
											+ "Program created by Artem, Chris and Laura");
			
	}
	/*
	 * helpMenu.addActionListener
	 */
	private void helpMenu()
	{
		JOptionPane.showMessageDialog(null, "Name can be multiple characters\n"
											+ "Duration should be an integer\n"
											+ "Enter dependencies separated by an space\n"
											+ "Add will enter the information into the Network\n"
											+ "Process will output all the paths in the Network\n"
											+ "Critical Path will only output the critical paths in the Network\n"
											+ "Restart will start a new process\n"
											+ "Exit will close the application\n\n"										
											+ "Changing the duration for an existing Name, enter the name,\n"
											+ "with the new duration, and click 'Change'\n\n"
											+ "To send the information a Text File, enter your file name, \n"
											+ "and click 'Text File'\n");
			
	}
	
	/*
	 * exitButton.addActionListener
	 */
	private void exitApp()
	{	
		System.exit(0);	
	}
	
 	
	public static void main(String[] args){
		GUI app=new GUI();
		app.setVisible(true);
		app.setSize(650,550);
		app.setLocation(200,100);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

/*	NOT USED
 	JMenu menu1 = new JMenu("File");
	MenuItem restartItem = new JMenuItem("Restart");
	//actionListener
	JMenuItem exitItem = new JMenuItem("Exit");	
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}	
		});
	
	menu1.add(restartItem);
	menu1.add(exitItem);
	menuBar.add(menu1);
			//JMenuItem about = new JMenuItem("Program created by Artem, Chris and Laura");
		//JMenuItem about2=new JMenuItem("The purpose of this program is to create a list of paths from the user input");
		//menu2.add(about);
		//menu2.add(about2);
		 * 		//JMenuItem helpName = new JMenuItem("Name can be multiple characters");
		//JMenuItem helpDuration = new JMenuItem("Duration should be an integer");
		//JMenuItem helpDepend = new JMenuItem("Enter dependencies separated by an space");
		//JMenuItem helpAdd = new JMenuItem("Add will enter the information into the Network");
		//JMenuItem helpCreate = new JMenuItem("Press Process after all the Nodes have been entered");
		//menu3.add(helpName);
		//menu3.add(helpDuration);
		//menu3.add(helpDepend);
		//menu3.add(helpAdd);
		//menu3.add(helpCreate);
 */



/*	
for(Activity act : activityList) 
{
	if(act.getDependencies().length()==0)
		activityTextArea.append(process(act));
	}
	
}
private void process(Activity first){
/*	String all="";
String full="";
for(Activity act : activityList) 
{
String[] currDependencies = act.getDependencies().split("\\s+");
	for(int i=0;i<currDependencies.length;i++)
	{
		String partial;
		if(currDependencies[i].compareToIgnoreCase(first.getName())==0)
		{
			partial=(first.getName()+ " " + process(act));
		}
		else 
		{
			partial= first.getName();
		}
		full+=partial+"";
	}
	all+=full + "\n";
}
return all;
}
private void deleteActivity() 
{
boolean isUnique=true;
for(Activity act : activityList) {
	if(act.getName().compareToIgnoreCase(nameTextField.getText())==0)
		isUnique=false;
}
if(isUnique) 
{
	for(int i=0; i<activityList.size();i++)
	{
		String currName=activityList.get(i).getName();
		if(currName.compareToIgnoreCase(nameTextField.getText())==0) 
		{
			activityList.remove(i);
			
		}
		
	}
	
	
	
	
}
	
for(int r=0; r<activityList.size(); r++)
{
String[] currDependencies = activityList.get(r).getDependencies().split("\\s+");
for(int i=0;i<currDependencies.length;i++)
{
if(first.getName()==currDependencies[i])
{
	activityTextArea.append(" --> " + activityList.get(r).getName());
	process(activityList.get(r));
}	
}
}
*/	