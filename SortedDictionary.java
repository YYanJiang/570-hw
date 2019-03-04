// In java, TreeMap is implemented by Red-Black tree

import java.util.Comparator;
import java.util.TreeMap;


public class SortedDictionary {
	public static void main(String[] args) {
		// Although the built-in TreeMap in java is implemented by
		// red-black tree, it still need an comparator to make the 
		// items in the tree sorted alphabetically.
		TreeMap<String, String> sortedDict = new TreeMap<String, String>(new Comparator<String>(){
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		
		sortedDict.put("hello", "world");
		sortedDict.put("goodbye", "everyone");
		sortedDict.put("name", "student");
		sortedDict.put("occupation", "student");
		sortedDict.put("year", "2016");
		sortedDict.put("gpa", "4.0");
		sortedDict.put("lab", "yes");
		sortedDict.put("assignment", "no");
		sortedDict.put("department", "cs");
		
		// Print the sorted dictionary out
		System.out.println("The sorted dictionary is as following now: " + sortedDict);
		
		// Retrieve the value of gpa and department and display
		System.out.println(sortedDict.get("gpa"));
		System.out.println(sortedDict.get("department"));
	}
}
