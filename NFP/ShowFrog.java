package de.ovgu.featureide.visualisation;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.fm.core.FeatureDependencies;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.DefaultFormat;
import de.ovgu.featureide.fm.core.configuration.SelectableFeature;
import de.ovgu.featureide.fm.core.io.manager.ConfigurationManager;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.ovgu.featureide.fm.ui.handlers.base.ASelectionHandler;


public class ShowFeatureRelationsGraphCommandHandler extends ASelectionHandler {
	

	@Override
	protected void singleAction(Object element) {
		IProject project = null;
		if (!(element instanceof IProject)) {
			if (element instanceof IAdaptable) {
				project = ((IAdaptable) element).getAdapter(IProject.class);
			}
		} else {
			project = (IProject) element;
		}
		final IFeatureProject featureProject = CorePlugin.getFeatureProject(project);
		Shell shell = new Shell(Display.getCurrent());
		shell.setText("Select feature");
		shell.setSize(400, 200);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		final org.eclipse.swt.widgets.List list = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.V_SCROLL);

		List<String> featureList = ConfigAnalysisUtils.getNoCoreNoHiddenFeatures(featureProject);
		for (String f : featureList) {
			list.add(f);
		}
        
		list.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent event) {
				int[] selections = list.getSelectionIndices();
				showFrog(featureProject, list.getItem(selections[0]));
			}

			public void widgetDefaultSelected(SelectionEvent event) {

			}
		});

		shell.open();

	}

	// P(i|currentI)
	public static double getGivenOperation(boolean[][] matrix, int currentI, int i) {
		double numerator = 0;
		double denominator = 0;
		for (int conf = 0; conf < matrix.length; conf++) {
			if (matrix[conf][currentI]) {
				denominator = denominator + 1.0;
				if (matrix[conf][i]) {
					numerator = numerator + 1.0;
				}
			}
		}
		if (denominator == 0) {
			return 0;
		}
		return numerator / denominator;
	}

	/**
	 * Show frog
	 * 
	 * @param featureProject
	 * @param featureCenter
	 */
	public static void showFrog(IFeatureProject featureProject, String featureCenter) {

		// Get feature in the center
		IFeatureModel featureModel = featureProject.getFeatureModel();
		IFeature fc = featureModel.getFeature(featureCenter);

		// Get formalized constraints, implies and excludes
		List<String> formalizedRequires = new ArrayList<String>();
		List<String> formalizedExcludes = new ArrayList<String>();
		FeatureDependencies fd = new FeatureDependencies(featureModel);
		for (IFeature f : fd.always(fc)) {
			formalizedRequires.add(f.getName());
		}
		for (IFeature f : fd.never(fc)) {
			formalizedExcludes.add(f.getName());
		}

		// Get all features in order ignoring the mandatory features
		List<String> featureList = ConfigAnalysisUtils.getNoCoreNoHiddenFeatures(featureProject);
		// Create the matrix configurations/features for the calculations
		boolean[][] matrix = null;
		try {
			matrix = ConfigAnalysisUtils.getConfigsMatrix(featureProject, featureList);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		Configuration configuration = new Configuration(featureModel);
		FileHandler.load(Paths.get(featureProject.getCurrentConfiguration().getLocationURI()), configuration, new DefaultFormat());
		
		List<SelectableFeature> featureArray = configuration.getFeatures();
		
	    System.out.println(featureModel.getFeatures());
		System.out.println(featureArray);
	
		/*for(IFeature f1: featureArray){
            if(FeatureUtils.getDescription(f1) != null){
            	
            	System.out.println("Hallo "+f1);
            }
		}*/
	
		//System.out.println("unselected Features from Feature Model"+configuration.getUnSelectedFeatures());
		//System.out.println("undefined Features from Feature Model"+configuration.getUndefinedSelectedFeatures());
		List<IFeature> childF = new ArrayList<>();
		List<IFeature> unselectedArray = configuration.getUndefinedSelectedFeatures();
		List<IFeature> unselectedArray11 = new ArrayList<IFeature>();
		unselectedArray11.addAll(unselectedArray);
		System.out.println(unselectedArray);
		for(IFeature f: unselectedArray){
			
			if((FeatureUtils.isAbstract(f)) && (FeatureUtils.hasChildren(f))){
				
				//System.out.println(f.getStructure());
				//unselectedArray11.remove(f.getName());
				Iterable<IFeature> j = (Iterable<IFeature>) FeatureUtils.getChildren(f).iterator();
				for (int i = 0; ((Iterator<IFeature>) j).hasNext(); i++)
		        {
					Object next = ((Iterator<IFeature>) j).next();
					childF.add((IFeature) next);
		        }
					
			}
			
		}
		
		System.out.println(childF);
		
		//System.out.println(FeatureUtils.getDescription(fc));
		
		Object[] NFPCenter = NFP.computeNFP(featureProject,fc);
		List<String> NFP = (List<String>)NFPCenter[0];
		List<String> NFP_VALUES = (List<String>)NFPCenter[1];
		System.out.println(NFP);
		System.out.println(NFP_VALUES);
		//List<Integer> NFPcost = NFP.computeQuantity(featureProject,featureArray);
		//System.out.println(NFPcost);
	/*HashMap<String,Integer> NFPHash = new HashMap<String,Integer>();
	for(int i=0;i<NFPCenter.size()-1;i++){
		if(i%2==0){
		NFPHash.put(NFPCenter.get(i),Integer.valueOf(NFPCenter.get(i+1)));
		}
	}
	System.out.println(NFPHash);
	System.out.println(NFPHash.get("cost"));
	System.out.println(NFPHash.get("security"));*/
	    //System.out.println(configuration.getSelectedFeatureNames());
		
		// Here we create the text with the data to be inserted in the html page

		// var CENTRAL_FEATURE = "Feature C";
		
		//var QUALITATIVE = ["security","preference"];
	    // var QUALITY_VALUES = [0.3,0.5];
		
        //var QUANTITATIVE = ["cost","time","memory"];
        //var QUANTITY_VALUES = [0.2,0.5,0.7];
		
		// var FEATURE_NAMES = ["Feature 1", "Feature 2", "Feature 3", "Feature 4", "Feature 5", "Feature 6"];
		// var PREDICTIONS = [ 0,0.2,0.5,0.6,0.8,1,1];
		// var GIVEN = [1, 0.99, 0.66, 0.5, 0.01, 0]; // no need in html
		// var FORMALIZED_REQUIRES = [];
		// var FORMALIZED_EXCLUDES = [];
		
	 
		StringBuffer data = new StringBuffer(" CENTRAL_FEATURE = \"");
		data.append(featureCenter);
		System.out.println(data);
		data.append("\";\n FEATURE_NAMES = [");
		for (String f : featureList) {
			if (!f.equals(featureCenter)) {
				data.append("\"");
				data.append(f);
				data.append("\",");
			}
		}
		data.setLength(data.length() - 1); // remove last comma
		data.append("];\n GIVEN = [");
		for (String f : featureList) {
			if (!f.equals(featureCenter)) {
				int i = featureList.indexOf(f);
				int ic = featureList.indexOf(featureCenter);
				data.append(getGivenOperation(matrix, ic, i));
				data.append(",");
			}
		}
		data.setLength(data.length() - 1); // remove last comma
		boolean atLeastOne = false;
		data.append("];\n FORMALIZED_REQUIRES = [");
		for (String f : formalizedRequires) {
			data.append("\"");
			data.append(f);
			data.append("\",");
			atLeastOne = true;
		}
		if (atLeastOne) {
			data.setLength(data.length() - 1); // remove last comma
		}
		atLeastOne = false;
		data.append("];\n FORMALIZED_EXCLUDES = [");
		for (String f : formalizedExcludes) {
			data.append("\"");
			data.append(f);
			data.append("\",");
			atLeastOne = true;
		}
		if (atLeastOne) {
			data.setLength(data.length() - 1); // remove last comma
		}
		atLeastOne = false;
	  if(NFP.size()>0){
		data.append("];\n NFP = [");
		for (String f : NFP) {
			data.append("\"");
			data.append(f);
			data.append("\",");
			atLeastOne = true;
		}
	  
		if (atLeastOne) {
			data.setLength(data.length() - 1); // remove last comma
		}
		atLeastOne = false;
	  }	
	  if(NFP_VALUES.size()>0){
		data.append("];\n NFP_VALUES = [");
		for (String f : NFP_VALUES) {
			data.append("\"");
			data.append(f);
			data.append("\",");
			atLeastOne = true;
		}
		if (atLeastOne) {
			data.setLength(data.length() - 1); // remove last comma
		}
		atLeastOne = false;
	   }	
		data.append("];\n");

		File fi = Utils.getFileFromPlugin("de.ovgu.featureide.visualisation", "template/featureRelations/page.html");
		String html = Utils.getStringOfFile(fi);
		html = html.replaceFirst("// DATA_HERE", data.toString());

		// Open the browser
		Shell shell = new Shell(Display.getCurrent());
		shell.setLayout(new FillLayout());
		shell.setSize(900, 800);
		shell.setText("Feature relations graph: " + featureCenter);
		Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		browser.setText(html);
		shell.open();
	}
}
