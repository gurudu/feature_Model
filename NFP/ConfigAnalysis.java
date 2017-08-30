package de.ovgu.featureide.visualisation;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.DefaultFormat;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;

/**
 * Configurations Analysis utils
 * 
 * @author Hari Kumar Gurudu
 */
public class ConfigAnalysisUtils {

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
		List<String> relatedFeatures = getRelatedFeatures(featureProject,fc);
	
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
	
	@SuppressWarnings("unchecked")
	public static List<String> getRelatedFeatures(IFeatureProject featureProject,IFeature fc) {
		
		IFeatureModel featureModel = featureProject.getFeatureModel();
	    Configuration configuration = new Configuration(featureModel);
	    FileHandler.load(Paths.get(featureProject.getCurrentConfiguration().getLocationURI()), configuration, new DefaultFormat());
		
		List<String> relatedFeatures = computeWidgetFeatures(featureProject);
	    List<IFeature> childF = new ArrayList<>();
		List<IFeature> inactivatedFeatures = configuration.getUnSelectedFeatures();
			Iterable<IFeature> j = (Iterable<IFeature>) FeatureUtils.getChildren(fc).iterator();
			if(FeatureUtils.hasChildren(fc)){	
				for (; ((Iterator<IFeature>) j).hasNext();)
		        {
					Object next = ((Iterator<IFeature>) j).next();
					childF.add((IFeature) next);	
		        }
			}
		childF.removeAll(inactivatedFeatures);
		relatedFeatures.remove(fc.getName());
		for(IFeature f : childF){
			relatedFeatures.add(f.getName());
		}
		
		return relatedFeatures;
		
	}
	

	@SuppressWarnings("unchecked")
	public static List<String> computeWidgetFeatures(IFeatureProject featureProject) {
		IFeatureModel featureModel = featureProject.getFeatureModel();
	    Configuration configuration = new Configuration(featureModel);
		FileHandler.load(Paths.get(featureProject.getCurrentConfiguration().getLocationURI()), configuration, new DefaultFormat());
		List<IFeature> childF = new ArrayList<>();
		List<String> widgetFeatures = new ArrayList<>();
		List<IFeature> unselectedFeatures = configuration.getUndefinedSelectedFeatures();
		for(IFeature f: unselectedFeatures) {
			Iterable<IFeature> j = (Iterable<IFeature>) FeatureUtils.getChildren(f).iterator();
			if(FeatureUtils.hasChildren(f)){	
				for (; ((Iterator<IFeature>) j).hasNext();)
		        {
					Object next = ((Iterator<IFeature>) j).next();
					childF.add((IFeature) next);	
		        }
			}
			
		}
		unselectedFeatures.removeAll(childF);
		for(IFeature f : unselectedFeatures) {
			widgetFeatures.add(f.getName());
		}
		return widgetFeatures;
	}
}
