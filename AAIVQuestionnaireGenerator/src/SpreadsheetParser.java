import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.data.SoyListData;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.tofu.SoyTofu;


public class SpreadsheetParser {
	public static void main(String[] args) throws IOException {
		
		int num = 0;
		
		NewLineRemover.run();
		
		Scanner fileScanner = new Scanner(new File("src/aaivparsed.txt"));
		Map<String, List<Person>> dormMap = new HashMap<String, List<Person>>();
		String questions = fileScanner.nextLine();
		String[] questionArray = questions.split("\t");
		
		while(fileScanner.hasNextLine()) {
			String responses = fileScanner.nextLine();
			String[] responseArray = responses.split("\t");
			
			// Bundle the Soy files for your project into a SoyFileSet.
		    SoyFileSet sfs = new SoyFileSet.Builder().add(new File("src/person.soy")).build();

		    // Compile the template into a SoyTofu object.
		    // SoyTofu's newRenderer method returns an object that can render any template in the file set.
		    SoyTofu tofu = sfs.compileToTofu();

		    SoyTofu simpleTofu = tofu.forNamespace("examples.person");
			
		    SoyMapData questionAnswerMap = new SoyMapData();
		    			
			String name = toTitleCase(responseArray[1].replace("\"", ""));
			String year = responseArray[2].replace("\"", "");
			String hometown = toTitleCase(responseArray[3].replace("\"", ""));
			String major = toTitleCase(responseArray[4].replace("\"", ""));
			
			String url = responseArray[responseArray.length - 2];
			String housing = toTitleCase(responseArray[responseArray.length - 1]);
                    
			
			Person p = new Person(num, name, url);
			
			List<Person> personList = dormMap.get(housing);
			if(personList == null){
				personList = new ArrayList<Person>();
				personList.add(p);
				dormMap.put(housing, personList);
			} else {
				personList.add(p);
			}
			System.out.println("\n" + name + "\n");
			System.out.println(responseArray.length);
			for(int i = 5; i < responseArray.length - 2; i++) {
				if(!responseArray[i].equals("none")) {
					System.out.println(i);
					String question = questionArray[i].replace("\"", "");
					System.out.println("\tQuestion: " + question);
					String response = responseArray[i].replace("\"", "");
					System.out.println("\tResponse: " + response);
					questionAnswerMap.put(question, response);
				}
			}
		    
		    String html = simpleTofu.newRenderer(".person").setData(new SoyMapData(
		    		"name", name,
		    		"year", year,
		    		"major", major,
		    		"hometown", hometown,
		    		"url", url,
		    		"dorm", housing,
		    		"questionAnswerMap", questionAnswerMap   		
		    		)).render();
		    
		    File file = new File("src/" + num + ".html");
		    
		    if (!file.exists()) {
				file.createNewFile();
			}
		    
		    FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(html);
			bw.close();
			
			num++;
		}
		
		for(String dorm : dormMap.keySet()) {
			
			List<Person> personList = dormMap.get(dorm);
			if(personList != null && personList.size() > 0) {
				Collections.sort(personList, new LexicographicComparator());
				
				// Bundle the Soy files for your project into a SoyFileSet.
			    SoyFileSet sfs = new SoyFileSet.Builder().add(new File("src/dorm.soy")).build();

			    // Compile the template into a SoyTofu object.
			    // SoyTofu's newRenderer method returns an object that can render any template in the file set.
			    SoyTofu tofu = sfs.compileToTofu();

			    SoyTofu simpleTofu = tofu.forNamespace("examples.dorm");
				
				File file = new File("src/" + dorm + ".html");
				SoyListData personListData = new SoyListData();

				for(int i = 0; i < personList.size(); i++) {
					Person p = personList.get(i);
				    
				    SoyMapData questionAnswerMap = new SoyMapData("name", p.getName(),
				    		"num", p.getNum(),
				    		"url", p.getUrl());
				    personListData.add(questionAnswerMap);
				}
				
				String html = simpleTofu.newRenderer(".dorm").setData(new SoyMapData(
			    		"dorm", dorm,
			    		"personList", personListData		
			    		)).render();
				
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(html);
				bw.close();
				
			}
			
		}
		fileScanner.close();
		System.out.println("Done");
	}
	
	public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
        sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
        }          
      return sb.toString().trim();
    }  
}

class LexicographicComparator implements Comparator<Person> {
    @Override
    public int compare(Person a, Person b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
}
