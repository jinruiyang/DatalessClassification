package edu.illinois.cs.cogcomp.classification.representation;

import java.util.HashSet;
import java.util.Set;

import edu.illinois.cs.cogcomp.descartes.util.Utilities;

public class QueryPreProcessor {
	
	public static Set<String> stopSet = new HashSet<String>();
	
	public static String process (String query) {
		
		if (stopSet.size() == 0) {
			stopSet = Utilities.getStopWords();
		}
		
		String newQuery  = "";
		
//	    "(" ...
//	    "*" ...
//	    <QUOTED> ...
//	    <TERM> ...
//	    <PREFIXTERM> ...
//	    <WILDTERM> ...
//	    "[" ...
//	    "{" ...
//	    <NUMBER> ...
//	    <TERM> ...
//	    "*" ...
		query = query.toLowerCase().replaceAll(",", " ").replaceAll(":", " ").replaceAll("\\.", " ");
		query = query.toLowerCase().replaceAll("\\?", " ").replaceAll("\\*", " ");
		query = query.toLowerCase().replaceAll("\\[", " ").replaceAll("\\]", " ");
		query = query.toLowerCase().replaceAll("\\(", " ").replaceAll("\\)", " ");
		query = query.toLowerCase().replaceAll("\\{", " ").replaceAll("\\}", " ");
		query = query.toLowerCase().replaceAll("\\<", " ").replaceAll("\\>", " ");
		query = query.toLowerCase().replaceAll("\"", " ");
		
		String[] queryArray = query.split("\\s+");
		
		for (String str : queryArray) {
			if (stopSet.contains(str.trim()) == false) {
				newQuery += str + " "; 
			}
		}
		
		return newQuery;
	}

}
