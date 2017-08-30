package de.ovgu.featureide.visualisation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;

public class NFProps {

	public  static Object[] computeNFP(IFeatureProject featureProject, IFeature fc){
		//List<IFeature> li =  new ArrayList<IFeature>();
		
	   
		 //List<Float> NFPcost = new ArrayList<Float>();
		 //List<Float> NFPtime = new ArrayList<Float>();
	     //List<Float> NFPmemory = new ArrayList<Float>();
	 	List<String> NFPFeature = new ArrayList<String>();
		List<String> nfp = new ArrayList<String>();
		List<String> NFPValues = new ArrayList<String>();
		int quantityCount = 0;
		HashMap<String, String> keyWithMinMax = computeQuantity(featureProject);
		
		String[] str = FeatureUtils.getDescription(fc).split("\\r?\\n");
		for(String a : str){
			String[] st = a.split(":");
			
			nfp.add(st[0]);
			
			if(isNumeric(st[1])) {
				if(keyWithMinMax.containsKey(st[0])){
					NFPValues.add(normalize(Double.parseDouble(st[1]), keyWithMinMax.get(st[0])));
				}
			}else{
				NFPValues.add(getEquivalentNumber(st[1]));
			}
		}
		return new Object[]{nfp, NFPValues,quantityCount};
	  }	
	
	
	private static String getEquivalentNumber(String b){
		String result = null;
		if( b.equals("HighP")){
			result = "0.925";
	   }else if(b.equals("MedP")){
		   result = "0.765";
	   }else if (b.equals("LowP")){
			result = "0.595";
	   }else if (b.equals("LowN")){
			result = "0.595"; 
	   }else if (b.equals("MedN")){
			result = "0.255";
	   }else if(b.equals("HighN")){
			result = "0.085";
	   }
		return result;
	}
	
	private static String normalize(double actual, String strMinMax){
		String[] minMax = strMinMax.split("-");
		double result = 0D;
		if(minMax.length == 2 
				&& (Double.parseDouble( minMax[0]) != Double.parseDouble( minMax[1]))){
				result = 	(actual - Double.parseDouble(minMax[0]))/((Double.parseDouble(minMax[1]) - Double.parseDouble(minMax[0])));
		}
		return result+"";
		
	}
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	public static HashMap<String, String> computeQuantity(IFeatureProject featureProject) {
		IFeatureModel featureModel = featureProject.getFeatureModel();
		HashMap<String, List<Double>> NFPHash = new HashMap<>();
		List<Double> valueList = null;
		HashMap<String, String> keyWithMinMax = new HashMap<>();

		for (IFeature f : featureModel.getFeatures()) {
			if (FeatureUtils.getDescription(f) != null) {
				String[] eachLine = FeatureUtils.getDescription(f).split("\\r?\\n");
				for (String a : eachLine) {
					String[] keyValStr = a.split(":");
					if(keyValStr.length==2 && isNumeric(keyValStr[1])){
						if(NFPHash.containsKey(keyValStr[0])) {
							NFPHash.get(keyValStr[0]).add(Double.parseDouble(keyValStr[1]));
						}
						else{
							valueList = new ArrayList<>();
							valueList.add(Double.parseDouble(keyValStr[1]));
							NFPHash.put(keyValStr[0], valueList);
						}
					}
				}
			}

		}
		
		for(String key : NFPHash.keySet()){
			Collections.sort(NFPHash.get(key));
			keyWithMinMax.put(key, NFPHash.get(key).get(0)+"-"+(NFPHash.get(key).size()==1 ?  NFPHash.get(key).get(0) : NFPHash.get(key).get(NFPHash.get(key).size()-1)));
		}
		
		System.out.println("Each property with min and max values");
		System.out.println(keyWithMinMax);
		return keyWithMinMax;

	  }		
}
