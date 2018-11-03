package GUI;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Date;
import java.text.*;


public class Report {
	private String path;
	private boolean append = false;
	
public Report(String file) {
	path = file;
	
}	
public Report(String file, boolean run) {
	path = file;
	append = run;
}
public void Report(String line) throws IOException {
	
	
	FileWriter write = new FileWriter (path, append);
	PrintWriter printl = new PrintWriter(write);
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date date = new Date();
	
	
	
	if(line.compareTo(path.replaceAll(".txt", "")) ==0) {
		printl.print(line);
		printl.println("\tRan on: "+format.format(date) );
	
	}else if (line.contains("->") ) {
		printl.println("List of paths: \n " +line.replace('}', ' ') +"\n");
		
	}else {
		
		printl.print("List of Activities: \n " +line+"\n");
		
	}
	
	printl.close();
	
		
	
}

	
}
