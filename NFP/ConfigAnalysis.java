package de.ovgu.featureide.visualisation;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.DefaultFormat;
import de.ovgu.featureide.fm.core.io.manager.ConfigurationManager;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;

/**
 * Configurations Analysis utils
 * 
 * @author jabier.martinez
 */
public class ConfigAnalysisUtils {

	/**
	 * Get a matrix configurations/features
	 * 
	 * @param featureProject
	 * @param featureList
	 * @return boolean[][]
	 * @throws CoreException
	 */
	public static boolean[][] getConfigsMatrix(IFeatureProject featureProject, List<String> featureList) throws CoreException {
		Collection<IFile> configs = new ArrayList<IFile>();
		// check that they are config files
		IFolder configsFolder = featureProject.getConfigFolder();
		for (IResource res : configsFolder.members()) {
			if (res instanceof IFile) {
				if (((IFile) res).getName().endsWith(".config")) {
					configs.add((IFile) res);
				}
			}
		}    
		boolean[][] matrix = new boolean[configs.size()][featureList.size()];
		int iconf = 0;
		for (IFile config : configs) {
			final Configuration configuration = new Configuration(featureProject.getFeatureModel());
			FileHandler.load(Paths.get(config.getLocationURI()), configuration, ConfigurationManager.getFormat(config.getName()));
			configuration.setPropagate(true);
			configuration.update();
			Set<String> configFeatures = configuration.getSelectedFeatureNames();
			int ifeat = 0;
			for (String f : featureList) {
				matrix[iconf][ifeat] = configFeatures.contains(f);
				ifeat++;
			}
			iconf++;
		}

		return matrix;
	}

	public static Object[] computeFeaturePredictions(IFeatureProject featureProject, IFeature fc) throws CoreException{
		
		List<String> PREDICTIONS = new ArrayList<String>();
		List<String> FeatureProps = new ArrayList<String>();
		List<String> FeatureValues = new ArrayList<String>();
		List<String> relatedFeatureValues = new ArrayList<String>();
		Collection<IFile> predicts = new ArrayList<IFile>();
		
	    IFolder configsFolder = featureProject.getConfigFolder();
		IFeatureModel featureModel = featureProject.getFeatureModel();
	    Configuration configuration = new Configuration(featureModel);
		FileHandler.load(Paths.get(featureProject.getCurrentConfiguration().getLocationURI()), configuration, new DefaultFormat());
		List<IFeature> childF = new ArrayList<>();
	
		List<String> relatedFeatures = computeWidgetFeatures(featureProject);
		List<IFeature> inactivatedFeatures = configuration.getUnSelectedFeatures();
		System.out.println("inactivated features"+inactivatedFeatures);
			Iterable<IFeature> j = (Iterable<IFeature>) FeatureUtils.getChildren(fc).iterator();
			if(FeatureUtils.hasChildren(fc)){	
				for (int i = 0; ((Iterator<IFeature>) j).hasNext(); i++)
		        {
					Object next = ((Iterator<IFeature>) j).next();
					childF.add((IFeature) next);	
		        }
			}
		System.out.println("abstract child array"+childF);
		childF.removeAll(inactivatedFeatures);
		 /*for(int i=0;i<childF.size();i++){
				if(unselectedFeatures.contains(childF.get(i))){
					unselectedFeatures.remove(i);
				
				}
		 }	*/
		relatedFeatures.remove(fc.getName());
		for(IFeature f : childF){
			relatedFeatures.add(f.getName());
		}
		for (IResource res : configsFolder.members()) {
			if (res instanceof IFile) {
				if (((IFile) res).getName().endsWith(".txt")) {
					predicts.add((IFile) res);
				}
			}
		}   
		
		for (IFile predict : predicts) {
        	try {
				java.io.InputStream is=predict.getContents();
				byte[] b=new byte[is.available()];
				is.read(b);
				is.close();
				String fulltext = new String(b);
				String[] a = fulltext.split("\\r?\\n");
		
				for (String t : a) {
					String[] s1 = t.split(":");
					for (String p : s1) {
						PREDICTIONS.add(p);
					}
				}
				for(int i=0;i<PREDICTIONS.size();i++){
					String b1 = PREDICTIONS.get(i);
					if(i%2==0){
						FeatureProps.add(b1);
					}else{	
						FeatureValues.add(b1);
					}
				}			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }
	  for(String f: relatedFeatures){	
		for(int i=0; i< FeatureProps.size(); i++){
			if(FeatureProps.get(i).equals(f)){
				relatedFeatureValues.add(FeatureValues.get(i));
			}
		}
	  }	
		return new Object[]{ relatedFeatures,relatedFeatureValues };
	}
	/**
	 * No core nor hidden features
	 * 
	 * @param featureProject
	 * @return list of feature names
	 */
	public static List<String> getNoCoreNoHiddenFeatures(IFeatureProject featureProject) {
		// make a copy because it is unmodifiable
		List<String> featureList1 = featureProject.getFeatureModel().getFeatureOrderList();
		List<String> featureList = new ArrayList<String>();
		featureList.addAll(featureList1);
		List<IFeature> coreFeatures = featureProject.getFeatureModel().getAnalyser().getCoreFeatures();
		Collection<IFeature> hiddenFeatures = featureProject.getFeatureModel().getAnalyser().getHiddenFeatures();
		for (IFeature coref : coreFeatures) {
		     
			featureList.remove(coref.getName());
		}
		for (IFeature hiddenf : hiddenFeatures) {
			featureList.remove(hiddenf.getName());
		}
		return featureList;
	}

	public static List<String> computeWidgetFeatures(IFeatureProject featureProject) {
		// TODO Auto-generated method stub
		IFeatureModel featureModel = featureProject.getFeatureModel();
	    Configuration configuration = new Configuration(featureModel);
		FileHandler.load(Paths.get(featureProject.getCurrentConfiguration().getLocationURI()), configuration, new DefaultFormat());
		List<IFeature> childF = new ArrayList<>();
		List<String> widgetFeatures = new ArrayList<>();
		List<IFeature> unselectedFeatures = configuration.getUndefinedSelectedFeatures();
		System.out.println("abstract unselected array before filtering"+unselectedFeatures);
		for(IFeature f: unselectedFeatures) {
			Iterable<IFeature> j = (Iterable<IFeature>) FeatureUtils.getChildren(f).iterator();
			if(FeatureUtils.hasChildren(f)){	
				for (int i = 0; ((Iterator<IFeature>) j).hasNext(); i++)
		        {
					Object next = ((Iterator<IFeature>) j).next();
					childF.add((IFeature) next);	
		        }
			}
			
		}
		System.out.println("abstract child array"+childF);
		unselectedFeatures.removeAll(childF);
		 /*for(int i=0;i<childF.size();i++){
				if(unselectedFeatures.contains(childF.get(i))){
					unselectedFeatures.remove(i);
				
				}
		 }	*/	
		System.out.println("abstract undefined array after filtering"+unselectedFeatures);
		for(IFeature f : unselectedFeatures) {
			widgetFeatures.add(f.getName());
		}
		return widgetFeatures;
	}
}
