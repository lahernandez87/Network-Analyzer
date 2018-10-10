
 import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
 import javax.swing.*;
 public class GUI extends JFrame{
 	
	// GUI componenets
	JTextArea	activityTextArea	=new JTextArea();
	
	
	JLabel 		nameLabel				=new JLabel		("Name: ");
	JTextField	nameTextField			=new JTextField (8);	
	JLabel 		durationLabel			=new JLabel		("Duration: ");
	JTextField	durationTextField		=new JTextField (3);
	JLabel 		dependenciesLabel		=new JLabel		("Dependencies: ");
	JTextField	dependenciesTextField	=new JTextField (8);	
	
	
	
	JButton addButton			=new JButton("Add");
	JButton displayAllButton	=new JButton("Process");
	JButton restartButton		=new JButton("Restart");
	JButton exitButton			=new JButton("Exit");
	
	
	//class Data
	
	private LinkedList<Activity> activityList=new LinkedList();
	
	
	public GUI()
	{
		JPanel flow1Panel=new JPanel(new FlowLayout (FlowLayout.CENTER));
		JPanel flow2Panel=new JPanel(new FlowLayout (FlowLayout.CENTER));
		
		JPanel gridPanel =new JPanel(new GridLayout(2,1));
		
		activityTextArea.setEditable(false);
		
		flow1Panel.add(nameLabel);
		flow1Panel.add(nameTextField);
		flow1Panel.add(durationLabel);
		flow1Panel.add(durationTextField);
		flow1Panel.add(dependenciesLabel);
		flow1Panel.add(dependenciesTextField);
		
		
		flow2Panel.add(addButton);
		flow2Panel.add(displayAllButton);
		flow2Panel.add(restartButton);
		flow2Panel.add(exitButton);
		
		gridPanel.add(flow1Panel);
		gridPanel.add(flow2Panel);
		
		add (activityTextArea, BorderLayout.CENTER);
		add (gridPanel,BorderLayout.SOUTH);
		
		addButton.addActionListener			(event -> addActivity());
		displayAllButton.addActionListener	(event -> displayAll());
		exitButton.addActionListener		(event -> exitApp());
		restartButton.addActionListener		(event -> restartActivity());
		
		
		
		
		
		//menuBar
		JMenuBar menuBar = new JMenuBar();//creating the menu bar
		//File with Exit/Restart is not added yet
		JMenu menu1 = new JMenu("File");//creating the first menu
		JMenuItem exitItem = new JMenuItem("Exit");
		
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}	
		});
		
		
		JMenuItem restartItem = new JMenuItem("Restart");
		//actionListener
		
		//menu1.add(restartItem);
		//menu1.add(exitItem);
		//menuBar.add(menu1);

				
		JMenu menu2 = new JMenu("About");
		JMenuItem about = new JMenuItem("Program created by Artem, Chris and Laura");
		menu2.add(about);
		menuBar.add(menu2);	
		
		JMenu menu3 = new JMenu("Help");
		JMenuItem helpName = new JMenuItem("Name can be multiple characters");
		JMenuItem helpDuration = new JMenuItem("Duration should be an integer");
		JMenuItem helpDepend = new JMenuItem("Enter dependencies separated by an space");
		JMenuItem helpAdd = new JMenuItem("Add will enter the information into the Network");
		JMenuItem helpCreate = new JMenuItem("Press create after all the Nodes have been entered");
		menu3.add(helpName);
		menu3.add(helpDuration);
		menu3.add(helpDepend);
		menu3.add(helpAdd);
		menu3.add(helpCreate);
		menuBar.add(menu3);	
		
		setJMenuBar(menuBar);		
		
 	
	}
	
	private void addActivity()
	{
		
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
	private void displayAll()
	{	
		boolean connected=true;
		int count=0;
		for(Activity act : activityList) {
			if(act.getDependencies().length()!=0)
			{
				String[] currDependencies = act.getDependencies().split("\\s+");
				activityTextArea.append(currDependencies[0] + "\n");
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
			JOptionPane.showMessageDialog(null, "Error: Nodes are not connected, restartarting the program");
			restartActivity();
			return;
		}
		
		
		activityTextArea.setText("Name\tDuration\tDependencies\n");
		for(Activity act : activityList) {
			activityTextArea.append(act + "\n");
		}
		
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
 }
	
	private void restartActivity()
	{
		
		while (!activityList.isEmpty()) {
			activityList.removeFirst();
	    }
		nameTextField.setText("");
		durationTextField.setText("");
		dependenciesTextField.setText("");
		activityTextArea.setText("");
		
		
		
	}
	
	
	private void exitApp()
	{	
		System.exit(0);	
	}
	
 	
	public static void main(String[] args)
	{
		GUI app=new GUI();
		app.setVisible(true);
		app.setSize(500,500);
		app.setLocation(200,100);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}