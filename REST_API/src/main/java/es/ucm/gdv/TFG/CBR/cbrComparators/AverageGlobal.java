package es.ucm.gdv.TFG.CBR.cbrComparators;

import java.util.ArrayList;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.GlobalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.InContextLocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.StandardGlobalSimilarityFunction;
import es.ucm.fdi.gaia.jcolibri.util.AttributeUtils;

public class AverageGlobal implements GlobalSimilarityFunction {


    public double compute(CaseComponent componentOfCase, CaseComponent componentOfQuery, CBRCase _case,
						  CBRQuery _query, NNConfig numSimConfig) {
		GlobalSimilarityFunction gsf = null;
		LocalSimilarityFunction lsf = null;
		
		
		Attribute[] attributes = AttributeUtils.getAttributes(componentOfCase.getClass());
		
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<Double> weights = new ArrayList<Double>();
		
		int ivalue = 0;
	
		for(int i=0; i<attributes.length; i++) {
			Attribute at1 = attributes[i];
			Attribute at2 = new Attribute(at1.getName(), componentOfQuery.getClass());
			
			try{
			if((gsf = numSimConfig.getGlobalSimilFunction(at1)) != null)
			{
				values.add(gsf.compute((CaseComponent)at1.getValue(componentOfCase), (CaseComponent)at2.getValue(componentOfQuery), _case, _query, numSimConfig));
				weights.add(numSimConfig.getWeight(at1)); 
				ivalue++;
			}
			else if((lsf = numSimConfig.getLocalSimilFunction(at1))  != null)
			{
				if (at1.getValue(componentOfCase) == null && at2.getValue(componentOfQuery) == null) 
					continue;
   		    	if(lsf instanceof InContextLocalSimilarityFunction)
   		    	{
   		    	    InContextLocalSimilarityFunction iclsf = (InContextLocalSimilarityFunction)lsf;
   		    	    iclsf.setContext(componentOfCase, componentOfQuery, _case, _query, at1.getName());
   		    	}
   		    	values.add(lsf.compute(at1.getValue(componentOfCase), at2.getValue(componentOfQuery)));
				weights.add(numSimConfig.getWeight(at1));
				ivalue++;
			}
			}catch(Exception e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		return computeSimilarity(values, weights, ivalue);

    }

	public double computeSimilarity(ArrayList<Double> values, ArrayList<Double> weigths, int ivalue)
	{
		double acum = 0;
		double weigthsAcum = 0;
		for(int i=0; i<ivalue; i++)
		{
			acum += values.get(i) * weigths.get(i);
			weigthsAcum += weigths.get(i);
		}
		return acum/weigthsAcum;
	}
}
