package edu.illinois.cs.cogcomp.classification.hierarchy.dataprocess.yahoo;

import java.util.HashMap;
import java.util.HashSet;

import edu.illinois.cs.cogcomp.classification.hierarchy.dataprocess.abstracts.AbstractTreeLabelData;
import edu.illinois.cs.cogcomp.classification.hierarchy.dataprocess.newsgroups.NewsgroupsTopicHierarchy;

public class YahooDirLabelData extends AbstractTreeLabelData {

	private static final long serialVersionUID = 1L;


	@Override
	public void readTreeHierarchy(String fileTopicHierarchyPath) {
		YahooTopicHierarchy ngTH = new YahooTopicHierarchy();
		
		HashMap<String, HashMap<String, String>> topicHierarchy = ngTH.getTopicHierarchy();

		treeIndex.put("root", new HashSet<String>());
		for (String topic : topicHierarchy.keySet()) {
			HashMap<String, String> subTopics = topicHierarchy.get(topic);
			treeIndex.get("root").add(topic);
			parentIndex.put(topic, "root");
			treeLabelNameHashMap.put(topic, topic);
			
			treeIndex.put(topic, new HashSet<String>());
			for (String subTopicKey : subTopics.keySet()) {
				String subTopicName = subTopics.get(subTopicKey);
				treeIndex.get(topic).add(subTopicKey);
				parentIndex.put(subTopicKey, topic);
				treeLabelNameHashMap.put(subTopicKey, subTopicName);
			}
		}
	}


	@Override
	public void readTopicDescription(String topicDescriptionPath) {
		
	}

}