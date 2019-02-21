/**
 * 
 */
package view.codingwolves;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author jasysak
 *
 */
public class ParseFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = null;
		String filepath = "./src/main/resources/"; // this will need to be updated...
		
		// temporarily hard-set filename -- this will be user entry
		String filename = "Test_Sample2.txt";
		
		try {
			s = new Scanner(new File(filepath + filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
		    list.add(s.next());
		}
		s.close();
		
		// replace all non-alphanumeric values and print list
		for(int i = 0; i < list.size(); i++) {
			String strTemp = list.get(i); 
			list.set(i, strTemp.replaceAll("[^a-zA-Z0-9]", ""));
			System.out.println(list.get(i));
		}
		
		// TODO generate JSON array(s) from filename, checksum, filepath, ArrayList
		//
		// Work in progress...
		//
		
	}
    		
}
