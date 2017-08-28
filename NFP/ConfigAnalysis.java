package de.ovgu.featureide.visualisation;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.configuration.Configuration;
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
		Collection<IFile> predicts = new ArrayList<IFile>();
		
		// check that they are config files
		IFolder configsFolder = featureProject.getConfigFolder();
		for (IResource res : configsFolder.members()) {
			if (res instanceof IFile) {
				if (((IFile) res).getName().endsWith(".config")) {
					configs.add((IFile) res);
				}else {
					predicts.add((IFile) res);
				}
			}
		}   
		
		computeFeaturePredictions(predicts);
        
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

	public static Object[] computeFeaturePredictions(Collection<IFile> predicts){
		List<String> PREDICTIONS = new ArrayList<String>();
		List<String> FeatureProps = new ArrayList<String>();
		List<String> FeatureValues = new ArrayList<String>();
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
			  System.out.println("File contents are "+ PREDICTIONS);
				for(int i=0;i<PREDICTIONS.size();i++){
					String b1 = PREDICTIONS.get(i);
					if(i%2==0){
						FeatureProps.add(b1);
					}else{	
						FeatureValues.add(b1);
					}
				}	
				System.out.println("File contents are "+ FeatureProps);
				System.out.println("File contents are "+ FeatureValues);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
        }
		return new Object[]{ FeatureProps, FeatureValues };
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
}
