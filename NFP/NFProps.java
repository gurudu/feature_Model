package de.ovgu.featureide.visualisation;

import java.util.ArrayList;
import java.util.List;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeature;

public class NFP {
	 //static List<Integer> NFPcostArray = new ArrayList<Integer>();
	 //static List<Integer> NFPtimeArray = new ArrayList<Integer>();
     //static List<Integer> NFPmemoryArray = new ArrayList<Integer>();
	
	public  static Object[] computeNFP(IFeatureProject featureProject, IFeature fc){
		//List<IFeature> li =  new ArrayList<IFeature>();
		
	   
		 //List<Float> NFPcost = new ArrayList<Float>();
		 //List<Float> NFPtime = new ArrayList<Float>();
	     //List<Float> NFPmemory = new ArrayList<Float>();
	 	List<String> NFPFeature = new ArrayList<String>();
		List<String> NFProps = new ArrayList<String>();
		List<String> NFPValues = new ArrayList<String>();
		

		String[] str = FeatureUtils.getDescription(fc).split(",");
	
		for(String a : str){
			String[] st = a.split(",");
			for(String b : st){
				String[] s1 = b.split("=");
				for(String c : s1){	
					 NFPFeature.add(c);					
				}
			}
		}
		for(int i=0;i<NFPFeature.size();i++){
			String b = NFPFeature.get(i);
			if(i%2==0){
			  NFProps.add(b);
			}else{	
				if( b.equals("HighP")){
					String b1 = b.replaceAll("HighP","0.925");
					NFPValues.add(b1);
			   }else if(b.equals("MedP")){
				   String b2 = b.replaceAll("MedP","0.765");
					NFPValues.add(b2);  
			   }else if (b.equals("LowP")){
				   String b3 = b.replaceAll("LowP","0.595");
					NFPValues.add(b3);   
			   }else if (b.equals("LowN")){
				   String b4 = b.replaceAll("LowN","0.425");
					NFPValues.add(b4); 
			   }else if (b.equals("MedN")){
				   String b5 = b.replaceAll("MedN","0.255");
					NFPValues.add(b5); 
			   }else if(b.equals("HighN")){
				   String b6 = b.replaceAll("HighN","0.085");
					NFPValues.add(b6);   
			   }else{
				NFPValues.add(NFPFeature.get(i));
			   }
				
			}
		}
		/*if( (NFProps.size()>0) && (NFPValues.size()>0)){
			for(int i=0;i<NFProps.size();i++){
				String s1 = NFProps.get(i);
				String c = NFPValues.get(i);
			   if( s1.equals("cost")){
					NFPcost.add(Float.valueOf(c));
			   }else if(s1.equals("time")){
					NFPtime.add(Float.valueOf(c)); 
			   }else if(s1.equals("memory")){
					NFPmemory.add(Float.valueOf(c)); 
			   }else{
				   System.out.println("other quality props");
			   }	
			}
	    }*/
		
		 //System.out.println("Arrays value "+ NFPcost);
		 //System.out.println("Arrays value "+ NFPtime);
		 //System.out.println("Arrays value "+ NFPmemory);

		return new Object[]{NFProps, NFPValues};
	   
	  }	
	
	/*public  static List<Integer> computeQuantity(IFeatureProject featureProject, List<IFeature> featureArray ){
		//List<IFeature> li =  new ArrayList<IFeature>();
		
	   //for(IFeature s : featureArray)	{
		
	 	List<String> NFPFeature = new ArrayList<String>();
		List<String> NFProps = new ArrayList<String>();
		List<String> NFPValues = new ArrayList<String>();
      for(IFeature f : featureArray){
       if(FeatureUtils.getDescription(f) != null){
		String[] str = FeatureUtils.getDescription(f).split(",");
	
		for(String a : str){
			String[] st = a.split(",");
			for(String b : st){
				String[] s1 = b.split("=");
				for(String c : s1){	
					 NFPFeature.add(c);					
				}
			}
		}
		for(int i=0;i<NFPFeature.size();i++){
			String b = NFPFeature.get(i);
			if(i%2==0){
			  NFProps.add(b);
			}else{	
				if( b.equals("HighP")){
					String b1 = b.replaceAll("HighP","0.925");
					NFPValues.add(b1);
			   }else if(b.equals("MedP")){
				   String b2 = b.replaceAll("MedP","0.765");
					NFPValues.add(b2);  
			   }else if (b.equals("LowP")){
				   String b3 = b.replaceAll("LowP","0.595");
					NFPValues.add(b3);   
			   }else if (b.equals("LowN")){
				   String b4 = b.replaceAll("LowN","0.425");
					NFPValues.add(b4); 
			   }else if (b.equals("MedN")){
				   String b5 = b.replaceAll("MedN","0.255");
					NFPValues.add(b5); 
			   }else if(b.equals("HighN")){
				   String b6 = b.replaceAll("HighN","0.085");
					NFPValues.add(b6);   
			   }else{
				NFPValues.add(NFPFeature.get(i));
			   }
				
			}
		}
		if( (NFProps.size()>0) && (NFPValues.size()>0)){
			for(int i=0;i<NFProps.size();i++){
				String s1 = NFProps.get(i);
				String c = NFPValues.get(i);
			   if( s1.equals("cost")){
					NFPcostArray.add(Integer.valueOf(c));
			   }else if(s1.equals("time")){
					NFPtimeArray.add(Integer.valueOf(c)); 
			   }else if(s1.equals("memory")){
					NFPmemoryArray.add(Integer.valueOf(c)); 
			   }else{
				   System.out.println("other quality props");
			   }	
			}
	    }	
      }	
		
      }
      System.out.println("Arrays value "+ NFPcostArray);
		System.out.println("Arrays value "+ NFPtimeArray);
		System.out.println("Arrays value "+ NFPmemoryArray);
		System.out.println("Arrays value "+ NFPValues);
		return NFPcostArray;
	   
	  }		*/
}
