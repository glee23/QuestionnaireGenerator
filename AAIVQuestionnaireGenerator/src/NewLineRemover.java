import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class NewLineRemover {
	
	public static void run() throws IOException {
		Scanner fileScanner = new Scanner(new File("src/aaiv3.txt"));
		File file = new File("src/aaivparsed.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String questions = fileScanner.nextLine();
		bw.write(questions + "\n");
		
		
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			boolean containsLineBreak = hasLineBreaks(line);
			if(containsLineBreak) {
				System.out.println(line);
				
				boolean joined = false;
				while(!joined) {
					String nextLine = fileScanner.nextLine();
					System.out.println("Next line: " + nextLine);
					if(nextLine.contains("\"")){
						if(hasLineBreaks(nextLine)) {
							//Other line breaks match once we remove
							line += " " + nextLine + "\t";
							
							joined = true;
						} else {
							line += ", " + nextLine;
						}
					} else {
						line += ", " + nextLine.substring(0, nextLine.length());
					}
					System.out.println("New line: " + line);
				}
				//No line breaks
				System.out.println(line);
				
			}
			bw.write(line + "\n");
			
		}
		bw.close();
	}
	
	public static boolean hasLineBreaks(String line) {
		String lb = "\"";
		if(!line.contains(lb)) return false;
		
		int num = 0;
		
		while(line.contains(lb)) {
			int index = line.indexOf(lb);
			
			line = line.substring(index + 1);
			num++;
		}
		
		return num % 2 == 1;
		
	}
	
}


