package edu.illinois.cs.cogcomp.classification.hierarchy.run.preparedata.rcvorg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import edu.illinois.cs.cogcomp.classification.hierarchy.dataprocess.newsgroups.NewsgroupsCorpusConceptData;
import edu.illinois.cs.cogcomp.classification.hierarchy.dataprocess.nytimes.NYTimesCorpusConceptData;
import edu.illinois.cs.cogcomp.classification.hierarchy.dataprocess.rcv.RCVCorpusConceptData;
import edu.illinois.cs.cogcomp.classification.hierarchy.dataprocess.rcvorg.RCVOriginalCorpusConceptData;
import edu.illinois.cs.cogcomp.classification.hierarchy.datastructure.ConceptData;
import edu.illinois.cs.cogcomp.classification.representation.esa.complex.DiskBasedComplexESA;
import edu.illinois.cs.cogcomp.classification.representation.esa.simple.SimpleESALocal;
import edu.illinois.cs.cogcomp.classification.hierarchy.run.ClassifierConstant;

public class CorpusESAConceptualizationRCVOriginal {

	public static void main(String[] args) {
//		conceptualizeCorpusTrain (50) ;
//		conceptualizeCorpusTrain (100) ;
//		conceptualizeCorpusTrain (200) ;
		conceptualizeCorpusTrain (500) ;
//		conceptualizeCorpusTrain (1000) ;
		
//		for (int i = 0; i < 1; ++i) {
//			conceptualizeCorpusTest (50, i) ;
//			conceptualizeCorpusTest (100, i) ;
//			conceptualizeCorpusTest (200, i) ;
//			conceptualizeCorpusTest (500, i) ;
//			conceptualizeCorpusTest (1000, i) ;
//		}

	}
	
	public static void conceptualizeCorpusTrain (int conceptNum) 	{
		int seed = 0;
		Random random = new Random(seed);
		double trainingRate = 0.5;
		
		String inputData = "/shared/shelley/yqsong/data/rcv1v2/ReutersCorpusVolume1/rcvOrgTrainContent.txt";
		String outputData = "/shared/shelley/yqsong/data/rcv1v2/org_output/rcv_train.simple.esa.concepts." + conceptNum;
		
		CorpusESAConceptualizationRCVOriginal corpusContentProc = new CorpusESAConceptualizationRCVOriginal();
		RCVOriginalCorpusConceptData ngData = new RCVOriginalCorpusConceptData();
		ngData.readCorpusContentOnly(inputData, random, trainingRate);
		corpusContentProc.writeCorpusSimpleConceptData(ngData.getCorpusContentMap(), conceptNum, outputData);


	}
//	
//	public static void conceptualizeCorpusTest (int conceptNum, int partNum) 	{
//		int seed = 0;
//		Random random = new Random(seed);
//		double trainingRate = 0.5;
//		
//		String inputData = "data/rcvTest/lyrl2004_tokens_test_pt" + partNum + ".dat";
//		if (ClassifierConstant.isServer == true) {
//			inputData = "/shared/shelley/yqsong/benchmark/rcv1v2/extracted/lyrl2004_tokens_test_pt" + partNum + ".dat";
//		}
//		String outputData = "D:/yqsong/data/rcvData/output_test/rcv_test_pt" + partNum + ".simple.esa.concepts." + conceptNum;
//		if (ClassifierConstant.isServer == true) {
//			outputData = "/shared/shelley/yqsong/benchmark/rcv1v2/output_test/rcv_test_pt" + partNum + ".simple.esa.concepts." + conceptNum;
//		}
//		
//		CorpusESAConceptualizationRCVOriginal corpusContentProc = new CorpusESAConceptualizationRCVOriginal();
//		RCVCorpusConceptData ngData = new RCVCorpusConceptData();
//		ngData.readCorpusContentOnly(inputData, random, trainingRate);
//		corpusContentProc.writeCorpusSimpleConceptData(ngData.getCorpusContentMap(), conceptNum, outputData);
//
//
//	}
	
	public void writeCorpusSimpleConceptData (HashMap<String, String> corpusContentMap, int numConcepts, String file) {
		String content = "";
		try {
			int count = 0;
			FileWriter writer = new FileWriter(file);
			SimpleESALocal esa = new SimpleESALocal();
			for (String docID : corpusContentMap.keySet()) {
				count++;
				System.out.println("written " + count +  " documents with concepts");
				content = corpusContentMap.get(docID);
				List<ConceptData> concepts = esa.getConcepts(numConcepts, content);
				List<String> conceptsList = new ArrayList<String>();
				String docContent = corpusContentMap.get(docID);
				writer.write(docID + "\t" + docContent + "\t");
				for (int i = concepts.size() - 1; i >= 0; i--) {
					writer.write(concepts.get(i).concept + "," + concepts.get(i).score + ";");
				}
				writer.write("\n\r");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(content);
			e.printStackTrace();
		}
			
	}
	
	public void writeCorpusComplexConceptData (HashMap<String, String> corpusContentMap, int numConcepts, String file) {
		try {
			int count = 0;
			FileWriter writer = new FileWriter(file);
			DiskBasedComplexESA esa = new DiskBasedComplexESA();
			for (String docID : corpusContentMap.keySet()) {
				count++;
				System.out.println("written " + count +  " documents with concepts");
				List<ConceptData> concepts = esa.retrieveConcepts(corpusContentMap.get(docID), numConcepts, ClassifierConstant.complexVectorType);
				String docContent = corpusContentMap.get(docID).replace("\t", " ");
				writer.write(docID + "\t" + docContent + "\t");
				for (int i = concepts.size() - 1; i >= 0; i--) {
					writer.write(concepts.get(i).concept + "," + concepts.get(i).score + ";");
				}
				writer.write("\n\r");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
}

