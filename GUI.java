import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GUI {
	
	private JFrame frame;
	private JTextField acttextField;
	private JTextField durtextField_1;
	private JTextField deptextField_2;
	
	public String aName;
	public int aDuration;
	public char aDepend;
	

	/**
	 * Launch the application.
	 */
	public LinkedList activities = new LinkedList();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel ActivityNameLabel = new JLabel("Activity Name:");
		ActivityNameLabel.setFont(new Font("Chalkboard", Font.BOLD, 20));
		ActivityNameLabel.setBounds(6, 6, 156, 74);
		frame.getContentPane().add(ActivityNameLabel);
		
		JLabel DurationLabel = new JLabel("Duration:");
		DurationLabel.setFont(new Font("Chalkboard", Font.BOLD, 20));
		DurationLabel.setBounds(6, 85, 112, 29);
		frame.getContentPane().add(DurationLabel);
		
		JLabel ListOfDependenciesLabel = new JLabel("List Of Dependencies:");
		ListOfDependenciesLabel.setFont(new Font("Chalkboard", Font.BOLD, 20));
		ListOfDependenciesLabel.setBounds(6, 136, 238, 29);
		frame.getContentPane().add(ListOfDependenciesLabel);
		
		JLabel NetworkAnalyzerLabel = new JLabel("Network Analyzer");
		NetworkAnalyzerLabel.setFont(new Font("Chalkboard", Font.PLAIN, 15));
		NetworkAnalyzerLabel.setBounds(182, 6, 139, 20);
		frame.getContentPane().add(NetworkAnalyzerLabel);
		
		JButton btnEnterAnotherActivity = new JButton("Enter another activity");
		btnEnterAnotherActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				
				aName = acttextField.getText();
				aDuration= Integer.parseInt(durtextField_1.getText());
				aDepend= deptextField_2.getText().charAt(0);
				
				activities.insertLink(aName, aDuration, aDepend);
				 
				
				activities.display();
				System.out.println(aDepend);
				
			}
		});
		
		
		
		
		btnEnterAnotherActivity.setBackground(SystemColor.windowBorder);
		btnEnterAnotherActivity.setBounds(115, 211, 190, 29);
		frame.getContentPane().add(btnEnterAnotherActivity);
		
		JButton btnCreateDiagram = new JButton("Create Diagram");
		btnCreateDiagram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIoutput out = new GUIoutput();
				out.setVisible(true);
				
				
				
			}
		});
		btnCreateDiagram.setBounds(306, 211, 138, 29);
		frame.getContentPane().add(btnCreateDiagram);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(0, 211, 117, 29);
		frame.getContentPane().add(btnExit);
		
		
		//activity name field
		acttextField = new JTextField();
		acttextField.setBounds(175, 32, 130, 26);
		frame.getContentPane().add(acttextField);
		acttextField.setColumns(10);
	
		//activity duration field
		durtextField_1 = new JTextField();
		durtextField_1.setBounds(175, 88, 130, 26);
		frame.getContentPane().add(durtextField_1);
		durtextField_1.setColumns(10);
		
		//activity dependencies field
		deptextField_2 = new JTextField();
		deptextField_2.setBounds(222, 139, 83, 61);
		frame.getContentPane().add(deptextField_2);
		deptextField_2.setColumns(10);
		 
		
	}
}
